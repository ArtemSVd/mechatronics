package com.company.logic.elements;

import com.company.logic.service.Point;

public interface SystemElement {
    Point getCenterMass();
    double getWeight();
    void update();

}
