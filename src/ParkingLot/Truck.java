package ParkingLot;

import Customers.Customer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;
import java.time.Period;

public class Truck extends ParkingLot {
    private double weight;
    public Truck(Customer customer, String name, String liencePlate, double weight) {
        super(customer, name, liencePlate, weight);
        this.weight = weight;
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
        double pricePerMonth = super.pricePerMonthOfTruck * weight;
        if(GetMonth(dayInput, dayOutput) >= super.monthInput){
            pricePerMonth *= (1 - super.discountForTruck);
            return new SimpleDoubleProperty(pricePerMonth);
        }else {
            return new SimpleDoubleProperty(pricePerMonth);
        }
    }

    @Override
    public double _PricePerMonth(LocalDate input, LocalDate output){
        double pricePerMonth = super.pricePerMonthOfTruck * weight;
        int month = GetMonth(input, output);
        if(month >= super.monthInput){
            pricePerMonth *= (1 - super.discountForTruck);
            return  pricePerMonth;
        }
        else return pricePerMonth;
    }

    public static Truck CreateTruck(Customer customer, String name, String liencePlate, double weight){
        return new Truck(customer, name, liencePlate, weight);
    }


}
