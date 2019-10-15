package com.company.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Utility {
    public Utility() {
    }

    public static double getDoubleValue(TextField textField){
        double d=0;
        try {
            String s = textField.getCharacters().toString();
            d =  Double.parseDouble(textField.getCharacters().toString());
        }catch(NumberFormatException n){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Write correct number (Example: 2.7)");
            alert.showAndWait();
        }
        return d;
    }
}
