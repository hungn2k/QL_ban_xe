package ParkingLot;

import CONST.CONST;
import Customers.Customer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public abstract class ParkingLot {
    protected Customer customer;  // Khách hàng
    protected StringProperty liencePlate; //Biển số xe
    protected StringProperty Name; // Tên xe
    protected double weight; // Trọng lượng
    protected final double pricePerMonthOfSubCar = CONST.PRICE_PER_MONTH_SUBCAR; // Giá tiền 1 tháng cho xe con
    protected final double pricePerMonthOfTruck = CONST.PRICE_PER_MONTH_TRUCK; // Giá tiền 1 tháng cho xe tải
    protected final double discountForSubCar = CONST.DISCOUNT_FOR_SUBCAR; // Giảm giá cho xe con
    protected final double discountForTruck = CONST.DISCOUNT_FOR_TRUCK; // Giảm giá cho xe tải
    protected final int monthInput = CONST.MONTH_INPUT; // Tháng giảm giá (5 năm = 60 tháng)

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    public String getLiencePlate() {
        return liencePlate.get();
    }

    public StringProperty liencePlateProperty() {
        return liencePlate;
    }

    public void setLiencePlate(String liencePlate) {
        this.liencePlate.set(liencePlate);
    }

    public String getName() {
        return Name.get();
    }

    public StringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer cutomer) {
        this.customer = cutomer;
    }

    public ParkingLot(Customer cutomer, String name, String liencePlate, double weight) {
        this.Name = new SimpleStringProperty(name);
        this.liencePlate = new SimpleStringProperty(liencePlate);
        this.customer = cutomer;
        this.weight = weight;
    }

    public int GetMonth(LocalDate input, LocalDate output){
        int month = (int) ChronoUnit.MONTHS.between(input, output);
        return month;
    }

    public abstract long CountPrice(LocalDate a, LocalDate b); //Hàm tính giá tiền của hợp đồng
    public abstract DoubleProperty PricePerMonth(LocalDate a, LocalDate b); // Hàm tính giá tiền/ tháng (kiểu Double Property)
    public abstract double _PricePerMonth(LocalDate a, LocalDate b);  //Hàm tính giá tiền/ tháng (kiểu Double)

}
