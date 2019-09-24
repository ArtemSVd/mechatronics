package com.company.exception;

// Исключение связанное с неправильным созданием сочленений между сегментами
public class JointInstallationException extends Exception {
    public JointInstallationException(String message) {
        super(message);
    }
}
