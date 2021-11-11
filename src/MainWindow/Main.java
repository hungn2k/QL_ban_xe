package MainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    public Stage mainWindow;
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root);
        mainWindow.setTitle("Phần mềm quản lý bãi đỗ xe =D");
        mainWindow.getIcons().add(new Image("/Style/Images/car.png"));
        mainWindow.setScene(scene);
        mainWindow.show();
        mainWindow.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
