package hw3.service.user.add;

import hw3.model.User;
import hw3.database.DataHelper;
import hw3.database.DatabaseHandler;
import hw3.util.AlertMaker;
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
import java.util.ResourceBundle;

/**
 * @Description: add User
 * @Author: Guanchen Zhao
 * @Date: 2021/2/27
 */
public class UserAddController implements Initializable {

    private final static Logger LOGGER = LogManager.getLogger(UserAddController.class.getName());


    private DatabaseHandler handler;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField account;
    @FXML
    private TextField phone;
    @FXML
    private TextField lab_training;
    @FXML
    private TextField PI_name;
    @FXML
    private TextField password;


    private Boolean isInEditMode = false;

    private Integer userId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseHandler.getInstance();
    }

    @FXML
    private void cancel(ActionEvent event) {
        closeStage();
    }


    private void closeStage() {
        getStage().close();
    }

    private Stage getStage() {
        return (Stage) account.getScene().getWindow();
    }


    void loadList() {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("../list/user_list.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("user list");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }

    @FXML
    private void save(ActionEvent event) {
        String nameStr = StringUtils.trimToEmpty(name.getText());
        String emailStr = StringUtils.trimToEmpty(email.getText());
        String accountStr = StringUtils.trimToEmpty(account.getText());
        String phoneStr = StringUtils.trimToEmpty(phone.getText());
        String labTrainingStr = StringUtils.trimToEmpty(lab_training.getText());
        String PINameStr = StringUtils.trimToEmpty(PI_name.getText());
        String passwordStr = DigestUtils.shaHex(StringUtils.trimToEmpty(password.getText()));

        if (nameStr.isEmpty() || accountStr.isEmpty() || phoneStr.isEmpty()) {
            AlertMaker.showSimpleAlert("Insufficient Data", "Please enter data in all fields.");
            return;
        }

        if (isInEditMode) {
            User newUser = generateUserEntity(accountStr, emailStr, nameStr, phoneStr, labTrainingStr, PINameStr, passwordStr);
            newUser.setUserId(userId);
            update(newUser);
            return;
        }

        if (DataHelper.accountIsExist(accountStr)) {
            AlertMaker.showSimpleAlert("Duplicate account", "User with same account exists.\nPlease use account");
            return;
        }

        User newUser = generateUserEntity(accountStr, emailStr, nameStr, phoneStr, labTrainingStr, PINameStr, passwordStr);
        boolean result = DataHelper.insertUser(newUser);
        if (result) {
            AlertMaker.showSimpleAlert("New user added", accountStr + " has been added");
            clearEntries();
        } else {
            AlertMaker.showSimpleAlert("Failed to add new user", "Check you entries and try again.");
        }
    }


    private User generateUserEntity(String account, String email, String name, String phone, String labTraining, String PIName, String password) {
        try {
            User user = new User();
            user.setAccount(account);
            user.setName(name);
            user.setEmail(email);
            user.setLab_training(labTraining);
            user.setPhone(phone);
            user.setPI_name(PIName);
            user.setPassword(password);
            return user;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "{}", e);
            return new User();
        }
    }

    public void infalteUI(User user) {
        name.setText(user.getName());
        account.setText(user.getAccount());
        PI_name.setText(user.getPI_name());
        password.setText(user.getPassword());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        lab_training.setText(user.getLab_training());
        userId = user.getUserId();
        isInEditMode = Boolean.TRUE;
    }

    private void clearEntries() {
        name.clear();
        account.clear();
        PI_name.clear();
        password.clear();
        phone.clear();
        email.clear();
        lab_training.clear();
    }

    private void update(User user) {
        boolean result = DataHelper.updateUser(user);
        if (result) {
            AlertMaker.showSimpleAlert("Success", "User data updated.");
        } else {
            AlertMaker.showSimpleAlert("Failed", "Cant update user.");
        }

    }


}
