package com.example.rentingapp.exception;

public class DAOException extends Exception {
    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DAOException(Throwable throwable) {
        super(throwable);
    }
}
