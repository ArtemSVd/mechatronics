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

    private Point startPoint;
    private Point endPoint;

    private double  weight;
    private double length;
    private double angle;

    private boolean isInvisible = false;
    private boolean isEphemeral = false;

    // Конструктор приватный -> можем использовать его только в классе
    private Segment(double length, double weight, double angle) {
        this.weight = weight;
        this.length = length;
        this.angle = angle;
    }
    // Метод, который возвращает объект
    public static Segment getSegment(double length, double weight, double angle){
        // Конструируем объект
        Segment segment = new Segment(length, weight, angle);
        segment.setStartPoint();
        segment.setEndPoint();
        // Возвращаем сконструированный объект
        return segment;
    }
    // Полиморфный метод устанавливает эфемерность и невидимость для объекта(по умолчанию оба - false)
    public static Segment getSegment(double length, double weight, double angle, boolean isInvisible,boolean isEphemeral){
        Segment segment = getSegment(length, weight, angle);
        segment.setInvisibility(isInvisible);
        segment.setEphemeral(isEphemeral);
        return segment;
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

    private void setStartPoint() {
        // Установка стартовой точки для объекта (конечная точка предыдущего объекта)
        startPoint = staticStartPoint;
    }

    public Point getEndPoint() {
        return endPoint;
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
