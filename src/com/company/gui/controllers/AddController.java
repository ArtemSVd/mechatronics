package com.company.gui.controllers;
import com.company.logic.MultiLinkSystem;
import com.company.logic.exception.OutOfValueRangeException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.company.utils.Utility.getDoubleValue;

public class AddController {

    private MultiLinkSystem system = MultiLinkSystem.getInstance();
    @FXML
    public TextField length;
    @FXML
    public TextField angle;
    @FXML
    public TextField limit;
    @FXML
    public TextField jointWeight;
    @FXML
    public TextField segmentWeight;
    @FXML
    public CheckBox invisible;
    @FXML
    public CheckBox ephemeral;
    @FXML
    public Button closeButton;
    @FXML
    public Button okButton;
    @FXML
    public void close(){
        closeForm();
    }
    @FXML
    public void addElements(){
        double length = getDoubleValue(this.length);
        double angle = getDoubleValue(this.angle);
        double weightSegment = getDoubleValue(this.segmentWeight);
        double weightJoint = getDoubleValue(this.jointWeight);
        double limit = getDoubleValue(this.limit);
        boolean isInvisible = invisible.isSelected();
        boolean isEphemeral = ephemeral.isSelected();

        boolean isJointException = false;
        try {
            system.addJoint(weightJoint,limit);
        } catch (OutOfValueRangeException e) {
            isJointException = true;
            showAlert(e.getMessage());
        }

        try {
            if(!isJointException) {
                system.addSegment(length, weightSegment, angle, isInvisible, isEphemeral);
            }
        } catch (OutOfValueRangeException e) {
            system.removeElementFromSystem(system.getAllElements().size());
            showAlert(e.getMessage());
        }
        closeForm();
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeForm(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
