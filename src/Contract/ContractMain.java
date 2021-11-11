package Contract;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ContractMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage contractWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Contract.fxml"));
        Scene scene = new Scene(root, 1038, 631 );
        contractWindow.setScene(scene);
        contractWindow.show();
    }
}
