package com.company.logic.exception;

// Выход за пределы допустимого диапазона, при задании веса для сегментов и сочленений
public class OutOfValueRangeException extends Exception {
    public OutOfValueRangeException(String message) {
        super(message);
    }
}
