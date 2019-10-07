package com.company.logic;

import com.company.logic.exception.OutOfValueRangeException;
import com.company.logic.elements.SystemElement;
import com.company.logic.service.FileService;

import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws OutOfValueRangeException, IOException, ClassNotFoundException {
        MultiLinkSystem system = MultiLinkSystem.getInstance();
        system.setAllElements(FileService.readSegmentsMapToFile());

        for(Map.Entry<Integer,SystemElement> entryMap : system.getAllElements().entrySet()){
            System.out.println(entryMap.getKey()+" : "+entryMap.getValue());
        }
    }
}
