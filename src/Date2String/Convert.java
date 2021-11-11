package Date2String;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Convert implements IConverter {
    //Phần triển khai các hàm
    @Override
    public String Date2String(LocalDate date) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String text = date.format(formatters);
        return text;

    }

    @Override
    public LocalDate String2Date(String date) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate parsedDate = LocalDate.parse(date, formatters);
        return parsedDate;
    }

    @Override
    public String Currency(double value){
        String newValue = String.valueOf(value);
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        newValue = formatter.format(Double.parseDouble(newValue));
        return newValue;
    }

    @Override
    public void SetFomatterForTextField(JFXTextField jfxTextField){
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Double> converter = new StringConverter<Double>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0 ;
                } else {
                    return Double.valueOf(s);
                }
            }


            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };
        TextFormatter<Double> textFormatter = new TextFormatter<Double>(converter, 0.0, filter);

        jfxTextField.setTextFormatter(textFormatter);
    }

    @Override
    public void SetFormatterForDate(JFXDatePicker datePicker){
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }


    public void SetFomatterForTextFieldInteger(JFXTextField jfxTextField){
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Integer> converter = new StringConverter<Integer>() {

            @Override
            public Integer fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0 ;
                } else {
                    return Integer.valueOf(s);
                }
            }


            @Override
            public String toString(Integer d) {
                return d.toString();
            }
        };
        TextFormatter<Integer> textFormatter = new TextFormatter<Integer>(converter, 0, filter);

        jfxTextField.setTextFormatter(textFormatter);
    }



}
