package Alert;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlertController implements Initializable {

    @FXML
    private JFXButton btRefuse;

    @FXML
    private JFXButton btAccept;

    @FXML
    private Label lbTitle;

    @FXML
    public Boolean Accept(){
        btAccept.getScene().getWindow().hide();

        return true;
    }

    @FXML
    public Boolean Refuse(){
        btRefuse.getScene().getWindow().hide();

        return false;
    }
    public void Show(String title, String massage) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        Parent root = FXMLLoader.load(getClass().getResource("/Alert/alert.fxml"));
        Label label = (Label) root.lookup("#lbTitle");
        label.setText(massage);
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
