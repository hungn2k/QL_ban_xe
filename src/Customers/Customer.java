package Customers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Customer {
    private StringProperty ID;
    private StringProperty name;
    private StringProperty phoneNumber;
    private StringProperty address;
    private StringProperty age;
    private StringProperty email;
    private StringProperty dateSignUp;


    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = new SimpleStringProperty(ID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }

    public String getAge() {
        return age.get();
    }

    public StringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age = new SimpleStringProperty(age);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public String getDateSignUp() {
        return dateSignUp.get();
    }

    public StringProperty dateSignUpProperty() {
        return dateSignUp;
    }

    public void setDateSignUp(String dateSignUp) {
        this.dateSignUp = new SimpleStringProperty(dateSignUp);
    }


    public Customer(String ID, String name, String phoneNumber, String age, String address, String email, String dateSignUp) {
        this.ID = new SimpleStringProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.age = new SimpleStringProperty(age);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.dateSignUp = new SimpleStringProperty(dateSignUp);

    }

    public Customer(){
        //
    }

    public static Customer CrateCustomer(String ID, String name, String phoneNumber, String age, String address, String email, String dateSignUp){
        Customer customer = new Customer(ID, name, phoneNumber, age, address, email, dateSignUp);
        return customer;
    }

    public static String CreateDate(int year, int month, int day){
        LocalDate localDate = LocalDate.of(year, month, day);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String text = localDate.format(formatters);
//        LocalDate parsedDate = LocalDate.parse(text, formatters);
        return text;
    }


}
