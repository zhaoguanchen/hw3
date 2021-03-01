package hw3.database;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

 /**
  * @Description: manege sql scripts
  * @Author: Guanchen Zhao
  * @Date: 2021/2/27
  */
public class DataHelper {

    private final static Logger LOGGER = LogManager.getLogger(DatabaseHandler.class.getName());


    public static boolean test() {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "SELECT count(*) FROM user ");
            LOGGER.info("test");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(count);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }



     public static boolean list() {
         try {
             PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                     "SELECT count(*) FROM user ");
             LOGGER.info("test");
             ResultSet rs = statement.executeQuery();
             if (rs.next()) {
                 int count = rs.getInt(1);
                 System.out.println(count);
                 return (count > 0);
             }
         } catch (SQLException ex) {
             LOGGER.log(Level.ERROR, "{}", ex);
         }
         return false;
     }

     public static boolean deleteUser(Integer userId) {
         try {
             PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                     "DELETE FROM user WHERE user_id = ?   ");
             statement.setString(1, String.valueOf(userId));
             int res = statement.executeUpdate();
             if (res == 1) {
                 return true;
             }

         } catch (SQLException ex) {
             LOGGER.log(Level.ERROR, "{}", ex);
         }
         return false;
     }

 }
