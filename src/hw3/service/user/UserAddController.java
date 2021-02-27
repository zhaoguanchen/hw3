package hw3.service.user;

import hw3.database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Description: add User
 * @Author: Guanchen Zhao
 * @Date: 2021/2/27
 */
public class UserAddController implements Initializable {


    private DatabaseHandler databaseHandler;


    private Boolean isInEditMode = Boolean.FALSE;

    private final static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(UserAddController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    @FXML
    private void addUser(ActionEvent event) {

    }


}
