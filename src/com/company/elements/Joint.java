package com.company.elements;

import com.company.exception.OutOfWeightRangeException;
import com.company.service.Point;
import com.company.service.SystemElement;
import java.io.Serializable;

public class Joint implements Serializable, SystemElement {
    private  Point startPoint;     // Точка установки сочленения
    private Point endPoint;
    private double  weight;
    private double angleLimit;       // Ограничение угла поворота
    private Point centerMass = getStartPoint();

    public Point getCenterMass() {
        return centerMass;
    }

    public Joint(double weight, double angleLimit) {
        this.startPoint = Segment.getStaticStartPoint();
        this.weight = weight;
        this.angleLimit = angleLimit;
    }

    public Point getCenterOfMass(){
        return startPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }
    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) throws OutOfWeightRangeException {
        if(weight < 1000 && weight > -1000)
            this.weight = weight;
        else
            throw new OutOfWeightRangeException("Вес задается в диапазоне от -1000 до 1000 Н");
    }

    public double getAngleLimit() {
        return angleLimit;
    }

    public void setAngleLimit(double angleLimit) {
        this.angleLimit = angleLimit;
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
