package com.company.gui.controllers;
import com.company.logic.MultiLinkSystem;
import com.company.logic.exception.OutOfValueRangeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.company.utils.Utility.getDoubleValue;

public class AddController {

    MultiLinkSystem system = MultiLinkSystem.getInstance();
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
    public void close(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void addElements(ActionEvent event){
        double length = getDoubleValue(this.length);
        double angle = getDoubleValue(this.angle);
        double weightSegment = getDoubleValue(this.segmentWeight);
        double weightJoint = getDoubleValue(this.jointWeight);
        double limit = getDoubleValue(this.limit);
        boolean isInvisible = invisible.isSelected();
        boolean isEphemeral = ephemeral.isSelected();

        try {
            system.addJoint(weightJoint,limit);
            system.addSegment(length,weightSegment,angle,isInvisible,isEphemeral);
        } catch (OutOfValueRangeException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }
}
