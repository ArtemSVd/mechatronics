package com.company;

public class Main {

    public static void main(String[] args) {
        Segment segment = new Segment(new Point(10,10),20);
        System.out.println("segment");
        System.out.println(segment.getStartPoint());
        System.out.println(segment.getEndPoint());
        System.out.println(" ");
        Segment segment1 = new Segment(new Point(40,40),20);
        System.out.println("segment1");
        System.out.println(segment1.getStartPoint());
        System.out.println(segment1.getEndPoint());
        System.out.println(" ");
    }
}
