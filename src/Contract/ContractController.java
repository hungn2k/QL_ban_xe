package Contract;
import CONST.CONST;
import Customers.Customer;
import Customers.CustomerInfo;
import Date2String.Convert;
import com.jfoenix.controls.*;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Contract.ContractInfo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;

import java.util.ResourceBundle;

public class ContractController implements Initializable {

    //Các thuộc tính FXML
    @FXML
    private TableColumn<Contract, String> colConGroup;

    @FXML
    private JFXButton btInfo;

    @FXML
    private TableView<Contract> tblContract;

    @FXML
    private TableColumn<Contract, String> colConCus;

    @FXML
    private TableColumn<Contract, String> colConID;

    @FXML
    private JFXTextField txtSearchPrice;

    @FXML
    private JFXButton btLoad;

    @FXML
    private JFXButton btFill;

    @FXML
    private JFXButton btDelete;

    @FXML
    private ImageView btBack;

    @FXML
    private JFXRadioButton rbBigger;

    @FXML
    private JFXRadioButton rbSmaller;

    @FXML
    private TableColumn<Contract, String> colConLience;

    @FXML
    private JFXRadioButton radCar;

    @FXML
    private JFXRadioButton radTruck;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableColumn<Contract, Number> colConPrice;

    @FXML
    private TableColumn<Contract, String> colConName;
    private ObservableList<Contract> contractsList;

    //Hàm dùng để set dữ liệu vào tableview
    private void LoadAllContracts(){
        try{
            tblContract.setItems(contractsList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Tải lại trang
    @FXML
    private void LoadAgain(){
        radCar.setSelected(false);
        radTruck.setSelected(false);
        rbSmaller.setSelected(false);
        rbBigger.setSelected(false);
        LoadAllContracts();
    }

    //Xóa hợp đồng được chọn
    @FXML
    private void Delete(){
        Contract selected = tblContract.getSelectionModel().getSelectedItem();
        contractsList.remove(selected);
        LoadAllContracts();
    }

    //Chuyển sang trang xem chi tiết
    @FXML
    private void ShowDetail() {

        tblContract.getSelectionModel().selectedIndexProperty().addListener(
                e -> {
                    Contract selected = tblContract.getSelectionModel().getSelectedItem();

                    btInfo.setDisable(false);
//
                    //Hiển thị cửa sổ mới
                    btInfo.setOnAction(p->{
                        Stage detailWindow = new Stage();
                        AnchorPane root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/Contract/ContractInfo.fxml"));

                        } catch (IOException ex) {
                            //
                        }
                        Scene scene = new Scene(root);
                        detailWindow.setScene(scene);
                        detailWindow.show();
                        tblContract.getScene().getWindow().hide();
                        //Truyển dữ liệu cho trang xem chi tiết
                        JFXTextField id = (JFXTextField) root.lookup("#txtInfoID");
                        id.setText(selected.getIDContract());
                        JFXComboBox<String> groupCar = (JFXComboBox<String>) root.lookup("#cbGroup");
//                        groupCar.setPromptText(selected.getGroupCar());
                        groupCar.setValue(selected.getGroupCar());
                        JFXDatePicker inputDate = (JFXDatePicker) root.lookup("#dpInputInfo");
                        inputDate.setValue(selected.getDayInput());
                        JFXDatePicker outputDate = (JFXDatePicker) root.lookup("#dpOutputInfo");
                        outputDate.setValue(selected.getDayOutput());
                        JFXTextField lience = (JFXTextField) root.lookup("#txtLienceInfo");
                        lience.setText(selected.getForCar().getLiencePlate());
                        JFXTextField cus = (JFXTextField) root.lookup("#txtCusInfo");
                        cus.setText(selected.getForCar().getCustomer().getName());
                        JFXTextField nameCar = (JFXTextField) root.lookup("#txtNameInfo");
                        nameCar.setText(selected.getForCar().getName());
                        JFXTextField pricePerMonth = (JFXTextField) root.lookup("#txtPricePerMonth");
                        DecimalFormat formatter = new DecimalFormat("#,### đ");
                        String pricePer = String.valueOf(Double.valueOf(selected.getForCar()._PricePerMonth(selected.getDayInput(), selected.getDayOutput())).longValue());
                        String newValue = formatter.format(Double.parseDouble(pricePer));
                        pricePerMonth.setText(newValue);
                        JFXTextField weight = (JFXTextField) root.lookup("#txtWeight");
                        weight.setText(String.valueOf(selected.getForCar().getWeight()));

                        JFXTextField total = (JFXTextField) root.lookup("#txtTotal");
                        String newTotal = String.valueOf(selected.getForCar().CountPrice(selected.getDayInput(), selected.getDayOutput()));
                        newTotal = formatter.format(Double.parseDouble(newTotal));
                        total.setText(newTotal);


                    });

                }
        );

    }

    //Chức năng tìm kiếm
    private void SearchForAll(){
        FilteredList<Contract> filteredList = new FilteredList<>(contractsList, e->true);
        txtSearch.setOnKeyReleased(e->{
            txtSearch.textProperty().addListener(((observableValue, s, t1) -> {
                filteredList.setPredicate(contract->{
                    if(t1 == null || t1.isEmpty()){
                        return true;
                    }
                    String lowerCase = t1.toLowerCase();
                    if(contract.getIDContract().contains(t1)){
                        return true;
                    }
                    else if(contract.getGroupCar().toLowerCase().indexOf(lowerCase) != -1){
                        return true;
                    }
                    else if(contract.getForCar().getName().toLowerCase().indexOf(lowerCase) != -1){
                        return true;
                    }
                    else if(contract.getForCar().getCustomer().getName().toLowerCase().indexOf(lowerCase) != -1){
                        return true;
                    }
                    else if(contract.getForCar().getLiencePlate().toLowerCase().indexOf(lowerCase) != -1){
                        return true;
                    }

                    return false;
                });
            }));
            SortedList<Contract> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tblContract.comparatorProperty());
            tblContract.setItems(sortedList);
        });

    }

    //Có 2 radiobutton khi nhấn vào 1 trong 2 thì danh sách sẽ hiển thị Xe con hoặc xe tải
    private void CheckCar(){
        ObservableList<Contract> chooseContractForCar = FXCollections.observableArrayList();
        radCar.setOnMouseClicked(e->{
            chooseContractForCar.clear();
            for (Contract contract: contractsList){
                if(contract.getGroupCar().equals(CONST.SUBCAR)){
                    chooseContractForCar.add(contract);
                }
            }
            tblContract.setItems(chooseContractForCar);

        });
        radTruck.setOnMouseClicked(e->{
            chooseContractForCar.clear();
            for (Contract contract: contractsList){
                if(contract.getGroupCar().equals(CONST.TRUCK)){
                    chooseContractForCar.add(contract);
                }
            }
            tblContract.setItems(chooseContractForCar);
        });


    }

    //Yêu cầu của cô: Tìm mức tiền/ tháng lớn hơn 1 số nhập vào
    private void SearchForPricePerMonth(){
        ObservableList<Contract> listForCountPrice = FXCollections.observableArrayList();

        rbBigger.setOnMouseClicked(e->{
            double valueToCompare = Double.parseDouble(txtSearchPrice.getText());
            listForCountPrice.clear();
            for (Contract contract: contractsList){
                if(contract.getForCar()._PricePerMonth(contract.getDayInput(), contract.getDayOutput()) > valueToCompare){
                    listForCountPrice.add(contract);
                }
            }
            tblContract.setItems(listForCountPrice);

        });

        rbSmaller.setOnMouseClicked(e->{
            double  valueToCompare = Double.parseDouble(txtSearchPrice.getText());
            listForCountPrice.clear();
            for (Contract contract: contractsList){
                if(contract.getForCar()._PricePerMonth(contract.getDayInput(), contract.getDayOutput()) < valueToCompare){
                    listForCountPrice.add(contract);

                }
            }
            tblContract.setItems(listForCountPrice);

        });
    }

    //Chuyển trang
    @FXML
    public void ClickInDetail(ActionEvent e) throws IOException {
        btInfo.getScene().getWindow().hide();
        Stage detailWindow = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Contract/ContractInfo.fxml"));
        Scene scene = new Scene(root);
        detailWindow.setTitle("Phần mềm quản lý bãi đỗ xe =D");
        detailWindow.getIcons().add(new Image("/Style/Images/car.png"));
        detailWindow.setScene(scene);
        detailWindow.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Convert convert = new Convert();
        contractsList = ContractInfo.ReturnContracts(); // Trả về danh sách hợp đồng

        //Set dữ liệu cho các cột tableview
        colConID.setCellValueFactory(f -> f.getValue().IDContractProperty());
        colConName.setCellValueFactory(f -> f.getValue().getForCar().nameProperty());
        colConGroup.setCellValueFactory(f -> f.getValue().groupCarProperty());
        colConLience.setCellValueFactory(f->f.getValue().getForCar().liencePlateProperty());
        colConCus.setCellValueFactory(f -> f.getValue().getForCar().getCustomer().nameProperty());
        colConPrice.setCellValueFactory(f -> f.getValue().getForCar().PricePerMonth(f.getValue().getDayInput(), f.getValue().getDayOutput()));

        SearchForAll();
        LoadAllContracts();
        ShowDetail();
        CheckCar();
        convert.SetFomatterForTextField(txtSearchPrice);
        SearchForPricePerMonth();

        //Binding khi dữ liệu thay đổi
        txtSearchPrice.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            ObservableList<Contract> listForCountPrice = FXCollections.observableArrayList();
            if(rbBigger.isSelected()){
                listForCountPrice.clear();
                for (Contract contract: contractsList){
                    if(contract.getForCar()._PricePerMonth(contract.getDayInput(), contract.getDayOutput()) > Double.parseDouble(txtSearchPrice.getText())){
                        listForCountPrice.add(contract);
                    }
                }
                tblContract.setItems(listForCountPrice);
            }

            if(rbSmaller.isSelected()){
                listForCountPrice.clear();
                for (Contract contract: contractsList){
                    if(contract.getForCar()._PricePerMonth(contract.getDayInput(), contract.getDayOutput()) < Double.parseDouble(txtSearchPrice.getText())){
                        listForCountPrice.add(contract);
                    }
                }
                tblContract.setItems(listForCountPrice);
            }
        }));
        //Quay lại
        btBack.setOnMouseClicked(e->{
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/MainWindow/main.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                tblContract.getScene().getWindow().hide();
                stage.setTitle("Phần mềm quản lý bãi đỗ xe =D");
                stage.getIcons().add(new Image("/Style/Images/car.png"));
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btLoad.setOnAction(e->{
            LoadAgain();
        });

    }



}
