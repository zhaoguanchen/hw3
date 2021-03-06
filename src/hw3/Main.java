package hw3;

import hw3.database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @Description: main class.
 * @Author: Guanchen Zhao
 * @Date: 2021/2/27
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("service/login/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        // connect database
        new Thread(DatabaseHandler::getInstance).start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
