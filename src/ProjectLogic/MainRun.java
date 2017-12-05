package ProjectLogic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class MainRun {

    public static void main(String args[]) throws
            SQLException, IOException, ClassNotFoundException, Exception
    {
      boolean running = true;
      while(running){
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
          System.out.println("\nPlease select: \n(1) I am a new Customer " +
                  "\n(2) I am a new Driver \n" +
                  "(3) I am a Previous customer that needs to login \n" +
                  "(4) I am a Previous driver that needs to login" +
                  "\n(5) Run Daily Reports\n" +
                  "(6) Exit the program");
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

              case 5:
                  DailyReport dailyReport = new DailyReport();
                  dailyReport.displayReport();
          }
      }
    }


}

