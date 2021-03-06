package hw3.service.index;

import hw3.database.DatabaseHandler;
import hw3.service.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Description: index page
 * @Author: Guanchen Zhao
 * @Date: 2021/2/27
 */
public class IndexController implements Initializable {
    private final static Logger LOGGER = LogManager.getLogger(IndexController.class.getName());


    private DatabaseHandler databaseHandler;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    @FXML
    private void exit() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Login");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void toInventory() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../inventory/InventoryScene.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Inventory");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }

    @FXML
    private void toUser() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../user/list/user_list.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("User");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }

    @FXML
    private void toItem() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../item/list/item.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Item");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }


}
