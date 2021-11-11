package Statisic;

import Contract.Contract;
import Contract.ContractInfo;
import Date2String.Convert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

import java.net.PasswordAuthentication;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class RevenueController implements Initializable {

    @FXML
    private JFXButton btLook;

    @FXML
    private JFXRadioButton rbMonth;

    @FXML
    private JFXTextField txtYear;

    @FXML
    private JFXTextField txtEnd;

    @FXML
    private JFXRadioButton rbYear;

    @FXML
    private ToggleGroup ok;

    @FXML
    private JFXTextField txtStart;

    @FXML
    private Label lbCheck;

    @FXML
    private Label lbChoose;

    @FXML
    private JFXTextField txtTotal;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private void ShowRevenue(Boolean forMonth){
        if(txtYear.getText().isBlank() || txtStart.getText().isBlank() || txtEnd.getText().isBlank()){

            return;
        }else {

            if(forMonth){
                int monthStart = Integer.parseInt(txtStart.getText());
                int monthEnd = Integer.parseInt(txtEnd.getText());
                int year = Integer.parseInt(txtYear.getText());
                txtStart.setPromptText("Tháng");
                txtEnd.setPromptText("Tháng");

                int i = 0;
                int countYear = 0 ;
                long totalPrice = 0;

                String[] month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                String[] months = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
                long[] totalPriceOfMonth = new long[12];
                for(i = 0; i < 12; i ++){
                    totalPriceOfMonth[i] = 0;
                }
                for(Contract contract: contracList){
                    if(contract.getDayInput().getYear() == year){
                        countYear ++;
                        LocalDate date = contract.getDayInput();
                        if(date.getMonth().compareTo(Month.of(monthStart)) >= 0 && date.getMonth().compareTo(Month.of(monthEnd)) <=0){

                            long price = contract.getForCar().CountPrice(contract.getDayInput(), contract.getDayOutput());
                            totalPrice += price;
                            totalPriceOfMonth[date.getMonthValue() - 1] += price;

                        }
                    }

                }
                if(countYear == 0) {
                    lineChart.getData().clear();
                    return;
                }
                else {
                    Convert convert = new Convert();
                    txtTotal.setText(String.valueOf(convert.Currency(totalPrice)));
//
                    XYChart.Series<String, Number> series = new XYChart.Series<>();

                    for(i = 0; i < 12; i++){
                        XYChart.Data<String, Number> a = new XYChart.Data<>(months[i], totalPriceOfMonth[i]);
                        series.getData().add(a);
                        series.setName("Doanh thu");
                    }
                    lineChart.getYAxis().setLabel("Tổng tiền");
                    lineChart.setAnimated(false);
                    lineChart.getData().clear();
                    lineChart.getData().addAll(series);

                }
            }
            else if(!forMonth){
                txtYear.setVisible(false);
                long totalPriceOfYear = 0;
                long totalPrice = 0;
                int count = 0;
                int yearStart = Integer.parseInt(txtStart.getText());
                int yearEnd = Integer.parseInt(txtEnd.getText());
                long[] years = new long[3000];
                Arrays.fill(years, 0);

                for (Contract contract: contracList){

                    if(contract.getDayInput().getYear() >= yearStart && contract.getDayInput().getYear() <= yearEnd){

                        years[contract.getDayInput().getYear()] += contract.getForCar().CountPrice(contract.getDayInput(), contract.getDayOutput());
                        totalPrice += contract.getForCar().CountPrice(contract.getDayInput(), contract.getDayOutput());;
                        count++;
                    }
                }

                if(count == 0){
                    lineChart.getData().clear();
                }
                else {
                    Convert convert = new Convert();

                    txtTotal.setText(String.valueOf(convert.Currency(totalPrice)));
                    int i = 0;
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    for(i = yearStart; i <= yearEnd; i ++){
                        String year = String.valueOf(i);
                        XYChart.Data<String, Number> a = new XYChart.Data<>("Năm " + year, years[i]);
                        series.getData().add(a);
                        series.setName("Doanh thu");
                    }
                    lineChart.getYAxis().setLabel("Tổng tiền");
                    lineChart.setAnimated(false);
                    lineChart.getData().clear();
                    lineChart.getData().addAll(series);


                }
            }
        }

    }


    private ObservableList<Contract> contracList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Convert convert = new Convert();
        convert.SetFomatterForTextFieldInteger(txtStart);
        convert.SetFomatterForTextFieldInteger(txtEnd);
        convert.SetFomatterForTextFieldInteger(txtYear);
        lineChart.getYAxis().setLabel("Tổng tiền");

        contracList = ContractInfo.ReturnContracts();
        rbMonth.setOnMouseClicked(e->{
            txtYear.setVisible(true);
            btLook.setOnAction(p->{

                ShowRevenue(true);
            });
        });
        rbYear.setOnMouseClicked(e->{
            txtStart.setPromptText("Năm");
            txtEnd.setPromptText("Năm");
            txtYear.setVisible(false);
            btLook.setOnAction(p->{
                ShowRevenue(false);
            });
        });
    }
}
