package ParkingLot;

import Customers.Customer;
import Customers.CustomerInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TruckInfo {

    private static ObservableList<Truck> truckList;
    private static ObservableList customerList;

    public static ObservableList<Truck> ReturnTruck(){
        customerList = CustomerInfo.ReturnCustomer();
        truckList = FXCollections.observableArrayList(
                Truck.CreateTruck((Customer) customerList.get(5), "Huyndai", "37A-9999", 2.0),
                Truck.CreateTruck((Customer) customerList.get(6), "Daewoo", "37A-1234", 1.0),
                Truck.CreateTruck((Customer) customerList.get(7), "Thaco", "37A-9879", 2.5),
                Truck.CreateTruck((Customer) customerList.get(8), "Suzuki", "37A-6969", 1.6)


                );
        return truckList;
    }

}
