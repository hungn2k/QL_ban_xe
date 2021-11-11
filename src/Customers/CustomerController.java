package Customers;

import Alert.AlertController;
import Date2String.Convert;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    //Thuôc tính FXML và các hàm của nó
    @FXML
    private TableColumn<Object, Object> colCustEmail;

    @FXML
    private JFXTextField txtPhoneNo;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private TableColumn<Object, Object> colCustReg;

    @FXML
    private JFXTextField txtcustID;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<Object, Object> colCustAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private TableColumn<Object, Object> colCustName;

    @FXML
    private JFXTextField txtcustName;

    @FXML
    private JFXTextField txtcustAddress;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTextField txtAge;

    @FXML
    private TableColumn<Object, Object> colAge;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXDatePicker txtRegDate;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView btBack;

    @FXML
    private TableColumn<Object, Object> colCustPhone;

    @FXML
    private TableColumn<Object, Object> colCustID;

    @FXML
    private Label lbCheck;

    @FXML
    private Label lbChoose;
    //Tạo List khách hàng
    private ObservableList customersList;
    //Load dữ liệu
    private void LoadAllCustomers(){
        try{
            tblCustomer.setItems(customersList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Kiểm tra các textfield có rỗng hay không
    private boolean checkAllField(){
        if(txtcustName.getText().isBlank() || txtRegDate.getValue().toString().isBlank() || txtRegDate.getValue().toString().isEmpty()|| txtAge.getText().isBlank() || txtcustAddress.getText().isBlank() || txtPhoneNo.getText().isBlank() ||txtcustID.getText().isBlank()){
            return false;
        }
        return true;
    }
    //Xóa dữ liệu trong textfield
    private void ResetInfo(){
        txtcustName.setText("");
        txtEmail.setText("");
        txtRegDate.setValue(null);
        txtAge.setText("");
        txtcustAddress.setText("");
        txtPhoneNo.setText("");
        txtcustID.setText("");
    }
    //Thêm khách hàng
    @FXML
    private void AddCustomers(ActionEvent e) throws InterruptedException {
        if(!checkAllField()){
            lbCheck.setVisible(true);
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.seconds(2));
            pt.setOnFinished(ex->{
                lbCheck.setVisible(false);
            });
            pt.play();

            return;
        }
        if(checkAllField()){
            Convert convert = new Convert();
            Customer customer = new Customer();
            customer.setID(txtcustID.getText());
            customer.setName(txtcustName.getText());
            customer.setAddress(txtcustAddress.getText());
            customer.setAge(txtAge.getText());
            customer.setPhoneNumber(txtPhoneNo.getText());
            customer.setEmail(txtEmail.getText());
            customer.setDateSignUp(convert.Date2String(txtRegDate.getValue()));
//            Customer customer = new Customer(
//                    txtcustID.getText(), txtcustName.getText(), txtPhoneNo.getText(), txtAge.getText(), txtcustAddress.getText(), txtEmail.getText(), String.valueOf(txtRegDate.getValue())
//            );
            customersList.add(customer);
            LoadAllCustomers();
            ResetInfo();
        }

    }
    //Xóa khách hàng
    @FXML
    private void DeleteCustomer(ActionEvent e) throws IOException {

        Customer selected = tblCustomer.getSelectionModel().getSelectedItem();
        AlertController alert = new AlertController();
        customersList.remove(selected);
        ResetInfo();

    }
    //Cập nhật dữ liệu
    @FXML
    private void UpdateCustomer(ActionEvent e){
        Customer selected = tblCustomer.getSelectionModel().getSelectedItem();

        if(selected == null){
            lbChoose.setVisible(true);
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.seconds(2));
            pt.setOnFinished(ex->{
                lbChoose.setVisible(false);
            });
            pt.play();
            return;
        }

        else {
            Convert convert = new Convert();

            customersList.remove(selected);
            selected.setPhoneNumber(txtPhoneNo.getText());
            selected.setDateSignUp(convert.Date2String(txtRegDate.getValue()));
            selected.setEmail(txtEmail.getText());
            selected.setAge(txtAge.getText());
            selected.setAddress(txtcustAddress.getText());
            selected.setName(txtcustName.getText());
            selected.setID(txtcustID.getText());
            customersList.add(selected);

            ResetInfo();
        }

    }
    //Binding dữ liệu
    private void BindingData(){
        tblCustomer.setOnMouseClicked(e->{
            Customer customer = tblCustomer.getItems().get(tblCustomer.getSelectionModel().getSelectedIndex());
            txtcustID.setText(customer.getID());
            txtPhoneNo.setText(customer.getPhoneNumber());
            txtcustAddress.setText(customer.getAddress());
            txtAge.setText(customer.getAge());
            txtRegDate.setValue(CustomerInfo.ChangeStringToLocalDate(customer.getDateSignUp()));
            txtEmail.setText(customer.getEmail());
            txtcustName.setText(customer.getName());
        });
    }

    //Override initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        customersList = CustomerInfo.ReturnCustomer(); // Trả về danh sách khách hàng
        Convert convert = new Convert();
        convert.SetFormatterForDate(txtRegDate);
        //Dùng Filtered list cho việc tìm kiếm
        FilteredList<Customer> filteredList = new FilteredList<>(customersList, e->true);

        //Set dữ liệu cho các cột của Tableview
        colCustID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCustReg.setCellValueFactory(new PropertyValueFactory<>("dateSignUp"));
        BindingData();
        LoadAllCustomers();
        //Chức năng tìm kiếm
        searchField.setOnKeyReleased(e->{
            searchField.textProperty().addListener(((observableValue, s, t1) -> {
                filteredList.setPredicate(customer->{
                    if(t1 == null || t1.isEmpty()){
                        return true;
                    }
                    String lowerCase = t1.toLowerCase();
                    if(customer.getID().contains(t1)){
                        return true;
                    }
                    else if(customer.getAddress().toLowerCase().indexOf(lowerCase) != -1){
                        return true;
                    }
                    else if(customer.getAge().toLowerCase().indexOf(lowerCase) != -1){
                        return true;
                    }
                    else if(customer.getPhoneNumber().indexOf(lowerCase) != -1){
                        return true;
                    }
                    else if(customer.getEmail().toLowerCase().indexOf(lowerCase) != -1){
                        return true;
                    }
                    else if(customer.getName().toLowerCase().indexOf(lowerCase) != -1){
                        return true;
                    }
                    return false;
                });
            }));
            SortedList<Customer> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tblCustomer.comparatorProperty());
            tblCustomer.setItems(sortedList);
        });

        //Quay lại
        btBack.setOnMouseClicked(e->{
            Stage mainWindow = new Stage();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/MainWindow/main.fxml"));
                Scene scene = new Scene(root);
                mainWindow.setScene(scene);
                tblCustomer.getScene().getWindow().hide();
                mainWindow.setTitle("Phần mềm quản lý bãi đỗ xe =D");
                mainWindow.getIcons().add(new Image("/Style/Images/car.png"));
                mainWindow.show();
                mainWindow.setResizable(false);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }
}

