package com.company;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        Segment segment = Segment.getObject(10000,10,10);
//        Segment segment2 = Segment.getObject(1000,10,10);
//        Segment segment3= Segment.getObject(100,10,10);
//        Segment segment4 = Segment.getObject(10,10,10);
//        Segment segment5 = Segment.getObject(1,10,10);
//        Segment segment6 = Segment.getObject(34,10,10);
//        Segment segment7 = Segment.getObject(7,10,10);
        Segment.readSegmentsMapToFile();

        for(Map.Entry<Integer,Segment> mEntry : Segment.segmentsMap.entrySet()){
            System.out.println(mEntry.getValue());
        }




    }
}
