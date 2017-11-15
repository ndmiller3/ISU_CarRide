package ProjectLogic;

import java.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

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

    public void displayReport()throws Exception{

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

        System.out.println("Number of rides taken till now :"+number_of_rides);

        System.out.println("Distance travelled by driver :"+distance_travelled);

        String sqlriderating = "select * from DRIVERS"; //2

        ResultSet rs2= stm.executeQuery(sqlriderating);

        String driver_ratings = "";

        String driver_earning = "";

        while(rs2.next()){

            driver_ratings = rs2.getString("DriverRating");

            driver_earning = rs2.getString("DriverEarningsarned");

        }

        System.out.println("Driver's rating till now :"+driver_ratings);

        System.out.println("Total payout to the drivers :"+driver_earning);

    }

}