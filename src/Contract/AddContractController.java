package Contract;
import CONST.CONST;
import Customers.Customer;
import Customers.CustomerInfo;
import Date2String.Convert;
import ParkingLot.Car;
import ParkingLot.Truck;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AddContractController implements Initializable {
    //Các thuộc tính FXML
    @FXML
    private JFXButton btAdd;

    @FXML
    private JFXTextField txtWeight;

    @FXML
    private JFXTextField txtNameInfo;

    @FXML
    private JFXTextField txtInfoID;

    @FXML
    private JFXTextField txtOutputInfo;

    @FXML
    private JFXTextField txtCusInfo;

    @FXML
    private JFXTextField txtLienceInfo;

    @FXML
    private JFXTextField txtPricePerMonth;

    @FXML
    private JFXTextField txtInputInfo;

    @FXML
    private JFXTextField txtTotal;

    @FXML
    private JFXTextField txtGroupInfo;

    @FXML
    private Label lbCheckDate;

    @FXML
    private JFXComboBox cbGroup;

    @FXML
    private ImageView btBack;

    @FXML
    private JFXDatePicker dpInputInfo;
    @FXML
    private JFXDatePicker dpOutputInfo;

    //Hàm thêm hợp đồng
    private void AddContract(String name, String lience, LocalDate inputDate, LocalDate outputDate, double weight){
        Contract newContract = new Contract();
        customerList = CustomerInfo.ReturnCustomer(); // Trả về danh sách khách hàng
        //Chọn khách hàng trước rồi mới chọn hợp đồng.
        Customer customer = new Customer();
        for (Customer cus: customerList){
            if (cus.getID().equals(idCustomer)) customer = cus;
        }

        // Nếu như combobox là "Xe con" thì...
        if(cbGroup.getValue() == CONST.SUBCAR){
            Car newCar = Car.CreateCar(customer, name, lience, 0.0);
            newContract = new Contract(txtInfoID.getText(), inputDate, outputDate, newCar, CONST.SUBCAR);
            contractList.add(newContract);
        //Ngược lại thì
        }else if(cbGroup.getValue() == CONST.TRUCK){
            Truck newTruck = Truck.CreateTruck(customer, name, lience, weight);
            newContract = new Contract(txtInfoID.getText(), inputDate, outputDate, newTruck, CONST.TRUCK);
            contractList.add(newContract);
        }
    }

    private double weightForTruck; // Trọng lượng xe tải
    private double priceForSubCar; //1,000,000
    private double priceForTruck; //700,000
    private double discountForCar = CONST.DISCOUNT_FOR_SUBCAR;
    private double discountforTruck = CONST.DISCOUNT_FOR_TRUCK;
    private String idCustomer;
    private long month;

    private ObservableList<Customer> customerList; // Danh sách khách hàng
    private ObservableList<Contract> contractList; //Danh sách hợp đồng
    private ObservableList<String> listGroupCar = FXCollections.observableArrayList(CONST.SUBCAR, CONST.TRUCK);
    private void Listener(){
        //Hàm dùng để binding dữ liệu
        //Thay đổi dữ liệu khi thay đổi dữ liệu của 1 thuộc tính khác
        priceForSubCar = CONST.PRICE_PER_MONTH_SUBCAR;
        priceForTruck = CONST.PRICE_PER_MONTH_TRUCK;
        try{
            weightForTruck = Double.parseDouble(txtWeight.getText());
        }catch (NumberFormatException e){}

        Convert convert = new Convert();

        month = ChronoUnit.MONTHS.between(dpInputInfo.getValue(), dpOutputInfo.getValue());

        /*Xử lý điều kiện:
        - Nếu như Xe con và < 5 năm và > 5 năm thì...
        - Nếu như Xe tải với các điều kiện đề cho thì...
        */
        if (cbGroup.getValue() == CONST.SUBCAR && month < CONST.MONTH_INPUT){
            txtWeight.setText("0.0");
            txtWeight.setDisable(true);
            String newPriceCar = convert.Currency(priceForSubCar);
            txtPricePerMonth.setText(newPriceCar);
            String total = convert.Currency(priceForSubCar * month);
            txtTotal.setText(total);
        }
        else if(cbGroup.getValue() == CONST.SUBCAR && month >= CONST.MONTH_INPUT){
            txtWeight.setText("0.0");
            txtWeight.setDisable(true);
            priceForSubCar *= (1 - discountForCar);
            String newPriceCar = convert.Currency(priceForSubCar);
            txtPricePerMonth.setText(newPriceCar);
            String total = convert.Currency(priceForSubCar * month);
            txtTotal.setText(total);
        }
        else if (cbGroup.getValue() == CONST.TRUCK && month < CONST.MONTH_INPUT){
            txtWeight.setDisable(false);
            priceForTruck *= weightForTruck;
            txtPricePerMonth.setText(convert.Currency(priceForTruck));
            String total = convert.Currency(priceForTruck * month);
            txtTotal.setText(total);
        }
        else if (cbGroup.getValue() == CONST.TRUCK && month >= CONST.MONTH_INPUT){
            txtWeight.setDisable(false);
            priceForTruck *= weightForTruck;
            priceForTruck *=  (1 - discountforTruck);
            txtPricePerMonth.setText(convert.Currency(priceForTruck));
            String total = convert.Currency(priceForTruck * month);
            txtTotal.setText(total);
        }
    }
    //Hàm kiểm tra các Field trước khi submit
    private void ValidateInputAfterSubmit(JFXTextField forValid){

        RequiredFieldValidator validator = new RequiredFieldValidator();
        forValid.getValidators().add(validator);
        validator.setMessage("Dòng này không được để trống");
        forValid.validate();

    }

    private AnchorPane ShowContractWindow(){
        Stage contractWindow = new Stage();
        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Contract/Contract.fxml"));

        } catch (IOException ex) {
            //
        }
        Scene scene = new Scene(root);
        contractWindow.setScene(scene);
        contractWindow.show();
        return root;
    }

    //Kiểm tra ngày bắt đầu và ngày kết thúc có hợp lệ không ( ngày kết thúc phải > ngày bắt đầu )
    private int CheckDateValid(LocalDate inputDate, LocalDate outputDate){
        return outputDate.compareTo(inputDate);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //sử dụng focusedPropetry để thêm Listener cho các thuộc tính.
        txtLienceInfo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    RequiredFieldValidator validator = new RequiredFieldValidator();
                    txtLienceInfo.getValidators().add(validator);
                    validator.setMessage("Dòng này không được để trống");
                    txtLienceInfo.validate();
                }
            }
        });

        txtNameInfo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    RequiredFieldValidator validator = new RequiredFieldValidator();
                    txtNameInfo.getValidators().add(validator);
                    validator.setMessage("Dòng này không được để trống");
                    txtNameInfo.validate();
                }
            }
        });

        txtCusInfo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1){
                    RequiredFieldValidator validator = new RequiredFieldValidator();
                    txtCusInfo.getValidators().add(validator);
                    validator.setMessage("Dòng này không được để trống");
                    txtCusInfo.validate();
                }
            }
        });

        contractList = ContractInfo.ReturnContracts();
        //Sinh ngẫu nhiên ID cho hợp đồng
        String gen = Long.toHexString(Double.doubleToLongBits(Math.random()));

        txtInfoID.setText(gen);
        dpInputInfo.setValue(LocalDate.now());
        dpOutputInfo.setValue(LocalDate.now());
        Convert convert = new Convert();

        //Set định dạng cho các field
        convert.SetFomatterForTextField(txtWeight);
        convert.SetFormatterForDate(dpInputInfo);
        convert.SetFormatterForDate(dpOutputInfo);

        contractList = ContractInfo.ReturnContracts(); // Trả về danh sách hợp đồng

        cbGroup.setItems(listGroupCar); // Set dữ liệu cho combobox

        //Sử dụng Event để tiến hành set dữ liệu
        cbGroup.setOnMouseClicked(e->{
            cbGroup.setPromptText("Loại xe");
            Listener();
        });
        cbGroup.setOnAction(p->{
            Listener();
        });
        dpInputInfo.setOnAction(e->{
            if (CheckDateValid(dpInputInfo.getValue(), dpOutputInfo.getValue()) < 0){
                lbCheckDate.setVisible(true);
                btAdd.setDisable(true);

            }else {
                lbCheckDate.setVisible(false);
                btAdd.setDisable(false);
            }
            Listener();
        });
        dpOutputInfo.setOnAction(e->{
            if (CheckDateValid(dpInputInfo.getValue(), dpOutputInfo.getValue()) < 0){
                lbCheckDate.setVisible(true);
                btAdd.setDisable(true);
            }else {
                lbCheckDate.setVisible(false);
                btAdd.setDisable(false);
            }
            Listener();
        });
        dpInputInfo.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            Listener();
        }));
        dpOutputInfo.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            Listener();
        }));
        txtWeight.textProperty().addListener(((observableValue, oldValue, newValue) ->{
            priceForTruck = CONST.PRICE_PER_MONTH_TRUCK;
            priceForSubCar = CONST.PRICE_PER_MONTH_SUBCAR;
            month = (int)ChronoUnit.MONTHS.between(dpInputInfo.getValue(), dpOutputInfo.getValue());
            try{
                weightForTruck = Double.parseDouble(newValue);
            }catch (NumberFormatException e){}
            if (cbGroup.getValue() == CONST.TRUCK && month < CONST.MONTH_INPUT){
                txtWeight.setDisable(false);
                priceForTruck *= weightForTruck;
                txtPricePerMonth.setText(convert.Currency(priceForTruck));
                String total = convert.Currency(priceForTruck * month);
                txtTotal.setText(total);
            }
            if (cbGroup.getValue() == CONST.TRUCK && month >= CONST.MONTH_INPUT){
                txtWeight.setDisable(false);

                priceForTruck *= weightForTruck;
                priceForTruck *=  (1 - discountforTruck);
                txtPricePerMonth.setText(convert.Currency(priceForTruck));
                String total = convert.Currency(priceForTruck * month);
                txtTotal.setText(total);
            }
        } ));


        //Khi nhấn vào thì chuyển sang giao diện của khách hàng để chọn khách hàng cho hợp đồng.
        txtCusInfo.setOnMouseClicked(e->{
            Stage customerWindow = new Stage();
            AnchorPane root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/Customers/addCustomer.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Scene scene = new Scene(root);
            customerWindow.setScene(scene);
            customerWindow.show();
            TableView tblCustomer = (TableView) root.lookup("#tblCustomer");

            tblCustomer.getSelectionModel().selectedItemProperty().addListener(p->{
                Customer selected = (Customer)tblCustomer.getSelectionModel().getSelectedItem();
                txtCusInfo.setText(selected.getName());
                idCustomer = selected.getID();
                customerWindow.hide();

            });


        });

        //Thêm khách hàng
        btAdd.setOnAction(e->{
            if(txtCusInfo.getText().isBlank() || txtNameInfo.getText().isBlank() || txtLienceInfo.getText().isBlank()){
                ValidateInputAfterSubmit(txtLienceInfo);
                ValidateInputAfterSubmit(txtNameInfo);
                ValidateInputAfterSubmit(txtCusInfo);
            }else{
                AddContract(txtNameInfo.getText(), txtLienceInfo.getText(), dpInputInfo.getValue(), dpOutputInfo.getValue(), Double.parseDouble(txtWeight.getText()));
                Stage contactWindow = new Stage();
                AnchorPane root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/Contract/Contract.fxml"));

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Scene scene = new Scene(root);
                contactWindow.setScene(scene);
                TableView tblContract = (TableView) root.lookup("#tblContract");
                tblContract.setItems(contractList);
                contactWindow.show();
            }

        });

        //Quay trở lại
        btBack.setOnMouseClicked(e->{
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/MainWindow/main.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                txtNameInfo.getScene().getWindow().hide();
                stage.setTitle("Phần mềm quản lý bãi đỗ xe =D");
                stage.getIcons().add(new Image("/Style/Images/car.png"));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }
}
