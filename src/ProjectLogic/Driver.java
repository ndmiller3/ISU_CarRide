package ProjectLogic;

import java.util.Scanner;

//@authorname Nolan Miller

// Updated: 11-9-17

public class Driver {
	private String driverName; //Variable for driver name
	private int currentYear = 2017; //Variable for the current year.
	private int available; //Variable to check driver availability.
	private int riderRating; //Variable to store rider rating.
public Driver()
{
	
}

public void newDriver()
{
	//Scanner to read all of the input
	Scanner driverData = new Scanner(System.in);
	
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
	
	//Checking to make sure they are an adult before becoming a driver
	//if(driverData.nextInt() < 18)
	//{
	//	System.out.print("Sorry, ALL Drivers must be at least 18 years of age. Goodbye!");
	//	return;
	//}
	
	//Storing their age into a variable.
	 int age = driverData.nextInt();
	
	//Asking for their email address
	System.out.print("What is your email address?\n");
	String email = driverData.next();
	
	//Asking for a desired password.
	System.out.print("What is your password?\n");
	String password = driverData.next();
	
	//Asking for their credit card number.
	System.out.print("What is your credit card number? (Please don't use spaces)\n");
	
	//Checks the length of the card number
	//if(driverData.next().length() != 16)
	//{
	//	System.out.print("That is not a valid card number, please reenter it now.\n");
	//}
	
	int cardNumber = driverData.nextInt();
	
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
	if (currentYear - year > 10)
	{
		System.out.print("Your car is too old to be used, therefore you are not able to be a driver. Sorry!");
	}
	
	//Asks what type or ride style they want.
	System.out.print("What is your ride style? Please enter 1 for regular, 2 for Car Pool, 3 for Cy-Select.\n");
	String rideStyle = driverData.next();
	
	//Asks for the license plate number.
	System.out.print("What is your license plate number?\n");
	String licensePlate = driverData.next();
	
	//Closes the scanner.
	driverData.close();
}

public void switchAvailability()
{
	//Scanner to read the availability. 
	Scanner avail = new Scanner (System.in);
	System.out.print("Are you available to work? Please type yes or no.\n");
	
	//If they are available the variable changes to 1 if they aren't it is 0.
	if (avail.next().equals("yes"))
	{
		available = 1;
	}
	else
	{
		available = 0;
	}
	
	//Closes the scanner.
	avail.close();
}

public void payDriver()
{
	
}

public void beginDrive()
{
	
}

public void endDrive()
{
	
}

public void rateRider()
{
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
	
	//Closes the scanner. 
	rating.close();
}

}