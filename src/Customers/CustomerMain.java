package Customers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public Stage customerWindow;
    @Override
    public void start(Stage primaryStage) throws IOException {
        customerWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("addCustomer.fxml"));
        Scene scene = new Scene(root, 1045, 631 );
        customerWindow.setScene(scene);
        customerWindow.show();
        customerWindow.setResizable(false);
    }
}
