package Customers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CustomerInfo {
    private static ObservableList<Customer> cutomerList;

    public static ObservableList<Customer> ReturnCustomer(){
        cutomerList = FXCollections.observableArrayList(
                Customer.CrateCustomer("1", "Phạm Vũ Hùng", "0357189115", "20", "Nghệ An","hdz@gmail.com", Customer.CreateDate(2019, 5, 10)),
                Customer.CrateCustomer("2", "Thân Minh Nam", "0658947851", "38", "Hà Nội","haha@gmail.com", Customer.CreateDate(2018, 2, 6)),
                Customer.CrateCustomer("3", "Lê Hồng Ưng", "0326599745", "19", "Cà Mau","yeuem123@gmail.com", Customer.CreateDate(2020, 4, 6)),
                Customer.CrateCustomer("4", "Son Tùng MTP", "0123456789", "46", "Thái Bình","top1vn@gmail.com", Customer.CreateDate(2021, 10, 1)),
                Customer.CrateCustomer("5", "Karik", "02222222", "45", "USA","hihi@gmail.com", Customer.CreateDate(2019, 12, 7)),
                Customer.CrateCustomer("6", "Bùi Anh Tuấn", "036969696", "22", "USA","123@gmail.com", Customer.CreateDate(2019, 12, 7)),
                Customer.CrateCustomer("7", "Anh yêu em", "011111111", "22", "Hà Nội","999@gmail.com", Customer.CreateDate(2019, 12, 7)),
                Customer.CrateCustomer("8", "Ahiihi", "049849849", "22", "Nam Định","ơ@gmail.com", Customer.CreateDate(2019, 12, 7)),
                Customer.CrateCustomer("9", "Hiền Hồ", "018919191", "28", "Hà Nội","conlừa@gmail.com", Customer.CreateDate(2019, 12, 7))


                );

        return cutomerList;
    }

    public static LocalDate ChangeStringToLocalDate(String date){
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate parsedDate = LocalDate.parse(date, formatters);
        return parsedDate;
    }


}
