package com.company;

public class Segment {
    static private Point staticStartPoint = new Point(0,0);

    private Point startPoint;
    private Point endPoint;
    private int weight;
    private double centerOfMass;

    public Segment(Point endPoint, int weight) {
        startPoint = staticStartPoint;
        this.endPoint = endPoint;
        this.weight = weight;
        staticStartPoint = endPoint;
        centerOfMass = calcCenterOfMass();
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getCenterOfMass() {
        return centerOfMass;
    }

    public double calcCenterOfMass() {
        return 0.0;
    }
}
