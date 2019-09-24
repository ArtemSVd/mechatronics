package com.company.elements;

import com.company.exception.OutOfWeightRangeException;
import com.company.service.Point;
import com.company.service.SystemElement;
import java.io.Serializable;

public class Joint implements Serializable, SystemElement {
    private  Point installationPoint;     // Точка установки сочленения
    private double  weight;
    private double angleLimit;       // Ограничение угла поворота
    private Point centerMass = getInstallationPoint();

    public Point getCenterMass() {
        return centerMass;
    }

    public Joint(double weight, double angleLimit) {
        this.installationPoint = Segment.getStaticStartPoint();
        this.weight = weight;
        this.angleLimit = angleLimit;
    }

    public Point getInstallationPoint() {
        return installationPoint;
    }

    public void setInstallationPoint(Point installationPoint) {
        this.installationPoint = installationPoint;
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
                "installationPoint=" + installationPoint +
                ", weight=" + weight +
                ", angleLimit=" + angleLimit +
                '}';
    }
}
