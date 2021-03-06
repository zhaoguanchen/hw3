package hw3.service.item.add_item;

import hw3.database.DataHelper;
import hw3.database.DatabaseHandler;
import hw3.model.Item;
import hw3.model.User;
import hw3.util.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;

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


    private Boolean isInEditMode = false;


    private Integer itemId;

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

        if (isInEditMode) {
            Item item = new Item();
            item.setItemId(itemId);
            item.setName(name);
            item.setManufacturer(manufacturer);
            item.setUnit(unit);
            update(item);
            return;
        }
        DatabaseHandler handler = DatabaseHandler.getInstance();
        //String update = "INSERT INTO test (name, unit, manufacturer) VALUES (" + name +", " + unit + ", " + unit + ");";
        String update = String.format(
                "INSERT INTO Items (name, unit, manufacturer) VALUES (\"%s\", \"%s\", \"%s\");",
                name,
                unit,
                manufacturer);
        handler.execUpdate(update);
        AlertMaker.showSimpleAlert("Success", "item data added.");
    }

    private void update(Item item) {
        boolean result = DataHelper.updateItem(item);
        if (result) {
            AlertMaker.showSimpleAlert("Success", "item data updated.");
        } else {
            AlertMaker.showSimpleAlert("Failed", "Cant update item.");
        }

    }

    public void infalteUI(Item item) {
        txtName.setText(item.getName());
        txtUnit.setText(item.getUnit());
        txtManufacturer.setText(item.getManufacturer());
        itemId = item.getItemId();
        isInEditMode = Boolean.TRUE;
    }

    private void clearEntries() {
        txtName.clear();
        txtUnit.clear();
        txtManufacturer.clear();
    }



}
