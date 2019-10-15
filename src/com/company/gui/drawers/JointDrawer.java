package com.company.gui.drawers;

import com.company.logic.elements.Joint;
import com.company.logic.service.Point;
import com.company.logic.service.RotateMatrix;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class JointDrawer {
    private static final Point INSTALLATION_POINT = new Point(200,200);
    private static final double CIRCLE_RADIUS = 10;

    public static Circle createCircle(int id, Joint joint){
        double radianAngle = 90*Math.PI/180;
        RotateMatrix rotateMatrix = new RotateMatrix(radianAngle,false);

        Point newStartPoint = rotateMatrix.getNewCoordinate(joint.getStartPoint(),INSTALLATION_POINT);

        Circle circle = new Circle(newStartPoint.getX(),newStartPoint.getY(),CIRCLE_RADIUS);

        if(id == 0)
            circle.setStroke(Color.RED);
        else
            circle.setStroke(Color.GREEN);

        circle.setStrokeWidth(5);
        circle.setId(String.valueOf(id));

        return circle;
    }
}
