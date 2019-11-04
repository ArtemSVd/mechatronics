package com.company.logic.elements;

import com.company.logic.exception.OutOfValueRangeException;
import com.company.logic.service.Point;
import com.company.logic.service.RotateMatrix;

import java.io.*;

public class Segment implements Serializable, SystemElement {

    private Point startPoint;
    private Point endPoint;

    private double  weight;
    private double length;
    private double angle;

    private boolean isInvisible = false;
    private boolean isEphemeral = false;

    private  Joint previousJoint;

    // Конструктор приватный -> можем использовать его только в классе
    private Segment(double length, double weight, double angle ) throws OutOfValueRangeException {
        setWeight(weight);
        setLength(length);
        this.angle = angle * Math.PI/180;
    }

    //  метод устанавливает эфемерность и невидимость для объекта(по умолчанию оба - false)
    public static Segment getSegment(double length, double weight, double angle,Joint previousJoint, boolean isInvisible,boolean isEphemeral) throws OutOfValueRangeException {
        Segment segment = new Segment(length, weight, angle);
        segment.setPreviousElements(previousJoint);   // Установка предыдущих сегмента и сустава
        segment.setStartPoint();        // Установка стартовой точки
        segment.setEndPoint();          // Установка конечной точки
        segment.setInvisibility(isInvisible);      // Установка эффекта невидимости
        segment.setEphemeral(isEphemeral);         // Установка эффекта эфемерномти
        return segment;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) throws OutOfValueRangeException {
        if(length >= 10 && length <= 100)
            this.length = length;
        else
            throw  new OutOfValueRangeException("The length is set in the range from 10 to 100");
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public void setInvisibility(boolean isInvisible) {
        this.isInvisible = isInvisible;
    }

    public boolean isEphemeral() {
        return isEphemeral;
    }

    public void setEphemeral(boolean ephemeral) {
        isEphemeral = ephemeral;
    }

    private  void setPreviousElements(Joint previousJoint){
        this.previousJoint = previousJoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    private void setStartPoint() {
        // Установка стартовой точки для объекта (конечная точка предыдущего объекта)
        startPoint = previousJoint != null? previousJoint.getStartPoint() : new Point(0,0);
    }

    private void setStartPoint(Point previousEndPoint) {
        // Установка стартовой точки для объекта (конечная точка предыдущего объекта)
        this.startPoint = previousEndPoint;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        double radianAngle = angle * Math.PI/180 + this.angle;
        double radMod = radianAngle >= 0? radianAngle : (radianAngle + 2*Math.PI);

        if(previousJoint.getAngleLimit() >= radMod)
            this.angle = radMod;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    private void setEndPoint() {
        // Расчитываем конечную точку
        RotateMatrix matrix = new RotateMatrix(getAngleLSK(),true);
        Point pointInLocalSK = new Point(length*Math.cos(angle),length*Math.sin(angle));
        endPoint = matrix.getNewCoordinate(pointInLocalSK,startPoint);
        // Установка угла поворота локальной системы координат
        Joint.staticAngleLSK +=Math.PI/2 - angle;
    }

    public Point getCenterMass() {
        double ax = startPoint.getX();
        double ay = startPoint.getY();
        double bx = endPoint.getX();
        double by = endPoint.getY();

        double x = (ax + bx)/2;
        double y = (ay + by)/2;

        Point centerMassInLocalSK = new Point(x,y);
        RotateMatrix matrix = new RotateMatrix(getAngleLSK(),true);
        return matrix.getNewCoordinate(centerMassInLocalSK,startPoint);
    }

    public double getAngleLSK() {
        return previousJoint.getAngleLSK();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) throws OutOfValueRangeException {
        if(weight <= 1000 && weight >= -1000)
            this.weight = weight;
        else
            throw new OutOfValueRangeException("The weight is set in the range from -1000 to 1000");
    }

    @Override
    public String toString() {
        return "Segment \n" +
                "  startPoint=" + startPoint +
                "\n  endPoint=" + endPoint +
                "\n  weight=" + weight +
                "\n  length=" + length +
                "\n  angle=" + Math.round(180*angle/Math.PI) +
                "\n  isInvisible=" + isInvisible +
                "\n  isEphemeral=" + isEphemeral;
    }

    @Override
    public void update(){
        setStartPoint(previousJoint == null ? new Point(0,0) : previousJoint.getStartPoint());
        setEndPoint();
    }
}
