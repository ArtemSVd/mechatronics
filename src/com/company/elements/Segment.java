package com.company.elements;

import com.company.exception.OutOfWeightRangeException;
import com.company.service.Point;
import com.company.service.SystemElement;


import java.io.*;


public class Segment implements Serializable, SystemElement {
    static private Point staticStartPoint = new Point(0,0);

    public static Point getStaticStartPoint() {
        return staticStartPoint;
    }

    private boolean right;

    private Point startPoint;
    private Point endPoint;
    private Point centerMass;

    private double  weight;
    private double length;
    private double angle;

    private boolean isInvisible = false;
    private boolean isEphemeral = false;

    private Joint joint;
    // Конструктор приватный -> можем использовать его только в классе
    private Segment(double length, double weight, double angle, Joint joint) {
        this.weight = weight;
        this.length = length;
        this.angle = angle;
        this.joint = joint;
    }
    // Метод, который возвращает объект
    public static Segment getSegment(double length, double weight, double angle, Joint joint){
        // Конструируем объект
        Segment segment = new Segment(length, weight, angle,joint);
        segment.setStartPoint();
        segment.setEndPoint();
        // Возвращаем сконструированный объект
        return segment;
    }
    // Полиморфный метод устанавливает эфемерность и невидимость для объекта(по умолчанию оба - false)
    public static Segment getSegment(double length, double weight, double angle,Joint joint, boolean isInvisible,boolean isEphemeral){
        Segment segment = getSegment(length, weight, angle, joint);
        segment.setInvisibility(isInvisible);
        segment.setEphemeral(isEphemeral);
        return segment;
    }

    public boolean isRight() {
        return right;
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

    public Point getStartPoint() {
        return startPoint;
    }
    public Point getEndPoint() {
        return endPoint;
    }

    private void setStartPoint() {
        // Установка стартовой точки для объекта (конечная точка предыдущего объекта)
        startPoint = staticStartPoint;
    }

    public void setCenterMass() {
        int ax = startPoint.getX();
        int ay = startPoint.getY();
        int bx = endPoint.getX();
        int by = endPoint.getY();

        int x = (ax + bx)/2;
        int y = (ay + by)/2;

        centerMass = new Point(x,y);
    }

    private void setEndPoint() {
        // Как то рассчитываем конечную точку

        // Устанавливаем стартовую точку для следующего объекта
        staticStartPoint = endPoint;
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

    public void setAngle(double angle) {
        double a = this.angle + angle;
        if(joint.getAngleLimit() < a)
        this.angle += angle;

        if (angle > 0) right = false;
        else right = true;
    }

    public double getAngle() {
        return angle;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Point getCenterMass() {return centerMass;}

    @Override
    public String toString() {
        return "Segment{" +
                "startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", weight=" + weight +
                ", length=" + length +
                ", angle=" + angle +
                ", isInvisible=" + isInvisible +
                ", isEphemeral=" + isEphemeral +
                '}';
    }
}
