package com.example.rentingapp.exception;

public class WrongPasswordException extends ServiceException {
    public WrongPasswordException() {
        super(ExcConstants.WRONG_PASS);
    }
}
