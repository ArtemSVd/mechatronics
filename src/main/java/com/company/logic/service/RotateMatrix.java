package com.company.logic.service;

public class RotateMatrix {
    private double angleLSK;
    private boolean isRightSystemCoordinate;

    public RotateMatrix(double angleLSK, boolean isRightSystemCoordinate) {
        this.angleLSK = angleLSK;
        this.isRightSystemCoordinate = isRightSystemCoordinate;
    }

    public Point getNewCoordinate(Point pointInLocalSK, Point pointStart ){
        double[] vector = getVector(pointInLocalSK);
        double[][] rotateMatrix = getRotateMatrix(pointStart);
        return multiplyMatrix(vector,rotateMatrix);
    }
    private double[] getVector(Point pointInLocalSK){
        return new double[]{pointInLocalSK.getX(),pointInLocalSK.getY(),1};
    }
    private double[][] getRotateMatrix(Point pointStart ){
            return new double[][] {
                    { Math.cos(angleLSK), -Math.sin(angleLSK), 0 },
                    { Math.sin(angleLSK), Math.cos(angleLSK), 0},
                    { pointStart.getX(), pointStart.getY(), 1 }
            };
    }
    private Point multiplyMatrix(double[] vector, double[][] rotateMatrix){
        if(isRightSystemCoordinate) {
            double x = vector[0] * rotateMatrix[0][0] + vector[1] * rotateMatrix[1][0] + vector[2] * rotateMatrix[2][0];
            double y = vector[0] * rotateMatrix[0][1] + vector[1] * rotateMatrix[1][1] + vector[2] * rotateMatrix[2][1];
            return new Point(x, y);
        }
        else{
            double x = vector[1] * rotateMatrix[0][0] + vector[0] * rotateMatrix[1][0] + vector[2] * rotateMatrix[2][0];
            double y = vector[1] * rotateMatrix[0][1] + vector[0] * rotateMatrix[1][1] + vector[2] * rotateMatrix[2][1];
            return new Point(x, y);
        }
    }
}
