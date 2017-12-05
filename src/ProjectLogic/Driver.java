package ProjectLogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//@authorname Nolan Miller

// Updated: 11-20-17

public class Driver {
	private String driverName; //Variable for driver name
	private int available; //Variable to check driver availability.
	private int riderRating; //Variable to store rider rating.
	private double dailyTotalMoney; // Variable to keep track how much the driver has earned.
	private String driverEmail; // Variable to hold Driver's email.
	private String driverCardNumber; // Variable to hold the Driver's credit card number
	private int driverID; // Variable to hold the Driver's ID Number.
	
	
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
	System.out.print("What is your age? (In years)\n");
	
	//Storing their age into a variable.
	int age = driverData.nextInt();
	
	//Checking to make sure they are an adult before becoming a driver
	if(age < 18)
	{
		System.out.print("Sorry, ALL Drivers must be at least 18 years of age. Goodbye!");
		return;
	}
	 
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
		System.out.print("That is not a valid card number, please reenter it now.\n");
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
	
	//Checks to see if the car is new enough to use. 
	if (year < 2007)
	{
		System.out.print("Your car is too old to be used, therefore you are not able to be a driver. Sorry!");
		return;
	}
	
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
		//If they are available the variable changes to 1 if they aren't it is 0.
		if (availability.equals("yes"))
		{
			available = 1;
			switchAvail.setInt(1, available);
			switchAvail.execute();
		}
		else
		{
			available = 0;
			available = 1;
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
	
	// TODO
	//DataBase BullShit
	
	totalCharges = totalCharges*.8;
}


/**
 * Begins the drive once the rider has been picked up.
 */
public void beginDrive()
{
	//TODO DATABASE BULLSHIT
	available = 0;
}


/**
 * Ends the drive once the rider has been dropped off.
 */
public void endDrive()
{
	
	//TODO DATABASE BULLSHIT
	available = 1;
	rateRider();
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
public void rateRider()
{
	//TODO DATABASE BULLSHIT
	
	
	//Scanner to read the data typed.
	Scanner rating = new Scanner (System.in);
	System.out.print("What would you rate your experience with your rider? (Enter 1 to 5 and 5 being the best and 1 being the worst.)\n");
	
	//Checks to determine how good the rider was.
	if(rating.next().equals("5"))
	{
		riderRating = 5;
	}
	else if(rating.next().equals("4"))
	{
		riderRating = 4;
	}
	else if(rating.next().equals("3"))
	{
		riderRating = 3;
	}
	else if(rating.next().equals("2"))
	{
		riderRating = 2;
	}
	else
	{
		riderRating = 1;
	}
	
}

}