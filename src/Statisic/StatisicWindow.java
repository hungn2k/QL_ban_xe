package Statisic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StatisicWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage statisicWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Statisic.fxml"));
        Scene scene = new Scene(root, 1045, 631 );
        statisicWindow.setTitle("Phần mềm quản lý bãi đỗ xe =D");
        statisicWindow.getIcons().add(new Image("/Style/Images/car.png"));
        statisicWindow.setScene(scene);
        statisicWindow.show();

    }
}
