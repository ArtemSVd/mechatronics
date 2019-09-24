package com.company.service;

import com.company.elements.Segment;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileService {

    // Метод для записи системы объектов в файл
    public static void writeSegmentsMapToFile(Map<Integer, Segment> segmentsMap) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(new File("temp.txt")));
        objectOutputStream.writeObject(segmentsMap);
    }
    // Метод для чтения системы объектов из файла
    public static HashMap readSegmentsMapToFile() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(new File("temp.txt")));
        return (HashMap) objectInputStream.readObject();

    }
}
