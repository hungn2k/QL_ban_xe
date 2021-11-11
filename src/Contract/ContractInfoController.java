package Contract;
import CONST.CONST;
import Customers.Customer;
import Date2String.Convert;
import ParkingLot.Car;
import ParkingLot.Truck;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import javafx.util.StringConverter;
import Contract.Contract;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class ContractInfoController implements Initializable {
    //PHẦN NÀY HẦU NHƯ TƯƠNG TỰ VỚI PHẦN THÊM HỢP ĐỒNG!
    //Các thuộc tính FXML
    @FXML
    private JFXButton btUpdate;

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
    private JFXDatePicker dpInputInfo;
    @FXML
    private JFXDatePicker dpOutputInfo;

    @FXML
    private ImageView btBack;

    @FXML
    private double weightForTruck;
    private double priceForSubCar;
    private double priceForTruck;
    private double discountForCar = CONST.DISCOUNT_FOR_SUBCAR;
    private double discountforTruck = CONST.DISCOUNT_FOR_TRUCK;
    private long month;
    private ObservableList carList;
    private ObservableList truckList;
    private ObservableList<Contract> contractList;
    private ObservableList<String> listGroupCar = FXCollections.observableArrayList(CONST.SUBCAR, CONST.TRUCK);
    private void Listener(){

        priceForSubCar = CONST.PRICE_PER_MONTH_SUBCAR;
        priceForTruck = CONST.PRICE_PER_MONTH_TRUCK;
        try{
            weightForTruck = Double.parseDouble(txtWeight.getText());
        }catch (NumberFormatException e){}

        Convert convert = new Convert();

        month = ChronoUnit.MONTHS.between(dpInputInfo.getValue(), dpOutputInfo.getValue());
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

    private int CheckDateValid(LocalDate inputDate, LocalDate outputDate){
        return outputDate.compareTo(inputDate);
    }


    @FXML
    private void UpdateInfo(){
        LocalDate newDateInput = dpInputInfo.getValue();
        LocalDate newDateOutput = dpOutputInfo.getValue();
        if(CheckDateValid(newDateInput, newDateOutput) < 0){
            lbCheckDate.setVisible(true);
            return;
        }else {
            for (Contract con : contractList) {
                if (con.getIDContract().equals(txtInfoID.getText())) {


                    Customer newCus = con.getForCar().getCustomer();
                    String nameCar = txtNameInfo.getText();
                    String lience = txtLienceInfo.getText();
                    double weight = Double.valueOf(txtWeight.getText());

                    contractList.remove(con);
                    if (cbGroup.getValue() == CONST.SUBCAR) {
                        Car newCar = Car.CreateCar(newCus, nameCar, lience, 0.0);
                        Contract newContract = new Contract(txtInfoID.getText(), newDateInput, newDateOutput, newCar, CONST.SUBCAR);
                        contractList.add(newContract);
                        txtInfoID.getScene().getWindow().hide();
                        AnchorPane contractWindow = ShowContractWindow();
                        TableView tableView = (TableView) contractWindow.lookup("#tblContract");
                        tableView.setItems(contractList);
                    }
                    if (cbGroup.getValue() == CONST.TRUCK) {
                        Truck newTruck = new Truck(newCus, nameCar, lience, weight);
                        Contract newContract = new Contract(txtInfoID.getText(), newDateInput, newDateOutput, newTruck, CONST.TRUCK);
                        contractList.add(newContract);
                        txtInfoID.getScene().getWindow().hide();
                        AnchorPane contractWindow = ShowContractWindow();
                        TableView tableView = (TableView) contractWindow.lookup("#tblContract");
                        tableView.setItems(contractList);
                    }
                }
            }
        }
    }

    private void SetFormatForDate(JFXDatePicker datePicker){
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Convert convert = new Convert();
        convert.SetFomatterForTextField(txtWeight);
        SetFormatForDate(dpInputInfo);
        SetFormatForDate(dpOutputInfo);

        contractList = ContractInfo.ReturnContracts();

        cbGroup.setItems(listGroupCar);
        cbGroup.setOnMouseClicked(e->{
            cbGroup.setPromptText("Loại xe");
        });

        cbGroup.setOnAction(p->{
            Listener();
        });
        dpInputInfo.setOnAction(e->{
            if (CheckDateValid(dpInputInfo.getValue(), dpOutputInfo.getValue()) < 0){
                lbCheckDate.setVisible(true);
                btUpdate.setDisable(true);

            }else {
                lbCheckDate.setVisible(false);
                btUpdate.setDisable(false);
            }
            Listener();
        });
        dpOutputInfo.setOnAction(e->{
            if (CheckDateValid(dpInputInfo.getValue(), dpOutputInfo.getValue()) < 0){
                lbCheckDate.setVisible(true);
                btUpdate.setDisable(true);
            }else {
                lbCheckDate.setVisible(false);
                btUpdate.setDisable(false);
            }
            Listener();
        });
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

        btBack.setOnMouseClicked(e->{
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Contract/Contract.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                txtTotal.getScene().getWindow().hide();
                stage.setTitle("Phần mềm quản lý bãi đỗ xe =D");
                stage.getIcons().add(new Image("/Style/Images/car.png"));
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }
}
