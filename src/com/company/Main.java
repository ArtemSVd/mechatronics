package com.company;

import com.company.exception.JointInstallationException;
import com.company.elements.Joint;
import com.company.elements.Segment;
import com.company.service.SystemElement;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws JointInstallationException {
        MultiLinkSystem multiLinkSystem = MultiLinkSystem.getInstance();

        multiLinkSystem.addSegment(Segment.getSegment(10,20,30));
        multiLinkSystem.addJoint(new Joint(10,20));
        multiLinkSystem.addSegment(Segment.getSegment(10,20,30));
        multiLinkSystem.addJoint(new Joint(10,20));

        for(Map.Entry<Integer,SystemElement> entryMap : multiLinkSystem.getAllElements().entrySet()){
            System.out.println(entryMap.getKey());
            System.out.println( entryMap.getValue());
        }

        multiLinkSystem.deleteElementFromMap(3);

        System.out.println("-----------------------");

        for(Map.Entry<Integer,SystemElement> entryMap : multiLinkSystem.getAllElements().entrySet()){
            System.out.println(entryMap.getKey());
            System.out.println( entryMap.getValue());
        }

        System.out.println("-----------------------");

        System.out.println(multiLinkSystem.getElement(1));
        System.out.println(multiLinkSystem.getElement(2));

        System.out.println("------------------");

        multiLinkSystem.addSegment(Segment.getSegment(10,20,30,true,true));
        for(Map.Entry<Integer,SystemElement> entryMap : multiLinkSystem.getAllElements().entrySet()){
            System.out.println(entryMap.getKey());
            System.out.println( entryMap.getValue());
        }



    }
}
