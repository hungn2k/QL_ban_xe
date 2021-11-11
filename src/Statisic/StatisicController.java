package Statisic;

import CONST.CONST;
import Contract.Contract;
import Contract.ContractInfo;
import Date2String.Convert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StatisicController implements Initializable {

    @FXML
    private JFXDatePicker dpInput;

    @FXML
    private JFXDatePicker dpOutput;

    @FXML
    private JFXRadioButton rbDate;

    @FXML
    private BarChart<String, Number> barChartPrice;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private AreaChart<String, Number> barChartGroup;

    @FXML
    private Label lbCheck;

    @FXML
    private Label lbChoose;

    @FXML
    private ImageView btBack;

    @FXML
    private PieChart pieChart;

    @FXML
    private void ShowRevenue() throws IOException {
        Stage revenueWindow = new Stage();
        AnchorPane root = null;
        revenueWindow.setTitle("Phần mềm quản lý bãi đỗ xe =D");
        revenueWindow.getIcons().add(new Image("/Style/Images/car.png"));
        root = FXMLLoader.load(getClass().getResource("/Statisic/Revenue.fxml"));
        Scene scene = new Scene(root);
        revenueWindow.setScene(scene);
        revenueWindow.show();
    }

    private int numberOfSubCar;
    private int numberOfTruck;
    private long totalPriceSubCar;
    private long totalPriceTruck;

    private ObservableList<Contract> contractList;
    XYChart.Series<String, Number> seriesGroup = new XYChart.Series<>();
    XYChart.Series<String, Number> seriesPriceSubCar = new XYChart.Series<>();
    XYChart.Series<String, Number> seriesPriceTruck = new XYChart.Series<>();

    private void ShowWithDate(LocalDate inputDate, LocalDate outputDate){
        int numberOfSubCar = 0;
        int numberOfTruck = 0;
        long totalPriceSubCar = 0;
        long totalPriceTruck = 0;
        contractList = ContractInfo.ReturnContracts();
        for (Contract contract: contractList){
            if(inputDate.compareTo(contract.getDayInput()) <= 0 && outputDate.compareTo(contract.getDayInput()) >= 0){
                if(contract.getGroupCar().equals(CONST.SUBCAR)) {
                    numberOfSubCar += 1;
                    totalPriceSubCar += contract.getForCar().CountPrice(contract.getDayInput(), contract.getDayOutput());
                }
                if(contract.getGroupCar().equals(CONST.TRUCK)){
                    numberOfTruck ++;
                    totalPriceTruck += contract.getForCar().CountPrice(contract.getDayInput(), contract.getDayOutput());
                }
            }

        }
        if(numberOfSubCar == 0 && numberOfTruck == 0){
            barChartPrice.getData().clear();
            barChartGroup.getData().clear();
            pieChart.getData().clear();
            return;
        }
        barChartPrice.getData().clear();
        barChartGroup.getData().clear();
        pieChart.getData().clear();

        XYChart.Series<String, Number> seriesGroup = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesPriceSubCar = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesPriceTruck = new XYChart.Series<>();

        seriesGroup.getData().add(new XYChart.Data<>(CONST.SUBCAR, numberOfSubCar));
        seriesGroup.getData().add(new XYChart.Data<>(CONST.TRUCK, numberOfTruck));

        barChartGroup.getData().add(seriesGroup);
        XYChart.Data<String, Number> dataForSubCar = new XYChart.Data<>("", totalPriceSubCar);
        XYChart.Data<String, Number> dataForTruck = new XYChart.Data<>("", totalPriceTruck);
        barChartGroup.setAnimated(false);
        seriesPriceSubCar.getData().add(dataForSubCar);
        seriesPriceSubCar.setName(CONST.SUBCAR);

        seriesPriceTruck.getData().add(dataForTruck);
        seriesPriceTruck.setName(CONST.TRUCK);


        barChartPrice.getData().addAll(seriesPriceSubCar, seriesPriceTruck);

        PieChart.Data subCarChart = new PieChart.Data(CONST.SUBCAR, numberOfSubCar);
        PieChart.Data truckChart = new PieChart.Data(CONST.TRUCK, numberOfTruck);
        int sum = numberOfSubCar + numberOfTruck;
        pieChart.getData().addAll(subCarChart, truckChart);
        pieChart.getData().forEach(data->{
            String percent = String.format("%.2f%%", (data.getPieValue() / sum * 100));
            Tooltip tooltip = new Tooltip(percent);
            Tooltip.install(data.getNode(), tooltip);
        });

    }
    private void ShowWithCondition(){
        rbDate.setOnMouseClicked(e->{
            if (rbDate.isSelected()){
                dpInput.setDisable(false);
                dpOutput.setDisable(false);
            }else{
                dpInput.setDisable(true);
                dpOutput.setDisable(true);
            }

        });


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Convert convert = new Convert();
        convert.SetFormatterForDate(dpInput);
        convert.SetFormatterForDate(dpOutput);
        dpInput.setValue(LocalDate.now());
        dpOutput.setValue(LocalDate.now());
        contractList = ContractInfo.ReturnContracts();
        for (Contract contract: contractList){
            if(contract.getGroupCar().equals(CONST.SUBCAR)) {
                numberOfSubCar ++;
                totalPriceSubCar += contract.getForCar().CountPrice(contract.getDayInput(), contract.getDayOutput());
            }
            else if(contract.getGroupCar().equals(CONST.TRUCK)){
                numberOfTruck ++;
                totalPriceTruck += contract.getForCar().CountPrice(contract.getDayInput(), contract.getDayOutput());

            }
        }


        seriesGroup.getData().add(new XYChart.Data<>(CONST.SUBCAR, numberOfSubCar));
        seriesGroup.getData().add(new XYChart.Data<>(CONST.TRUCK, numberOfTruck));
        seriesGroup.setName("Số xe");
        barChartGroup.getData().add(seriesGroup);
        seriesPriceSubCar.getData().add(new XYChart.Data<>("", totalPriceSubCar));
        seriesPriceSubCar.setName(CONST.SUBCAR);
        seriesPriceTruck.getData().add(new XYChart.Data<>("", totalPriceTruck));
        seriesPriceTruck.setName(CONST.TRUCK);

        barChartPrice.getData().addAll(seriesPriceSubCar, seriesPriceTruck);
        PieChart.Data subCarChart = new PieChart.Data(CONST.SUBCAR, numberOfSubCar);
        PieChart.Data truckChart = new PieChart.Data(CONST.TRUCK, numberOfTruck);
        int sum = numberOfSubCar + numberOfTruck;
        pieChart.getData().addAll(subCarChart, truckChart);
        pieChart.getData().forEach(data->{
            String percent = String.format("%.2f%%", (data.getPieValue() / sum * 100));
            Tooltip tooltip = new Tooltip(percent);
            Tooltip.install(data.getNode(), tooltip);
        });
        ShowWithCondition();
        dpInput.valueProperty().addListener(e->{
            ShowWithDate(dpInput.getValue(), dpOutput.getValue());
        });
        dpOutput.valueProperty().addListener(e->{
            ShowWithDate(dpInput.getValue(), dpOutput.getValue());
        });

        btBack.setOnMouseClicked(e->{
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/MainWindow/main.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Phần mềm quản lý bãi đỗ xe =D");
                stage.getIcons().add(new Image("/Style/Images/car.png"));
                rbDate.getScene().getWindow().hide();
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }
}
