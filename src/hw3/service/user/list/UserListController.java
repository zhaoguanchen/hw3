package hw3.service.user.list;

import hw3.data.model.User;
import hw3.database.DataHelper;
import hw3.database.DatabaseHandler;
import hw3.service.user.add.UserAddController;
import hw3.util.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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


    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumn();
        loadData();
    }

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
    private void edit(ActionEvent event) {
        //Fetch the selected row
        User selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No member selected", "Please select a member for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/assistant/ui/addmember/member_add.fxml"));
            Parent parent = loader.load();

            UserAddController controller = (UserAddController) loader.getController();
//            controller.infalteUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Member");
            stage.setScene(new Scene(parent));
            stage.show();
//            LibraryAssistantUtil.setStageIcon(stage);

            stage.setOnHiding((e) -> {
                refresh(new ActionEvent());
            });

        } catch (IOException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void closeStage(ActionEvent event) {
        getStage().close();
    }


}
