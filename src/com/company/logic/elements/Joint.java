package com.company.logic.elements;

import com.company.logic.exception.OutOfValueRangeException;
import com.company.logic.service.Point;

import java.io.Serializable;

public class Joint implements Serializable, SystemElement {

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
        if(weight < 1000 && weight > -1000)
            this.weight = weight;
        else
            throw new OutOfValueRangeException("Вес задается в диапазоне от -1000 до 1000 Н");
    }

    public double getAngleLimit() {
        return angleLimit;
    }

    private void setAngleLimit(double angleLimit) {
        this.angleLimit = angleLimit * Math.PI/180;
    }

    @Override
    public void update( ) {
        if(previousSegment != null)
            setStartPoint(previousSegment.getEndPoint());
    }
    @Override
    public String toString() {
        return "Joint{" +
                "installationPoint=" + startPoint +
                ", weight=" + weight +
                ", angleLimit=" + angleLimit +
                '}';
    }
}
