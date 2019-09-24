package com.company.exception;

// Выход за пределы допустимого диапазона, при задании веса для сегментов и сочленений
public class OutOfWeightRangeException extends Exception {
    public OutOfWeightRangeException(String message) {
        super(message);
    }
}
