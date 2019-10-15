package com.company.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class EditController {

    @FXML
    public TextField length;
    @FXML
    public TextField angle;
    @FXML
    public TextField limit;
    @FXML
    public TextField weightSegment;
    @FXML
    public  TextField weightJoint;
    @FXML
    public CheckBox invisible;
    @FXML
    public  CheckBox ethemeral;


    @FXML
    public void handleButtonSetParametrs(ActionEvent event){
       double length = getDoubleValue(this.length);
       double angle = getDoubleValue(this.angle);
       double weightSegment = getDoubleValue(this.weightSegment);
       double weightJoint = getDoubleValue(this.weightJoint);
       double limit = getDoubleValue(this.limit);
    }
    private double getDoubleValue(TextField textField){
        double d=0;
        try {
            d =  Double.parseDouble(textField.getCharacters().toString());
        }catch(NumberFormatException n){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Write correct number (Example: 2.7)");
            alert.showAndWait();
        }
        return d;
    }
}
