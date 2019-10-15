package com.company.gui;

import com.company.logic.MultiLinkSystem;
import com.company.logic.elements.Joint;
import com.company.logic.elements.Segment;
import com.company.logic.exception.JointInstallationException;
import com.company.logic.exception.OutOfValueRangeException;
import com.company.logic.service.Point;
import com.company.logic.service.RotateMatrix;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;


public class Gui extends Application {
    private Point originPoint = new Point(200,200);
    private final MultiLinkSystem system = MultiLinkSystem.getInstance();

    public Gui() throws OutOfValueRangeException {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_form.fxml"));

        primaryStage.setTitle("Mechatronics");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    private void createLine(int id, Segment segment, Group root){
        double radianAngle = 90*Math.PI/180;

        RotateMatrix rotateMatrix = new RotateMatrix(radianAngle, false);
        Point newStartPoint = rotateMatrix.getNewCoordinate(segment.getStartPoint(), originPoint);
        Point newEndPoint = rotateMatrix.getNewCoordinate(segment.getEndPoint(),originPoint);

        Line line = new Line(newStartPoint.getX(),newStartPoint.getY(),newEndPoint.getX(),newEndPoint.getY());
        line.setStrokeWidth(7);
        line.setStroke(Color.BLACK);
        line.setId(String.valueOf(id));
        line.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Лови информейшн");
            alert.setContentText("Ты нажал на сегмент № "+ line.getId());
            alert.showAndWait();
        });
        root.getChildren().add(line);
    }
    private void createCircle(int id, Joint joint,Group root){
        double radianAngle = 90*Math.PI/180;
        RotateMatrix rotateMatrix = new RotateMatrix(radianAngle,false);
        Point newStartPoint = rotateMatrix.getNewCoordinate(joint.getStartPoint(),originPoint);

        Circle circle = new Circle(newStartPoint.getX(),newStartPoint.getY(),10);

        circle.setStroke(Color.GREEN);
        circle.setStrokeWidth(5);
        circle.setId(String.valueOf(id));
        circle.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Лови информейшн");
            alert.setContentText("Ты нажал на сочленение № "+ circle.getId());
            alert.showAndWait();
        });
        root.getChildren().add(circle);
    }
    private void addElements() throws JointInstallationException, OutOfValueRangeException {
        system.addSegment(50,20,90,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,30,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,90,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,180,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,-90,false,false);

        Segment segment =(Segment) system.getElement(9);
        segment.setAngle(90);
        system.updateFrom(9);
    }
    private void readElem(Group root){
        for(int i = 1; i < system.getAllElements().size();i +=2) {
            if (system.getElement(i) instanceof Segment) {
                createLine(i, (Segment) system.getElement(i), root);
            }
        }
        for(int i = 0; i < system.getAllElements().size();i+=2) {
            if(system.getElement(i) instanceof Joint){
                createCircle(i,(Joint) system.getElement(i), root);
            }
        }
    }
}
