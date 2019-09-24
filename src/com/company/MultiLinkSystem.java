package com.company;

import com.company.exception.JointInstallationException;
import com.company.elements.Joint;
import com.company.elements.Segment;
import com.company.service.SystemElement;

import java.util.HashMap;
import java.util.Map;

public class MultiLinkSystem {

     private Map<Integer, SystemElement> elementsMap = new HashMap<>();
     private int count = 0;
     private static MultiLinkSystem instance;

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
}
