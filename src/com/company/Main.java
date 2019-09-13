package com.company;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Segment segment = Segment.getObject(10000,10,10);
        Segment segment2 = Segment.getObject(1000,10,10);
        Segment segment3= Segment.getObject(100,10,10);
        Segment segment4 = Segment.getObject(10,10,10);
        Segment segment5 = Segment.getObject(1,10,10);
        Segment segment6 = Segment.getObject(34,10,10);
        Segment segment7 = Segment.getObject(7,10,10);


        System.out.println(Segment.getCount());

        Segment.deleteObjectFromMap(2);

        for(Map.Entry<Integer,Segment> mEntry :Segment.segmentsMap.entrySet()){
            System.out.println(mEntry.getValue());
        }
        System.out.println(Segment.getCount());


    }
}
