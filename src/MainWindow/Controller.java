package MainWindow;

import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private Button btCustomer;
    @FXML
    private Button btContract;
    @FXML
    private Button btChart;
    @FXML
    private Button btCart;
    @FXML
    private Label labelMenu;
    @FXML
    private VBox overflowMenu;
    @FXML
    private Button btTest;
    private boolean pop = false;
    @FXML
    private Button btLogout;
    @FXML
    private Button btExit;


//    private Stage mainWindow;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        JFXRippler rippler = new JFXRippler(labelMenu);
        rippler.setMaskType(JFXRippler.RipplerMask.RECT);
        OpenPopup();
        LogOut();

    }
    public void LogOut(){
        if(pop == false){
            btLogout.setOnAction(e->{
                btLogout.getScene().getWindow().hide();
                Stage login = new Stage();
                Parent root;
                try{
                    root = FXMLLoader.load(getClass().getResource("/LoginUI/Login.fxml"));
                    login.setTitle("Phần mềm quản lý bãi đỗ xe =D");
                    login.getIcons().add(new Image("/Style/Images/car.png"));
                    Scene scene = new Scene(root);
                    login.setScene(scene);
                    login.show();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            });

        }
    }

    public void Exit(){
            Platform.exit();
    }

    public void OpenPopup() {
        btTest.setOnMouseClicked(e -> {
            pop = !pop;
            overflowMenu.setVisible(pop); //true
            System.out.println(pop);
        });

    }

    private void ChangeToAnotherWindow(String link) throws IOException {
        overflowMenu.getScene().getWindow().hide();
        Stage anotherWindow = new Stage();
        anotherWindow.setTitle("Phần mềm quản lý bãi đỗ xe =D");
        anotherWindow.getIcons().add(new Image("/Style/Images/car.png"));
        Parent root = FXMLLoader.load(getClass().getResource(link));
        Scene scene = new Scene(root);
        anotherWindow.setScene(scene);
        anotherWindow.show();
        anotherWindow.setResizable(false);
    }

    @FXML
    public void ChangeToCustomer(ActionEvent e) throws IOException {
        ChangeToAnotherWindow("/Customers/addCustomer.fxml");
    }
    @FXML
    public void ChangeToContract(ActionEvent e) throws IOException {
      ChangeToAnotherWindow("/Contract/Contract.fxml");
    }

    @FXML
    public void ChangeToAddContract(ActionEvent e) throws IOException {
        ChangeToAnotherWindow("/Contract/AddContract.fxml");
    }

    @FXML
    public void ChangeToStatisic(ActionEvent e) throws IOException {
        ChangeToAnotherWindow("/Statisic/Statisic.fxml");
    }

}
