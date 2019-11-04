package com.company.logic;

import com.company.logic.elements.Joint;
import com.company.logic.elements.Segment;
import com.company.logic.exception.OutOfValueRangeException;
import com.company.logic.service.Point;
import com.company.logic.elements.SystemElement;

import java.util.HashMap;
import java.util.Map;

public class MultiLinkSystem {
     private Map<Integer, SystemElement> elementsMap = new HashMap<>(); // Хранилище всех элементов
     private int count = 0; // Кол-во всех элементов (Segments. Joints)
     private static MultiLinkSystem instance;

    private MultiLinkSystem()  { }  // Приватный конструктор(объект нельзя создать через new)

    public static MultiLinkSystem getInstance()  {
        if(instance == null){
            instance = new MultiLinkSystem();
        }
        return instance;
    }

    // метод для каскадного удаления объектов
    public void removeElementFromSystem(int elemNumber) {
        if (elemNumber > 0) {
            int size = elementsMap.size();
            for (int i = elemNumber; i <= size; i++) {
                elementsMap.remove(i);
            }
            count -= size - elemNumber + 1;
        }
    }

    public Map<Integer, SystemElement> getAllElements() {
        return elementsMap;
    }

    public void setAllElements(Map<Integer, SystemElement> elementsMap) {
        this.elementsMap = elementsMap;
        count = elementsMap.size();
    }

    // Добавление сегмента
    public void addSegment(double length, double weight, double angle, boolean isInvisible,boolean isEphemeral) throws  OutOfValueRangeException {
        Joint joint = (Joint) elementsMap.get(count);
        Segment segment = Segment.getSegment(length,weight,angle,joint,isInvisible,isEphemeral);

        elementsMap.put(++count,segment);
        //setSystemCenterMass();

    }

    // Добавление сочленения
    public void addJoint(double weight, double angleLimit) throws OutOfValueRangeException {
        Segment previousSegment = (Segment) getElement(count);
        Joint joint = new Joint(weight,angleLimit,previousSegment);
        elementsMap.put(++count,joint);
        //setSystemCenterMass();
    }

    // Метод для получения конкретного элемента
    public SystemElement getElement(int elemNumber){
        return elementsMap.get(elemNumber);
    }

    public Point getSystemCenterMass(){
        double summOfMass = 0;
        double multOfMassX = 0;
        double multOfMassY = 0;

        for (int i = 1; i <= count; i++) {
            double x = getElement(i).getCenterMass().getX();
            double weight = getElement(i).getWeight();
            summOfMass += weight;
            multOfMassX += weight*x;

            double y = getElement(i).getCenterMass().getY();
            multOfMassY += weight*y;
        }
        double x = multOfMassX/summOfMass;
        double y = multOfMassY/summOfMass;

        return new Point (x,y);
    }

    public void updateFrom(int num){
        for (int i = num; i <= elementsMap.size(); i++) {
            getElement(i).update();
        }
    }
}
