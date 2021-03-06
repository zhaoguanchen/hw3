package hw3.database;

import hw3.model.User;
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

    public static boolean accountIsExist(String account) {
        try {
            String sql = "SELECT count(1) FROM user WHERE account = ?";
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, account);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean insertUser(User user) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO user(account, name, phone, email, lab_training, PI_name, password) VALUES(?,?,?,?,?,?,?)");
            statement.setString(1, user.getAccount());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getLab_training());
            statement.setString(6, user.getPI_name());
            statement.setString(7, user.getPassword());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static boolean updateUser(User user) {
        try {

            String update = "UPDATE user SET account=?, name=?,phone =?, email=?, lab_training=?, PI_name=?, password=? WHERE user_id=?";


            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    update);
            statement.setString(1, user.getAccount());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getLab_training());
            statement.setString(6, user.getPI_name());
            statement.setString(7, user.getPassword());
            statement.setInt(8, user.getUserId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }
}
