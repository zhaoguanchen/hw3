package hw3.service.login;

import hw3.database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @Description: Login Service
 * @Author: Guanchen Zhao
 * @Date: 2021/2/27
 */
public class LoginController implements Initializable {

    private final static Logger LOGGER = LogManager.getLogger(LoginController.class.getName());

    @FXML
    private TextField account;
    @FXML
    private TextField password;

    private DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
    }


    @FXML
    private void login(ActionEvent event) {

        System.out.println("account:" + account.getText());
        String accountStr = StringUtils.trimToEmpty(account.getText());
        String passwordStr = DigestUtils.shaHex(password.getText());

        if (checkUser(accountStr, passwordStr)) {
            closeStage();
            loadMain();
            LOGGER.log(Level.INFO, "User successfully logged in {}", account);
        } else {
            account.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
    }


    private Boolean checkUser(String account, String password) {
        try {
            LOGGER.log(Level.INFO, "checkUser  {}", account);
            String qu = "SELECT account,password FROM user where account = ?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(qu);
            stmt.setString(1, account);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String realPassword = rs.getString("password");
                if (password.equals(realPassword)) {
                    LOGGER.log(Level.INFO, "checkUser true {}", account);
                    return true;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    @FXML
    private void cancel(ActionEvent event) {
        System.exit(0);
    }

    private void closeStage() {
        ((Stage) account.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../index/index.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Index");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }


}
