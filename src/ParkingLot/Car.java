package ParkingLot;

import Customers.Customer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;


public class Car extends ParkingLot {
    //Lớp Car kế thừa từ ParkingLot
    // Lúc đầu định đặt là SubCar mà quên mất :((
    public Car(Customer customer, String name, String liencePlate, double weight)
    {
        super(customer, name, liencePlate, weight);
    }

    @Override
    public long CountPrice(LocalDate dayInput, LocalDate dayOutput) {
        double pricePerMonth = _PricePerMonth(dayInput, dayOutput);
        pricePerMonth *= GetMonth(dayInput, dayOutput);
        long result = Double.valueOf(pricePerMonth).longValue();
        return result;
    }

    @Override
    public DoubleProperty PricePerMonth(LocalDate dayInput, LocalDate dayOutput) {
        int month = GetMonth(dayInput, dayOutput);
        if (month >= super.monthInput) {
            double newPricePerMonth = super.pricePerMonthOfSubCar * (1 - super.discountForSubCar);
            DoubleProperty result = new SimpleDoubleProperty(newPricePerMonth);
            return result;
        } else {
            return new SimpleDoubleProperty(pricePerMonthOfSubCar);
        }

    }

    @Override
    public double _PricePerMonth(LocalDate input, LocalDate output){
        int month = GetMonth(input, output);
        if(month >= super.monthInput){
            double newPricePerMonth = super.pricePerMonthOfSubCar * (1 - super.discountForSubCar);
            return newPricePerMonth;
        }
        else{
            return super.pricePerMonthOfSubCar;
        }
    }

    //Hàm tạo ra dữ liệu
    public static Car CreateCar(Customer customer, String name, String liencePlate, double weight){
        return new Car(customer, name, liencePlate, weight);
    }



}
