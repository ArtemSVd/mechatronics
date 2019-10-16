package com.company.logic.elements;

import com.company.logic.exception.OutOfValueRangeException;
import com.company.logic.service.Point;

import java.io.Serializable;

public class Joint implements Serializable, SystemElement {
     static double staticAngleLSK = 0;
    private double angleLSK = staticAngleLSK;

    private final Segment previousSegment;

    private Point centerMass = getStartPoint();
    private  Point startPoint;     // Точка установки сочленения

    private double  weight;
    private double angleLimit;       // Ограничение угла поворота

    public Joint(double weight, double angleLimit, Segment previousSegment) throws OutOfValueRangeException {
        this.previousSegment = previousSegment;
        this.startPoint = previousSegment==null? new Point(0,0) :previousSegment.getEndPoint();
        setWeight(weight);
        setAngleLimit(angleLimit);
        updateAngleLSK();
    }

    @Override
    public Point getCenterMass() {
        return centerMass;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    private void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) throws OutOfValueRangeException {
        if(weight <= 1000 && weight >= -1000)
            this.weight = weight;
        else
            throw new OutOfValueRangeException("The weight is set in the range from -1000 to 1000");
    }

    public double getAngleLimit() {
        return angleLimit;
    }

    private void setAngleLimit(double angleLimit) {
        this.angleLimit = angleLimit * Math.PI/180;
    }

    public double getAngleLSK() {
        return angleLSK;
    }

    private void setAngleLSK(double angleLSK) {
        this.angleLSK = angleLSK;
    }

    private void updateAngleLSK(){
        double stAngleLSK = previousSegment==null? 0 : previousSegment.getAngleLSK() + Math.PI/2 - previousSegment.getAngle();
        setAngleLSK(previousSegment == null ? 0 : stAngleLSK);
        staticAngleLSK =stAngleLSK;
    }

    @Override
    public void update( ) {
        updateAngleLSK();
        setStartPoint(previousSegment == null ? new Point(0,0) : previousSegment.getEndPoint());
    }
    @Override
    public String toString() {
        return "Joint" +
                "\n  installationPoint=" + startPoint +
                "\n  weight=" + weight +
                "\n  angleLimit=" + 180*angleLimit/Math.PI;
    }
}
