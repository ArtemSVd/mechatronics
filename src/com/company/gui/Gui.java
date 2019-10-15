package com.company.gui;

import com.company.gui.drawers.JointDrawer;
import com.company.gui.drawers.SegmentDrawer;
import com.company.logic.MultiLinkSystem;
import com.company.logic.elements.Joint;
import com.company.logic.elements.Segment;
import com.company.logic.exception.JointInstallationException;
import com.company.logic.exception.OutOfValueRangeException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;



public class Gui extends Application {

    private final MultiLinkSystem system = MultiLinkSystem.getInstance();

    public Gui() throws OutOfValueRangeException {
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws JointInstallationException, OutOfValueRangeException {
        Group root = new Group();
        addElements();
        readElem(root);

        primaryStage.setTitle("Смехотроника");
        primaryStage.setScene(new Scene(root,400,400));
        primaryStage.show();
    }
    private void addElements() throws JointInstallationException, OutOfValueRangeException {
        system.addSegment(50,20,180,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,210,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,90,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,180,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,60,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,90,false,false);
        system.addJoint(50,360);
        system.addSegment(50,20,10,false,false);
        Segment segment =(Segment) system.getElement(9);
        segment.setAngle(90);
        system.updateFrom(9);
    }
    private void readElem(Group root){
        for(int i = 1; i < system.getAllElements().size();i +=2) {
            if (system.getElement(i) instanceof Segment) {
                Segment segment = (Segment) system.getElement(i);
                Line line = SegmentDrawer.createLine(i,segment);
                root.getChildren().add(line);
            }
        }
        for(int i = 0; i < system.getAllElements().size();i+=2) {
            if(system.getElement(i) instanceof Joint){
                Joint joint = (Joint) system.getElement(i);
                Circle circle = JointDrawer.createCircle(i,joint);
                root.getChildren().add(circle);
            }
        }
    }
}
