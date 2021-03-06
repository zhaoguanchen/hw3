package hw3.service.user.list;

import hw3.model.User;
import hw3.database.DataHelper;
import hw3.database.DatabaseHandler;
import hw3.service.user.add.UserAddController;
import hw3.util.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * @Description: list User
 * @Author: Guanchen Zhao
 * @Date: 2021/2/28
 */
public class UserListController implements Initializable {
    private final static Logger logger = LogManager.getLogger(UserListController.class.getName());

    private final ObservableList<User> list = FXCollections.observableArrayList();

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> userId;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> account;
    @FXML
    private TableColumn<User, String> phone;
    @FXML
    private TableColumn<User, String> lab_training;
    @FXML
    private TableColumn<User, String> PI_name;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumn();
        loadData();
    }

    /**
     * @Author: Guanchen Zhao
     * @Description: initialize table column
     * @Date: 3:04 下午 2021/3/2
     **/
    private void initColumn() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        account.setCellValueFactory(new PropertyValueFactory<>("account"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        lab_training.setCellValueFactory(new PropertyValueFactory<>("lab_training"));
        PI_name.setCellValueFactory(new PropertyValueFactory<>("PI_name"));
    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    /**
     * @Author: Guanchen Zhao
     * @Description: load table data
     * @Date: 3:04 下午 2021/3/2
     **/
    private void loadData() {
        list.clear();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT user_id, account, name, email, lab_training, phone,PI_name FROM user";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                User user = generateUserEntity(rs);
                list.add(user);
            }
        } catch (SQLException ex) {
            logger.error("generate user error. {0}", ex);
        }

        tableView.setItems(list);

    }


    private User generateUserEntity(ResultSet rs) {
        try {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setAccount(rs.getString("account"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setLab_training(rs.getString("lab_training"));
            user.setPhone(rs.getString("phone"));
            user.setPI_name(rs.getString("PI_name"));
            return user;
        } catch (Exception e) {
            return new User();
        }
    }

    /**
     * @Author: Guanchen Zhao
     * @Description: deal delete process
     * @Date: 3:05 下午 2021/3/2
     **/
    @FXML
    private void delete(ActionEvent event) {
        //Fetch the selected row
        User selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No member selected", "Please select a member for deletion.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting book");
        alert.setContentText("Are you sure want to delete " + selectedForDeletion.getName() + " ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            boolean result = DataHelper.deleteUser(selectedForDeletion.getUserId());
            if (result) {
                AlertMaker.showSimpleAlert("Book deleted", selectedForDeletion.getName() + " was deleted successfully.");
                list.remove(selectedForDeletion);
            } else {
                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getName() + " could not be deleted");
            }
        } else {
            AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
        }
    }

    @FXML
    private void refresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/user_add.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("add User");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding((e) -> {
                refresh(new ActionEvent());
            });

        } catch (IOException ex) {
            logger.error("", ex);
        }
    }

    @FXML
    private void edit(ActionEvent event) {
        //Fetch the selected row
        User selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No user selected", "Please select a user for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/user_add.fxml"));
            Parent parent = loader.load();
            UserAddController controller = loader.getController();
            controller.infalteUI(selectedForEdit);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit User");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding((e) -> {
                refresh(new ActionEvent());
            });

        } catch (IOException ex) {
            logger.error("", ex);
        }
    }


    private void closeStage() {
        getStage().close();
    }


    @FXML
    private void returnToIndex(ActionEvent event) {
        closeStage();
        loadIndex();
    }

    void loadIndex() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../../index/index.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("index");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            logger.log(Level.ERROR, "{}", ex);
        }
    }

}
