package LoginUI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import Alert.AlertController;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable{
    private String username = "nhom7";
    private String password = "123";
    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton loginButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
//        System.out.println("ooo");
    }
    @FXML
    public void Login(ActionEvent e) throws IOException {
        String usernameInput = usernameField.getText();
        String passwordInput = passwordField.getText();

        if(usernameInput.equals(this.username) && passwordInput.equals(this.password)){
            loginButton.getScene().getWindow().hide();
            Stage home = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/MainWindow/main.fxml"));
            home.setTitle("Phần mềm quản lý bãi đỗ xe =D");
            home.getIcons().add(new Image("/Style/Images/car.png"));
            Scene scene = new Scene(root);
            home.setScene(scene);
            home.show();
        }
        else{
            AlertController alert = new AlertController();
            alert.Show("Thông báo", "Tài khoản hoặc mật khẩu không đúng");

        }
    }
}


