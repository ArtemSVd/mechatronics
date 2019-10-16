package com.company.gui.controllers;

import com.company.gui.drawers.JointDrawer;
import com.company.gui.drawers.SegmentDrawer;
import com.company.logic.MultiLinkSystem;
import com.company.logic.elements.Joint;
import com.company.logic.elements.Segment;
import com.company.logic.exception.OutOfValueRangeException;
import com.company.logic.service.FileService;
import com.company.logic.service.Point;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.io.IOException;

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

    private int selectedElements;
    private double scale = 1;

    @FXML
    public void deleteSelectedElem(){
        system.removeElementFromSystem(selectedElements-1);
        //system.updateFrom(selectedElements-1);
        deleteElements();
        Point  point = new Point(canvas.getWidth()/2,canvas.getHeight()/2);
        scale = 1;
        readElem(canvas, point);
    }
    @FXML
    public void addElement(){
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("add_form.fxml"));
            stage.setTitle("Add segment");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            deleteElements();
            Point  point = new Point(canvas.getWidth()/2,canvas.getHeight()/2);
            readElem(canvas, point);
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An unexpected error has occurred");
            alert.showAndWait();
        }
    }
    @FXML
    public void saveElements() {
        try {
            FileService.writeSegmentsMapToFile(system.getAllElements());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void writeElements() {
        try {
            system.setAllElements(FileService.readSegmentsMapToFile());
            deleteElements();
            scale = 1;
            Point  point = new Point(canvas.getWidth()/2,canvas.getHeight()/2);
            readElem(canvas, point);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleButtonSetParametrs(){
       double length = getDoubleValue(this.length);
       double angle = getDoubleValue(this.angle);
       double weightSegment = getDoubleValue(this.weightSegment);
       double weightJoint = getDoubleValue(this.weightJoint);
       double limit = getDoubleValue(this.limit);

       boolean isInvisible = invisible.isSelected();
       boolean isEphemeral = ephemeral.isSelected();

        Segment segment = (Segment) system.getElement(selectedElements);
        Joint joint = (Joint) system.getElement(selectedElements - 1);
        try {
            segment.setLength(length);
            segment.setAngle(angle);
            segment.setWeight(weightSegment);
            segment.setInvisibility(isInvisible);
            segment.setEphemeral(isEphemeral);

            joint.setWeight(weightJoint);
            joint.setAngleLimit(limit);

            Segment segmentInfo =(Segment) system.getElement(selectedElements);
            Joint jointInfo = (Joint) system.getElement(selectedElements-1);
            info.setText(segmentInfo.toString()+"\n"+jointInfo.toString());

            system.updateFrom(selectedElements - 1);
            Point  point = new Point(canvas.getWidth()/2,canvas.getHeight()/2);
            deleteElements();
            readElem(canvas,point);

        } catch (OutOfValueRangeException e) {
            e.printStackTrace();
        }

    }

    private void readElem(AnchorPane root,Point point){
        SegmentDrawer segmentDrawer = new SegmentDrawer(point);
        JointDrawer jointDrawer = new JointDrawer(point);
        for(int i = 2; i <= system.getAllElements().size();i +=2) {
            if (system.getElement(i) instanceof Segment) {
                Segment segment = (Segment) system.getElement(i);
                Line line = segmentDrawer.createLine(i,segment);
                checkBorder(line);
                line.setOnMouseClicked(mouseEvent -> {
                    int id = Integer.parseInt(line.getId());
                    selectedElements = id;
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
    private void deleteElements(){
        canvas.getChildren().clear();
    }
    private void checkBorder(Line line){

        double height = canvas.getHeight();
        double width = canvas.getWidth();


        System.out.println(height);
        System.out.println(width);

        double endX = (line.getEndX()) * scale;
        double endY = (line.getEndY()) * scale;
        while(endX >= width-80 || endY >= height || endX <= 0 || endY <= 0) {
            if(scale < 0.3) break;
            scale -= 0.1;
            endX = (line.getEndX()) * scale;
            endY = (line.getEndY()) * scale;

            System.out.println("EndX :"+endX);
            System.out.println("EndY: "+endY);
            System.out.println("StartX :"+line.getStartX() * scale);
            System.out.println("StartY: "+line.getStartY() * scale);
        }
        canvas.setScaleX(scale);
        canvas.setScaleY(scale);
        canvas.setScaleZ(scale);

        System.out.println(scale);

    }
}
