package hw3.service.index;

import hw3.database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Description: index page
 * @Author: Guanchen Zhao
 * @Date: 2021/2/27
 */
public class IndexController implements Initializable {


    private DatabaseHandler databaseHandler;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    @FXML
    private void add() {

    }
}
