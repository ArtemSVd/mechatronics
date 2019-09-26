package com.company.service;

public class RotateMatrix {
    double angle;
    boolean right;

    public RotateMatrix(double angle, boolean right) {
        this.angle = angle;
        this.right = right;
    }

    public Point getNewCoordinate(Point point){
        int x  = point.getX();
        int y = point.getY();
        if(right){
            int newX =(int) (x * Math.cos(angle) + y * Math.sin(angle));
            int newY = (int) (-x*Math.sin(angle) + y * Math.cos(angle));
            return new Point(newX,newY);
        }
        else {
            int newX =(int) (x * Math.cos(angle) - y * Math.sin(angle));
            int newY = (int) (x*Math.sin(angle) + y * Math.cos(angle));
            return new Point(newX,newY);
        }
    }
}
