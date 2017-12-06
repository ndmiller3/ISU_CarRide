package ProjectLogic;

import java.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Young Hyup Sohn
 *
 * updated 15.11.2017
 */

public class DailyReport{

    private static String url;
    private static String username;
    private static String password;

    public DailyReport(){

    }

    public void displayReport()throws IOException, SQLException
    {
        Scanner contin = new Scanner(System.in);
        System.out.println("**************WARNING*******************\n" +
                           "This will empty the current rides table.\n" +
                "Continue? (Y/N)\n");

        if(contin.next().equalsIgnoreCase("y")){
            FileWriter dailyStats = new FileWriter("Daily Stats.txt");
            PrintWriter writeStats = new PrintWriter(dailyStats);


            Connection connection = Database.getConnection();

            Statement stm = connection.createStatement();

            String sqlridesofday = "select * from RIDES"; //1

            ResultSet rs1= stm.executeQuery(sqlridesofday);

            String number_of_rides = "";

            String distance_travelled = "";

            while(rs1.next()){

                number_of_rides = rs1.getString("RideIdentification");

                distance_travelled = rs1.getString("DistanceTraveled");

            }

            System.out.println("Number of rides taken till now : "+number_of_rides);

            //writes to file
            writeStats.print("\nNumber of rides taken till now : "+number_of_rides+"\n");
            //writes to file
            writeStats.print("Total Distance travelled by driver : "+distance_travelled+"\n");

            System.out.println("Total Distance travelled by driver : "+distance_travelled);

            String sqlriderating = "select AVG(DriverRating), AVG(DriverEarningsarned) from DRIVERS"; //2

            ResultSet rs2= stm.executeQuery(sqlriderating);

            String driver_ratings = "";

            Double driver_earning=0.00;

            while(rs2.next()){
                driver_ratings = rs2.getString(1);
                driver_earning += rs2.getDouble(2);
            }

            System.out.println("Average Driver rating: "+ driver_ratings);
            //write stats to file
            writeStats.print("Average Driver rating: "+ driver_ratings+"\n");

            System.out.println("Total payout to the drivers : "+ driver_earning);
            //write stats to file
            writeStats.print("Total payout to the drivers : "+ driver_earning+"\n");

            dailyStats.close();
            writeStats.close();

            Statement emptyRides = connection.createStatement();
            emptyRides.execute("TRUNCATE DRIVERS");
        }
        if(contin.next().equalsIgnoreCase("n")){

        }
    }
}