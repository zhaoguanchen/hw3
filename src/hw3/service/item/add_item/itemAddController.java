package hw3.service.item.add_item;

import hw3.database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @Description: add item stage controller
 * @Author: Li Liao
 * @Date: 03/04/2021
 */

public class itemAddController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = DatabaseHandler.getInstance();
    }
    // private final static Logger logger = Logger.getLogger(itemController.class.getName());

    private DatabaseHandler handler;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtUnit;
    @FXML
    private TextField txtManufacturer;
    @FXML
    private Button save;
    @FXML
    private Button back;

    @FXML
    private void backButtonPressed(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void saveButtonPressed(ActionEvent event) throws SQLException {
        String name = txtName.getText();
        String unit = txtUnit.getText();
        String manufacturer = txtManufacturer.getText();
        System.out.println(name);
        if (name.isEmpty() || unit.isEmpty() || manufacturer.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insufficient Data");
            alert.setHeaderText(null);
            alert.setContentText("Please enter data in all fields.");
            alert.showAndWait();
        }
        DatabaseHandler handler = DatabaseHandler.getInstance();
        //String update = "INSERT INTO test (name, unit, manufacturer) VALUES (" + name +", " + unit + ", " + unit + ");";
        String update = String.format(
                "INSERT INTO Items (name, unit, manufacturer) VALUES (\"%s\", \"%s\", \"%s\");",
                name,
                unit,
                manufacturer);
        handler.execUpdate(update);
    }

}
