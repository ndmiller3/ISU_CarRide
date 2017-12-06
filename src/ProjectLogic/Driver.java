package ProjectLogic;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;


/**
 * @authorname Nolan Miller
 * Updated: 12-5-17
 */

public class Driver {
	//these values are the default for showcasing
	private String driverName = "Drive McTest"; //Variable for driver name
	private int available=1; //Variable to check driver availability.
	private int riderRating; //Variable to store rider rating.
	private double dailyTotalMoney; // Variable to keep track how much the driver has earned.
	private String driverEmail; // Variable to hold Driver's email.
	private String driverCardNumber; // Variable to hold the Driver's credit card number
	Random ran = new Random();
	private int driverID= ran.nextInt(5); // Variable to hold the Driver's ID Number.
    private int distanceTraveled=2;
    private int rideID;
	private int age;
	
/**
 * Constructor for driver
 */
public Driver()
{
  
}

/**
 *  Asks and sets up everything for driver
 */
public void newDriver()
{
	//Scanner to read all of the input
	Scanner driverData = new Scanner(System.in).useDelimiter("\\n");
	
	//Asking for the first name of the driver
	System.out.print("What is your first name?\n");
	String firstName = driverData.next();

	
	//Asking for the last name of the driver
	System.out.print("What is your last name?\n");
	String lastName = driverData.next();
	
	//Storing the first and last name into a single variable
	driverName = firstName + " " + lastName;
	
	//Asking for the age of the driver.
	System.out.print("What is your age? (In years)(Must be 18)\n");
	
	//Storing their age into a variable.
	 age = driverData.nextInt();
	 
	 
	//Asking for their email address
	System.out.print("What is your email address?\n");
	 driverEmail = driverData.next();
	
	//Asking for a desired password.
	System.out.print("What is your password?\n");
	String password = driverData.next();
	
	//Asking for their credit card number.
	System.out.print("What is your credit/debit card number?\nPlease enter as such: 1234567891234567\n");
	
	 driverCardNumber = driverData.next();

	//Checks the length of the card number
	while(driverCardNumber.length() != 16)
	{
		System.out.print("That is not a valid card number, please re-enter it now.\n");
		driverCardNumber = driverData.next();
	}
	
	//Asks for the make of the car
	System.out.print("What is your car make?\n");
	String car = driverData.next();
	
	//Asks for the model of the car
	System.out.print("What is your car model?\n");
	
	String model = driverData.next();
	
	
	//Asks for the year of the car
	System.out.print("What is the year of your car?\n");
	int year = driverData.nextInt();
	
	//Asks what type or ride style they want.
	System.out.print("What is your ride style? Please enter 1 for regular, 2 for Car Pool, 3 for Cy-Select.\n");
	String rideStyle = driverData.next();
	
	//Asks for the license plate number.
	System.out.print("What is your license plate number?\n");
	String licensePlate = driverData.next();
	
	
	//Checks to make sure the license plate number is the correct length.
	while(licensePlate.length() > 7)
	{
		System.out.println("That is not a valid license plate number, please re-enter it now.\n");
		licensePlate = driverData.next();
	}
	
	 try(Connection con = Database.getConnection())
	 {
		String newDriverQuery = "INSERT INTO DRIVERS (DriverName,Age,Email,Password,RideStyle,LicensePlate,Make,Model,Model_Year,Card_Number) VALUES(?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement newDriverStatement = con.prepareStatement(newDriverQuery);
		
		newDriverStatement.setString(1,firstName+" "+lastName);
        newDriverStatement.setInt(2,age);
        newDriverStatement.setString(3,driverEmail);
        newDriverStatement.setString(4,password);
        newDriverStatement.setString(5, rideStyle);
        newDriverStatement.setString(6, licensePlate);
        newDriverStatement.setString(7, car);
        newDriverStatement.setString(8, model);
        newDriverStatement.setInt(9, year);
        newDriverStatement.setString(10,driverCardNumber);
        newDriverStatement.execute();
		
        con.close();
	 }
	 catch (SQLException e)
	 {
		 System.out.print(e);
	 }
	
}


/**
 *  Determines if Driver is available to work. The number 1 for yes and 0 for no. 
 */
public void switchAvailability()
{
	
	//Scanner to read the availability.
	try(Connection con = Database.getConnection())
	{	
		Scanner avail = new Scanner(System.in);
		System.out.print("Are you available to work? Please type yes or no.\n");
		String availability = avail.next();
		
		PreparedStatement switchAvail = con.prepareStatement("UPDATE DRIVERS SET AVAILABILITY=?");
		
		
		// Checks to see if they typed a valid answer.
		while(!availability.equals("yes") && !availability.equals("no"))
		{
			System.out.print("That is an invalid answer, please type yes or no if you are available to work:\n");
			availability = avail.next();
		}
		
		//If they are available the variable changes to 1 if they aren't it is 0.
		if (availability.equals("yes"))
		{
			available = 1;
			switchAvail.setInt(1, available);
			switchAvail.execute();
		}
		if(availability.equals("no"))
		{
			available = 0;
			switchAvail.setInt(1, available);
			switchAvail.execute();
		}
		
		switchAvail.close();
		con.close();
	}
	catch (SQLException e) {
		
	}
	
}


/**
 * This will calculate how much the driver will get paid. 
 */
public void payDriver(double totalCharges)
{
	totalCharges = totalCharges*.8;

	try(Connection con = Database.getConnection())
	{
        PreparedStatement updatePaying = con.prepareStatement("UPDATE DRIVERS SET DriverEarningsarned = DriverEarningsarned +? WHERE DriverID=?");
        updatePaying.setDouble(1, totalCharges);
        updatePaying.setInt(2,driverID );
        updatePaying.execute();

        con.close();
    }
    catch (Exception e){
	    System.out.println(e);
    }
	System.out.print("You were paid $" + totalCharges + " for that ride.");
}


/**
 * Begins the drive once the rider has been picked up.
 */
public void beginDrive()
{
    //switches availability, in a perfect app this would also open the navigation app of the drivers choice
    try(Connection con = Database.getConnection())
    {
        PreparedStatement updateAvail = con.prepareStatement("UPDATE DRIVERS SET AVAILABILITY=0 WHERE DRIVERID=?");
        updateAvail.setInt(1,driverID);
        updateAvail.execute();
    }
    catch (Exception e){
        System.out.println(e);
    }
}


/**
 * Ends the drive once the rider has been dropped off.
 */
public void endDrive(int riderID, String startLocation, String endLocation,int riderId)
{

    //this is for our sake right now, creates a random distance so we don't have to poll Google Maps
    Random rand = new Random();
    distanceTraveled = rand.nextInt(27);

    //updates the Rides table
    try(Connection con = Database.getConnection()){

        PreparedStatement rideTableUpdate = con.prepareStatement("INSERT INTO RIDES (RiderID,DriverID,StartLocation,Destination,DistanceTraveled,MonetaryAmount) VALUES(?,?,?,?,?,?)");
        rideTableUpdate.setInt(1,riderID);
        rideTableUpdate.setInt(2,driverID);
        rideTableUpdate.setString(3, startLocation);
        rideTableUpdate.setString(4, endLocation);
        rideTableUpdate.setDouble(5, distanceTraveled);
        rideTableUpdate.setDouble(6,(distanceTraveled*.50));
        rideTableUpdate.execute();

        PreparedStatement updateAvail = con.prepareStatement("UPDATE DRIVERS SET AVAILABILITY=1 WHERE DRIVERID=?");
        updateAvail.setInt(1,driverID);
        updateAvail.execute();


    }
    catch (Exception e){
        System.out.println(e);
    }
    //these are sort of self explanatory
    if(available != 0)
    {
	rateRider(riderID);
	payDriver(distanceTraveled*.50);
    }
   
}

/**
 *  Login option for Drivers who already have an account.
 */
public void driverLogin()
{
	Scanner login = new Scanner(System.in);
	
	//These next few lines try to gather customer data and search a database for corresponding entries.
    System.out.println("Enter your email: ");
    driverEmail = login.next();

    System.out.println("Enter your password: ");
    String password = login.next();
    
    try (Connection con = Database.getConnection())
    {
    		String loginQuery = "SELECT Email, Card_Number, DriverID FROM DRIVERS WHERE Email=? AND PASSWORD=?";
    		
    		
    		//Sets up the driver's email and password
    		PreparedStatement loginSearch = con.prepareStatement(loginQuery);
    		loginSearch.setString(1,driverEmail);
    		loginSearch.setString(2, password);
    		ResultSet resultSet = loginSearch.executeQuery();
    		
    		//Checks to see if we actually have the driver's account information
    		while (!resultSet.next())
    		{
                 System.out.println("\n**INVALID EMAIL/PASSWORD. PLEASE TRY AGAIN**\n \nEnter your email: ");
                 driverEmail = login.next();

                 System.out.println("Enter your password: ");
                 password = login.next();

                 loginSearch.setString(1,driverEmail);
                 loginSearch.setString(2,password);
                 resultSet = loginSearch.executeQuery();
    		  }

    		  driverEmail=resultSet.getString(1);
              driverCardNumber = resultSet.getString(2);
              driverID = resultSet.getInt(3);

              System.out.println("\n**LOGIN SUCCESSFUL**"); 
    		  
    }
    catch(SQLException e)
    {
    		System.out.println(e);
    }
}


/**
 * Gives the driver a chance to rate their experience with the rider. 
 */
public void rateRider(int riderID)
{
	//Scanner to read the data typed.
	Scanner rating = new Scanner (System.in);
	System.out.print("What would you rate your experience with your rider? (Enter 1 to 5, 5 being fantastic and 1 being bad.)\n");
	int stars = rating.nextInt();
	
	//Checks to determine how good the rider was.
	if(stars == 5)
	{
		riderRating = 5;
	}
	else if(stars == 4)
	{
		riderRating = 4;
	}
	else if(stars == 3)
	{
		riderRating = 3;
	}
	else if(stars == 2)
	{
		riderRating = 2;
	}
	else
	{
		riderRating = 1;
	}
	try(Connection con = Database.getConnection()){
        PreparedStatement setRating = con.prepareStatement("UPDATE DRIVERS SET DriverRating=? WHERE DriverID =? ");
        setRating.setInt(1, riderRating);
        setRating.setInt(2,riderID);
        setRating.execute();

        con.close();
    }
    catch (Exception e ){
        System.out.println(e);
    }
}

public int getDriverID()
{
    return driverID;
}

}