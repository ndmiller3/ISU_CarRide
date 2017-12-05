package ProjectLogic;

import java.sql.*;
import java.util.Scanner;

/**
 * @author Daniel Tidyman
 *
 * Updated 8.11.2017
 */
public class Rider {

    public double distanceTraveled; //TODO random distance generate, $3.00 + .50 every mile

    private String startLocation;
    private String destination;
    private int rideStyle;
    private String customerEmail;
    private int customerID;
    private String customerCardNumber;
    private double totalCharges;
    private int riderID=1;
    private int driverID=1;

    /**
     * Constructs a new Rider object
     */
    public Rider(){

    }

    /**
     * Class to create a brand new Customer
     *
     */
	public void NewCustomer() {

        Scanner customerData = new Scanner(System.in);

        //Ask the Customer for their relevant data
        System.out.println("\nWhat is your first name?");
        String firstName = customerData.next();
        //TODO handle spaces in names
        System.out.println("What is your last name?");
        String lastName =customerData.next();

        System.out.println("What is your age (in years)?");
        //little correcting so certain people don't try to break the code
        while(!customerData.hasNextInt())
        {
            customerData.next();
            System.out.println("**Invalid age entered!**\nWhat is your age (in years) (please enter only digits)?");
        }
        int customerAge = customerData.nextInt();

        System.out.println("What is your email address?");
        customerEmail =customerData.next();

        System.out.println("What is your password?");
        String customerPassword = customerData.next();

        System.out.println("What is your credit/debit card number?\nPlease enter as such: 1234567891234567");
        //more error correcting
        customerCardNumber = customerData.next();

        //fixed with suggestion from Nolan
        while(customerCardNumber.length() != 16)
        {
            System.out.print("That is not a valid card number, please reenter it now.\n");
            customerCardNumber = customerData.next();
        }


       try(Connection con = Database.getConnection()){

           String newCustomerQuery = "INSERT INTO CUSTOMER (RiderName,Age,CustomerEmail,Password,Card_Number) VALUES(?,?,?,?,?)";

           PreparedStatement newCustomerStatement = con.prepareStatement(newCustomerQuery);

           //set customer values in SQL and execute
           newCustomerStatement.setString(1,firstName+" "+lastName);
           newCustomerStatement.setInt(2,customerAge);
           newCustomerStatement.setString(3,customerEmail);
           newCustomerStatement.setString(4,customerPassword);
           newCustomerStatement.setString(5,customerCardNumber);
           newCustomerStatement.execute();

          String getRiderID = "SELECT RiderID FROM CUSTOMER WHERE CustomerEmail=? AND Password=?";
          PreparedStatement getRiderIDStatement = con.prepareStatement(getRiderID);
          getRiderIDStatement.setString(1,customerEmail);
          getRiderIDStatement.setString(2,customerPassword);

          ResultSet resultSet = getRiderIDStatement.executeQuery();

          riderID = resultSet.getInt(1);

           con.close();
       }
       catch (Exception e){
           System.out.println(e);
       }
    }

    /**
     * This class is designed to gather start location, destination and rideStyle, and query the database for a currently
     * available driver that is nearby and can handle the parameters given. This class also sets the variables that are used
     * to calculate price
     *
     * Note: we were not able to implement a method to calculate distance based off google maps at this time. Possible TODO
     *
     */
    public void callRide(){

        Scanner rideDetails = new Scanner(System.in);

        System.out.println("Please enter your starting address (including City, State and Zip)\nExample: 1234 North Cy Drive, Ames, IA 50010\n");
        startLocation = rideDetails.next();

        System.out.println("Please enter your destination address (including City, State and Zip)\nExample: 1234 North Cy Drive, Ames, IA 50010\n");
        destination = rideDetails.next();

        System.out.println("Please select your ride style by typing the corresponding number: \n(1) Regular\n(2) Car Pool\n(3)Cy-lect\n");
        while(!rideDetails.hasNextInt() || rideDetails.nextInt()<1 || rideDetails.nextInt()>3){
            System.out.println("**INVALID SELECTION**\n \nPlease select your ride style by typing the corresponding number: \n(1) Regular\n(2) Car Pool\n(3)Cy-lect\n");
        }
        rideStyle = rideDetails.nextInt();

        try(Connection connection = Database.getConnection()){

            //Pulls a DriverID to use as the Driver
            String rideQuery = "SELECT DriverID, DriverName FROM DRIVERS WHERE RIDESTYLE=? AND AVAILABILITY = 1";
            PreparedStatement ridePrep = connection.prepareStatement(rideQuery);
            ridePrep.setInt(1,rideStyle);
            ResultSet resultSet = ridePrep.executeQuery();

            //Set's driver ID and prints the Driver Name
            driverID = resultSet.getInt(1);
            System.out.println("Your Driver is: "+resultSet.getString(2));

            connection.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Takes in a RideID and calculates the Rider's Charges
     * @param rideID
     */
    public void RiderCharges(int rideID){

        try(Connection con = Database.getConnection())
        {
            PreparedStatement getDistance = con.prepareStatement("SELECT DistanceTraveled FROM RIDES WHERE RideIdentification=?");
            getDistance.setInt(1,rideID);

            //gets the distance traveled
            ResultSet resultSet = getDistance.executeQuery();

            //calculates charges
            totalCharges = resultSet.getInt(1);
        }

        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Get's the rating the rider gives the driver
     */
    public void rateDriver(int driverID){
        Scanner rating = new Scanner(System.in);

        System.out.println("Please enter the rating of your driver, 1-5: ");
        int driverRating = rating.nextInt();

        try(Connection con = Database.getConnection())
        {
         PreparedStatement setRating = con.prepareStatement("UPDATE DRIVERS SET DriverRating=? WHERE DriverID =? ");
         setRating.setInt(1,driverRating);
         setRating.setInt(2,driverID);
         setRating.execute();

        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void customerLogin(){

        Scanner loginScanner = new Scanner(System.in);

        //These next few lines try to gather customer data and search a database for corresponding entries.
        System.out.println("Enter your email: ");
        customerEmail = loginScanner.next();

        System.out.println("Enter your password: ");
        String password = loginScanner.next();

        try(Connection connection = Database.getConnection()){

            //Since all we need is the customerEmail and cardNumber, we just select these two fields
            String loginQuery = "SELECT CustomerEmail, Card_Number, RiderID FROM CUSTOMER WHERE CustomerEmail=? AND PASSWORD=?";

            //these set and execute the query to try and find the matching customer data
            PreparedStatement loginSearch = connection.prepareStatement(loginQuery);
            loginSearch.setString(1,customerEmail);
            loginSearch.setString(2,password);

            ResultSet resultSet = loginSearch.executeQuery();

            //tests to see if we have correct login information
            while (!resultSet.next()){
                System.out.println("\n**INVALID EMAIL/PASSWORD. PLEASE TRY AGAIN**\n \nEnter your email: ");
                customerEmail = loginScanner.next();

                System.out.println("Enter your password: ");
                password = loginScanner.next();

                loginSearch.setString(1,customerEmail);
                loginSearch.setString(2,password);
                resultSet = loginSearch.executeQuery();
            }

            //set customer data so we can charge 💰💰💰
            customerEmail=resultSet.getString(1);
            customerCardNumber = resultSet.getString(2);
            customerID = resultSet.getInt(3);

            System.out.println("\n**LOGIN SUCCESSFUL**");

        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public double getTotalCharges(){
        return totalCharges;
    }
    public int getRiderID(){
        return riderID;
    }
    public String getStartLocation()
    {
        return startLocation;
    }
    public String getDestination()
    {
        return destination;
    }
}
