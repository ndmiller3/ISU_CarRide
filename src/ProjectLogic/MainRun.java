package ProjectLogic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class MainRun {

    public static void main(String args[]) throws
            SQLException, IOException, ClassNotFoundException, Exception
    {
      boolean running = true;

          Rider rider= new Rider();
          Driver driver = new Driver();

          System.out.println("Going online...Please be patient\n");
          Database.init();
          System.out.println("You are now connected.\n");

          Scanner mainMenu = new Scanner (System.in);

          System.out.println("**************************************\n" +
                  "*                                   *\n" +
                  "* Welcome to ISU's Car Ride System! *\n" +
                  "*                                   *" +
                  "\n**************************************");
          String menuOptions = "\nPlease select: \n(1) I am a new Customer " +
                  "\n(2) I am a new Driver \n" +
                  "(3) I am a Previous customer that needs to login and call a ride\n" +
                  "(4) I am a Previous driver that needs to login" +
                  "\n(5) Run Daily Reports\n" +
                  "(6) Exit the program";

        do{
            System.out.println(menuOptions);
            System.out.println("\nPlease type number of selection here: ");

            while(!mainMenu.hasNextInt()){
                mainMenu.next();
                System.out.println(menuOptions);
                System.out.println("\nPlease type number of selection here: ");
            }
            int choicemainMenu=mainMenu.nextInt();

          switch(choicemainMenu)
          {
              //creates a new Rider for the database and calls a ride
              case 1:
                  rider = new Rider();
                  rider.NewCustomer();
                  System.out.println("Welcome to the ISU Car Ride System!\n");
                  rider.callRide();
                  break;

                  //creates for a new Driver for the database and gets a default ride
              case 2:
                  driver = new Driver();
                  driver.newDriver();
                  System.out.println("Welcome to the ISU Car Ride System!\n");
                  driver.driverLogin();
                  driver.switchAvailability();
                  driver.beginDrive();
                  driver.endDrive(rider.getRiderID(),rider.getStartLocation(), rider.getDestination(), rider.getRiderID());
                  break;

                  //logs in a new rider and calls a ride
              case 3:
                  rider = new Rider();
                  rider.customerLogin();
                  rider.callRide();
                  break;

                  //logs in a new driver and calls a default ride
              case 4:
                  Driver d = new Driver();
                  d.driverLogin();
                  d.switchAvailability();
                  d.beginDrive();
                  d.endDrive(rider.getRiderID(),rider.getStartLocation(), rider.getDestination(),rider.getRiderID());
                  break;

                  //daily reports
              case 5:
                  DailyReport dailyReport = new DailyReport();
                  dailyReport.displayReport();
                  break;

              case 6:
            	  System.out.print("Thank you for riding with the ISU Car Ride System!");
                  running = false;
                  break;

              default:
                  break;
          }
      }while(running);
    }


}

