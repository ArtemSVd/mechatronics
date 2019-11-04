package com.company.gui.drawers;

import com.company.logic.elements.Segment;
import com.company.logic.service.Point;
import com.company.logic.service.RotateMatrix;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SegmentDrawer {

    private  final Point installationPoint;

    public SegmentDrawer(Point point) {
        this.installationPoint = point;
    }

    public Line createLine(int id, Segment segment){
        double radianAngle = 90*Math.PI/180;

        RotateMatrix rotateMatrix = new RotateMatrix(radianAngle, false);

        Point newStartPoint = rotateMatrix.getNewCoordinate(segment.getStartPoint(), installationPoint);
        Point newEndPoint = rotateMatrix.getNewCoordinate(segment.getEndPoint(), installationPoint);

        Line line = new Line(newStartPoint.getX(),newStartPoint.getY(),newEndPoint.getX(),newEndPoint.getY());

        line.setStrokeWidth(7);
        if(!segment.isInvisible()) {
            line.setStroke(Color.BLACK);
        }
        else{
            line.setStroke(Color.valueOf("#d3d3d3"));
        }

        line.setId(String.valueOf(id));

       return line;
    }
}
