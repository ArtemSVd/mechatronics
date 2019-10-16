package com.company.gui.drawers;

import com.company.logic.elements.Joint;
import com.company.logic.service.Point;
import com.company.logic.service.RotateMatrix;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class JointDrawer {
    private final Point installationpoint ;
    private static final double CIRCLE_RADIUS = 5;

    public JointDrawer(Point installationpoint) {
        this.installationpoint = installationpoint;
    }

    public  Circle createCircle(int id, Joint joint){
        double radianAngle = 90*Math.PI/180;
        RotateMatrix rotateMatrix = new RotateMatrix(radianAngle,false);

        Point newStartPoint = rotateMatrix.getNewCoordinate(joint.getStartPoint(),installationpoint);

        Circle circle = new Circle(newStartPoint.getX(),newStartPoint.getY(),CIRCLE_RADIUS);

        if(id == 1)
            circle.setStroke(Color.RED);
        else
            circle.setStroke(Color.GREEN);

        circle.setStrokeWidth(5);
        System.out.println("joint created");
        return circle;
    }
}
