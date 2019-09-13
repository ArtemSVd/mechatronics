package com.company;

import java.util.HashMap;
import java.util.Map;

public class Segment {
    static private Point staticStartPoint = new Point(0,0);
    static public Map<Integer,Segment> segmentsMap = new HashMap<>();

    static private int count = 0;
    public static int getCount() {
        return count;
    }

    private Point startPoint;
    private Point endPoint;

    private double  weight;
    private double length;
    private double angle;

    private double centerOfMass;

    // Конструктор приватный -> можем использовать его только в классе
    private Segment(double length, double weight, double angle) {
        this.weight = weight;
        this.length = length;
        this.angle = angle;
    }
    // Метод, который возвращает объект
    public static Segment getObject(double length,double weight, double angle){
        // Конструируем объект
        Segment segment = new Segment(length, weight, angle);
        segment.setStartPoint();
        segment.setEndPoint();
        segment.calcCenterOfMass();
        // Добавляем каждый созданный объект в мапу
        segmentsMap.put(++count,segment);
        // Возвращаем сконструированный объект
        return segment;
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

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCenterOfMass() {
        return centerOfMass;
    }

    public double calcCenterOfMass() {
        return 0.0;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "weight=" + weight +
                ", length=" + length +
                '}';
    }

    // Статический метод для каскадного удаления объектов
    public static void deleteObjectFromMap(int num){
        int size = segmentsMap.size();
        for(int i = num; i <= size;i++) {
            segmentsMap.remove(i);
        }
        count -= size - num + 1;
    }
}
