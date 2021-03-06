package hw3.service.inventory;

import hw3.database.DatabaseHandler;
import hw3.model.InventoryItem;
import hw3.model.Item;
import hw3.service.user.add.UserAddController;
import hw3.util.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    private final ObservableList<InventoryItem> list = FXCollections.observableArrayList();

    @FXML
    private TableView<InventoryItem> inventoryTableView;
    @FXML
    private TableColumn<InventoryItem, String> inventoryID;
    @FXML
    private TableColumn<InventoryItem, Integer> entityID;
    @FXML
    private TableColumn<InventoryItem, String> entityName;
    @FXML
    private TableColumn<InventoryItem, String> unit;
    @FXML
    private TableColumn<InventoryItem, String> manufacturer;
    @FXML
    private TableColumn<InventoryItem, Integer> quantity;
    @FXML
    private TableColumn<InventoryItem, String> expiry;
    @FXML
    private TableColumn<InventoryItem, String> ownerID;
    @FXML
    private TableColumn<InventoryItem, String> owner;
    @FXML
    private TableColumn<InventoryItem, String> phone;
    @FXML
    private TableColumn<InventoryItem, String> email;
    @FXML
    private TextField addInventoryID;
    @FXML
    private TextField addEntityID;
    @FXML
    private TextField addExpiry;
    @FXML
    private TextField addQuantity;
    @FXML
    private TextField addOwnerID;

    private final static Logger LOGGER = LogManager.getLogger(InventoryController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumn();
        listAll();
    }

    private void initColumn() {
        inventoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        inventoryID.setCellValueFactory(new PropertyValueFactory<>("inventoryID"));
        entityID.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("entityID"));
        entityName.setCellValueFactory(new PropertyValueFactory<>("entityName"));
        unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        expiry.setCellValueFactory(new PropertyValueFactory<>("expiry"));
        ownerID.setCellValueFactory(new PropertyValueFactory<>("ownerID"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        entityID.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        entityID.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<InventoryItem, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<InventoryItem, Integer> event) {
                InventoryItem inventoryItem = event.getRowValue();
                Integer newValue = event.getNewValue();
                Integer inventoryID = inventoryItem.getInventoryID();
                if(update(inventoryID, "entityID", newValue.toString())) {
                    DatabaseHandler handler = DatabaseHandler.getInstance();
                    String qu = "SELECT * FROM Items WHERE itemId = " + newValue + ";";
                    ResultSet rs = handler.execQuery(qu);
                    Item item = generateItem(rs);
                    inventoryItem.setEntityID(newValue);
                    inventoryItem.setEntityName(item.getName());
                    inventoryItem.setUnit(item.getUnit());
                    inventoryItem.setManufacturer(item.getManufacturer());
                    inventoryTableView.refresh();
                }
            }
        });

        expiry.setCellFactory(TextFieldTableCell.forTableColumn());
        expiry.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<InventoryItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<InventoryItem, String> event) {
                InventoryItem inventoryItem = event.getRowValue();
                String newValue = "\"" + event.getNewValue() + "\"";
                Integer inventoryID = inventoryItem.getInventoryID();
                if(update(inventoryID, "expiry", newValue)) inventoryItem.setExpiry(newValue);
            }
        });

        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<InventoryItem, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<InventoryItem, Integer> event) {
                InventoryItem inventoryItem = event.getRowValue();
                Integer newValue = event.getNewValue();
                Integer inventoryID = inventoryItem.getInventoryID();
                if(update(inventoryID, "quantity", newValue.toString())) inventoryItem.setQuantity(newValue);
            }
        });


    }

    @FXML
    private void listAll() {
        list.clear();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT Inventory.inventoryID, " +
                "Inventory.entityID, Items.name as entityName, Items.unit, Items.manufacturer, " +
                "Inventory.quantity, Inventory.expiry, " +
                "Inventory.ownerID, user.name as owner, user.phone, user.email " +
                "FROM Inventory, Items, user " +
                "WHERE Inventory.entityID = Items.itemId " +
                "AND Inventory.ownerID = user.user_id;";
        ResultSet rs = handler.execQuery(qu);
        if(rs == null) return;
        generateInventoryItemList(rs);
        inventoryTableView.setItems(list);
    }

    @FXML
    private void deleteSelected() {
        ObservableList<InventoryItem> selectedInventoryItems = inventoryTableView.getSelectionModel().getSelectedItems();
        if (selectedInventoryItems.isEmpty()) {
            System.out.println("No items are selected, cannot delete anything.\n");
            return;
        }
        StringBuilder selectedInventoryID = new StringBuilder();
        for(InventoryItem inventoryItem : selectedInventoryItems) {
            selectedInventoryID.append(inventoryItem.getInventoryID()).append(", ");
        }
        selectedInventoryID.setLength(selectedInventoryID.length() - 2);
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "DELETE FROM Inventory WHERE inventoryID IN (" + selectedInventoryID.toString() + ");";
        if(handler.execAction(qu)) {
            if (list.size() == selectedInventoryItems.size()) {
                list.clear();
            } else {
                selectedInventoryItems.forEach(list::remove);
            }
        }
    }

    @FXML
    private void add() {
        String entityID = addEntityID.getText();
        String expiry = addExpiry.getText();
        String quantity = addQuantity.getText();
        String ownerID = addOwnerID.getText();
        if (entityID.isEmpty() || expiry.isEmpty() || quantity.isEmpty() || ownerID.isEmpty()) {
            String title = "Insufficient Data.";
            String content = "Please enter data in EntityID, Expiry, Quantity and OwnerID.";
            AlertMaker.showSimpleAlert(title, content);
        }
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "INSERT INTO Inventory (entityID, quantity, expiry, ownerID) VALUES ("
                + entityID + ", "
                + quantity + ", "
                + "\"" + expiry + "\", "
                + ownerID + ");";
        if(handler.execAction(qu)) {
            String q = "SELECT inventoryID FROM Inventory WHERE"
                    + " entityID = " + entityID + " AND"
                    + " expiry = " + "\"" + expiry + "\" AND"
                    + " ownerID = " + ownerID + ";";
            ResultSet rs = handler.execQuery(q);
            String inventoryID = generateInventoryID(rs);
            addInventoryID.setText(inventoryID);
        }
    }

    private boolean update(Integer inventoryID, String column, String newValue) {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "UPDATE Inventory SET " + column + " = " + newValue + " WHERE inventoryID = " + inventoryID + ";";
        return handler.execAction(qu);
    }

    private void generateInventoryItemList(ResultSet rs) {
        try {
            while (rs.next()) {
                InventoryItem inventoryItem = new InventoryItem();
                inventoryItem.setInventoryID(rs.getInt("inventoryID"));
                inventoryItem.setEntityID(rs.getInt("entityID"));
                inventoryItem.setEntityName(rs.getString("entityName"));
                inventoryItem.setUnit(rs.getString("unit"));
                inventoryItem.setManufacturer(rs.getString("manufacturer"));
                inventoryItem.setQuantity(rs.getInt("quantity"));
                inventoryItem.setExpiry(rs.getString("expiry"));
                inventoryItem.setOwnerID(rs.getInt("ownerID"));
                inventoryItem.setOwner(rs.getString("owner"));
                inventoryItem.setPhone(rs.getString("phone"));
                inventoryItem.setEmail(rs.getString("email"));
                list.add(inventoryItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Item generateItem(ResultSet rs) {
        try {
            rs.next();
            Item item = new Item();
            item.setItemId(rs.getInt("itemId"));
            item.setName(rs.getString("name"));
            item.setUnit(rs.getString("unit"));
            item.setManufacturer(rs.getString("manufacturer"));
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return new Item();
        }
    }


    private String generateInventoryID(ResultSet rs) {
        try {
            rs.next();
            return String.valueOf(rs.getInt("inventoryID"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private Stage getStage() {
        return (Stage) inventoryTableView.getScene().getWindow();
    }

    private void closeStage() {
        getStage().close();
    }


    @FXML
    private void returnToIndex(ActionEvent event) {
        closeStage();
    }
}
