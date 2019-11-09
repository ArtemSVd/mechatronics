package com.company.gui.drawers;

import com.company.logic.MultiLinkSystem;
import com.company.logic.elements.Segment;
import com.company.logic.service.Point;
import com.company.logic.service.RotateMatrix;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CenterMassDrawer {
    private static final double CIRCLE_RADIUS = 5;
    private MultiLinkSystem multiLinkSystem = MultiLinkSystem.getInstance();
    private Point startCoordinate;

    public CenterMassDrawer(Point startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public Circle createCenterMass(){
        double radianAngle = 90*Math.PI/180;
        RotateMatrix rotateMatrix = new RotateMatrix(radianAngle,false);

        Point newStartPoint = rotateMatrix.getNewCoordinate(multiLinkSystem.getSystemCenterMass(), startCoordinate);

        Circle circle = new Circle(newStartPoint.getX(), newStartPoint.getY(), CIRCLE_RADIUS);

        circle.setStroke(Color.RED);

        circle.setStrokeWidth(5);

        return circle;
    }
}
