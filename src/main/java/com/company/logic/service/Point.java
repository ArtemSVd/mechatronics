package com.company.logic.service;

import java.io.Serializable;

public class Point implements Serializable {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return Math.round(x);
    }

    public double getY() {
        return Math.round(y);
    }

    @Override
    public String toString() {
        return "{x=" + Math.round(x) +
                ", y=" + Math.round(y) +
                '}';
    }

}
