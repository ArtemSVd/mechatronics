package com.company.utils;

import com.company.logic.elements.Segment;
import com.company.logic.service.Point;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;

public class Utility {
    public Utility() {
    }

    public static double getDoubleValue(TextField textField) throws NumberFormatException {
        return Double.parseDouble(textField.getCharacters().toString());
    }
    public static boolean isIntersected(Line firstLine, Line secondLine) {
        Point startFirstLine = new Point(firstLine.getStartX(), firstLine.getStartY());
        Point endFirstLine = new Point(firstLine.getEndX(), firstLine.getEndY());
        Point startSecondLine = new Point(secondLine.getStartX(), secondLine.getStartY());
        Point endSecondLine = new Point(secondLine.getEndX(), secondLine.getEndY());

        double common =
                (endFirstLine.getX() - startFirstLine.getX()) * (endSecondLine.getY() - startSecondLine.getY())
                        -
                        (endFirstLine.getY() - startFirstLine.getY()) * (endSecondLine.getX() - startSecondLine.getX());

        if (common == 0) return false; //они параллельны

        double rH =
                (startFirstLine.getY() - startSecondLine.getY()) * (endSecondLine.getX() - startSecondLine.getX())
                        -
                        (startFirstLine.getX() - startSecondLine.getX()) * (endSecondLine.getY() - startSecondLine.getY());

        double sH =
                (startFirstLine.getY() - startSecondLine.getY()) * (endFirstLine.getX() - startFirstLine.getX())
                        -
                        (startFirstLine.getX() - startSecondLine.getX()) * (endFirstLine.getY() - startFirstLine.getY());

        double r = rH / common;
        double s = sH / common;

        return r > 0 && r < 1 && s > 0 && s < 1;
    }
}
