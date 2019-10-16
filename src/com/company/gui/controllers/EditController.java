package com.company.gui.controllers;

import com.company.gui.drawers.JointDrawer;
import com.company.gui.drawers.SegmentDrawer;
import com.company.logic.MultiLinkSystem;
import com.company.logic.elements.Joint;
import com.company.logic.elements.Segment;
import com.company.logic.elements.SystemElement;
import com.company.logic.service.FileService;
import com.company.logic.service.Point;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Map;

import static com.company.utils.Utility.getDoubleValue;

public class EditController {

    private MultiLinkSystem system = MultiLinkSystem.getInstance();
    @FXML
    public TextArea info;
    @FXML
    public TextField length;
    @FXML
    public TextField angle;
    @FXML
    public TextField limit;
    @FXML
    public TextField weightSegment;
    @FXML
    public TextField weightJoint;
    @FXML
    public CheckBox invisible;
    @FXML
    public CheckBox ephemeral;
    @FXML
    public Button set;
    @FXML
    public AnchorPane canvas;
    @FXML
    public void addElement(ActionEvent event){
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("add_form.fxml"));
            stage.setTitle("Add segment");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An unexpected error has occurred");
            alert.showAndWait();
        }
    }
    @FXML
    public void showInfo(ActionEvent event)  {
        Point point = new Point(canvas.getWidth()/2,canvas.getHeight()/2);
        readElem(canvas,point);

    }
    @FXML
    public void saveElements(ActionEvent event) {
        try {
            FileService.writeSegmentsMapToFile(system.getAllElements());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void writeElements(ActionEvent event) {
        try {
            system.setAllElements(FileService.readSegmentsMapToFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleButtonSetParametrs(ActionEvent event){
       double length = getDoubleValue(this.length);
       double angle = getDoubleValue(this.angle);
       double weightSegment = getDoubleValue(this.weightSegment);
       double weightJoint = getDoubleValue(this.weightJoint);
       double limit = getDoubleValue(this.limit);
    }

    private void readElem(AnchorPane root,Point point){
        SegmentDrawer segmentDrawer = new SegmentDrawer(point);
        JointDrawer jointDrawer = new JointDrawer(point);

        for(int i = 2; i <= system.getAllElements().size();i +=2) {
            if (system.getElement(i) instanceof Segment) {
                Segment segment = (Segment) system.getElement(i);
                Line line = segmentDrawer.createLine(i,segment);
                line.setOnMouseClicked(mouseEvent -> {
                    int id = Integer.parseInt(line.getId());
                    Segment segmentInfo =(Segment) system.getElement(id);
                    Joint jointInfo = (Joint) system.getElement(id-1);
                    info.setText(segmentInfo.toString()+"\n"+jointInfo.toString());
                    setParametersForSegment(segmentInfo);
                    setParametersForJoint(jointInfo);
                });
                root.getChildren().add(line);
            }
        }
        for(int i = 1; i <= system.getAllElements().size();i+=2) {
            if(system.getElement(i) instanceof Joint){
                Joint joint = (Joint) system.getElement(i);
                Circle circle = jointDrawer.createCircle(i,joint);
                root.getChildren().add(circle);
            }
        }
    }
    private void setParametersForSegment(Segment segment){
        length.setText(String.valueOf(segment.getLength()));
        angle.setText(String.valueOf(180*segment.getAngle()/Math.PI));
        weightSegment.setText(String.valueOf(segment.getWeight()));
        invisible.setSelected(segment.isInvisible());
        ephemeral.setSelected(segment.isEphemeral());
    }
    private void setParametersForJoint(Joint joint){
        weightJoint.setText(String.valueOf(joint.getWeight()));
        limit.setText(String.valueOf(180*joint.getAngleLimit()/Math.PI));
    }
}
