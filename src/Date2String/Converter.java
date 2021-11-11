package Date2String;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Interface Iconverter
interface IConverter {
    //Chuyển kiểu LocalDate -> String
    public String Date2String(LocalDate date);
    //Chuyển kiểu String -> LocalDate
    public LocalDate String2Date(String date);
    //Chuyển kiểu tiền tệ ví dụ: 1000000 -> 1,000,000đ
    public String Currency(double value);
    //Định dạng dữ liệu cho JFXTextField
    public void SetFomatterForTextField(JFXTextField jfxTextField);
    //Định dạng dữ liệu cho JFXDatePicker
    public void SetFormatterForDate(JFXDatePicker datePicker);

}
