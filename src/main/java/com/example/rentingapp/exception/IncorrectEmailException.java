package com.example.rentingapp.exception;

public class IncorrectEmailException extends ServiceException {
    public IncorrectEmailException() {
        super(ExcConstants.INC_EMAIL);
    }
}
