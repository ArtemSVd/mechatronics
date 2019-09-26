package com.company;

import com.company.exception.JointInstallationException;
import com.company.elements.Joint;
import com.company.elements.Segment;
import com.company.service.Point;
import com.company.service.RotateMatrix;
import com.company.service.SystemElement;

import java.util.HashMap;
import java.util.Map;

public class MultiLinkSystem {

     private Map<Integer, SystemElement> elementsMap = new HashMap<>();
     private Point systemCenterMass;
     private int count = 0; // Кол-во всех элементов (Segments. Joints)
     private static MultiLinkSystem instance;
    {
        elementsMap.put(0, new Joint(0,360));
    }

    public MultiLinkSystem() { }  // Приватный конструктор(объект нельзя создать через new)

    // Метод возвращает единственно-возможный объект(синглтон)
    public static MultiLinkSystem getInstance(){
        if(instance == null){
            instance = new MultiLinkSystem();
        }
        return instance;
    }
    // Статический метод для каскадного удаления объектов
    public void deleteElementFromMap(int elemNumber){
        int size = elementsMap.size();
        for(int i = elemNumber; i <= size;i++) {
            elementsMap.remove(i);
        }
        count -= size - elemNumber + 1;
    }
    // Метод возвращает мапу с элементами
    public Map<Integer, SystemElement> getAllElements() {
        return elementsMap;
    }
    // Метод для установки значений из другой мапы (например, загруженной из файла)
    public void setAllElements(Map<Integer, SystemElement> elementsMap) {
        this.elementsMap = elementsMap;
        count = elementsMap.size();
    }
    // Добавление сегмента
    public void addSegment(Segment segment) throws JointInstallationException {
        if(elementsMap.get(count) instanceof Segment) {
            throw new JointInstallationException("Отсутствует сустав!"); }
        Joint joint = (Joint) elementsMap.get(count);

        elementsMap.put(++count,segment);
    }
    // Добавление сочленения
    public void addJoint(Joint joint) throws JointInstallationException {
        if(elementsMap.get(count) instanceof Joint) {
            throw new JointInstallationException("Невозможно установить сустав на сустав!"); }
        elementsMap.put(++count,joint);
    }
    // Метод для получения конкретного элемента
    public SystemElement getElement(int elemNumber){
        return elementsMap.get(elemNumber);
    }

    private void setSystemCenterMass(){
        double summOfMass = 0;
        double multOfMassX = 0;
        double multOfMassY = 0;

        for (int i = 1; i <= count; i++) {
            int x = getElement(i).getCenterMass().getX();
            double weight = getElement(i).getWeight();
            summOfMass += weight;
            multOfMassX += weight*x;

            int y = getElement(i).getCenterMass().getY();
            multOfMassY += weight*x;
        }
        int x = (int)(multOfMassX/summOfMass);
        int y = (int)(multOfMassY/summOfMass);

        systemCenterMass = new Point (x,y);
    }
    public void update(){
        Point newEndPoint= null;
        if(getElement(1) instanceof Segment) {
            Segment segment = (Segment) getElement(1);
            RotateMatrix rotateMatrix = new RotateMatrix(segment.getAngle(),segment.isRight());
            Point newStartPoint = rotateMatrix.getNewCoordinate(segment.getStartPoint());
             newEndPoint = rotateMatrix.getNewCoordinate(segment.getEndPoint());
            segment.setStartPoint(newStartPoint);
            segment.setEndPoint(newEndPoint);
        }
        if(getElement(1) instanceof Joint) {
            Joint joint = (Joint) getElement(1);
            joint.setStartPoint(newEndPoint);
            joint.setEndPoint(newEndPoint);
        }


    }
}
