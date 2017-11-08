package ProjectLogic;

import java.util.Scanner;

public class Driver {
	private String driverName;
public Driver()
{
	
}

public void newDriver()
{
	Scanner driverData = new Scanner(System.in);
	
	System.out.print("What is your first name?\n");
	String firstName = driverData.next();
	
	System.out.print("What is your last name?\n");
	String lastName = driverData.next();
	
	driverName = firstName + " " + lastName;
	
	System.out.print("What is your age? (In years)\n");
	int age = driverData.nextInt();
	
	System.out.print("What is your email address?\n");
	String email = driverData.next();
	
	System.out.print("What is your password?\n");
	String password = driverData.next();
	
	System.out.print("What is your credit card number?\n");
	int cardNumber = driverData.nextInt();
	
	System.out.print("What is your car make?\n");
	String car = driverData.next();
	
	System.out.print("What is your car model?");
	String model = driverData.next();
	
	System.out.print("What is the year of your car?\n");
	int year = driverData.nextInt();
	
	System.out.print("What is your ride style? Please enter 1 for regular, 2 for Car Pool, 3 for Cy-Select.\n");
	String rideStyle = driverData.next();
	
	System.out.print("What is your license plate number?\n");
	String licensePlate = driverData.next();
}

public void switchAvailability()
{
	
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
	
}

}