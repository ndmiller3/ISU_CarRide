package ProjectLogic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class MainRun {

    public static void main(String args[]) throws
            SQLException, IOException, ClassNotFoundException
    {
        Rider rider=null;
        Driver driver=null;

        System.out.println("Going online...Please be patient\n");
        Database.init();
        System.out.println("You are now connected.\n");

        Scanner mainMenu = new Scanner (System.in);

        System.out.println("**************************************\n" +
                "*                                   *\n" +
                "* Welcome to ISU's Car Ride System! *\n" +
                "*                                   *" +
                "\n**************************************");
        System.out.println("\nPlease select if you are a: \n(1) new Customer \n(2) Driver \n(3) Previous customer that needs to login \n(4) Previous driver that needs to login");
        System.out.println("\nPlease type number of selection here: ");
        

        switch(mainMenu.nextInt())
        {
            case 1:
                rider = new Rider();
                rider.NewCustomer();

            case 2:
                driver = new Driver();
                driver.newDriver();
                driver.switchAvailability();
                driver.beginDrive();
                driver.endDrive(rider.getRiderID(),rider.getStartLocation(), rider.getDestination(), rider.getRiderID());
                
                // TODO Daniel what would be the correct value to put in for the 0?
                driver.payDriver(0);

            case 3:
                rider = new Rider();
                rider.customerLogin();
            	
            case 4:
            	Driver d = new Driver();
            	d.driverLogin();
            	d.switchAvailability();
            	d.beginDrive();
            	d.endDrive(rider.getRiderID(),rider.getStartLocation(), rider.getDestination(),rider.getRiderID());
            	
            	//TODO Daniel this is the same issue. 
            	d.payDriver(0);
        }
        if(rider!=null){
            //let's get some showing what a rider can do

        }

    }

}

