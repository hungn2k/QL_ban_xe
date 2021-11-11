package ParkingLot;

import Customers.Customer;
import Customers.CustomerInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarInfo {

    private static ObservableList<Car> carList;
    private static ObservableList customerList;

    public static ObservableList<Car> ReturnCar(){
        customerList = CustomerInfo.ReturnCustomer();
        carList = FXCollections.observableArrayList(
            Car.CreateCar((Customer) customerList.get(0), "Vinfast", "37A-9999", 0.0),
                Car.CreateCar((Customer) customerList.get(1), "Ford", "37A-8888", 0.0),
                Car.CreateCar((Customer) customerList.get(2), "Mercedes", "37A-6666", 0.0),
                Car.CreateCar((Customer) customerList.get(3), "Maybach500", "37A-5555", 0.0),
                Car.CreateCar((Customer) customerList.get(4), "Porche", "37A-2222", 0.0)



        );
        return carList;
    }

}
