package com.example.rentingapp.exception;

public class PasswordNotMatchesException extends ServiceException {
    public PasswordNotMatchesException() {
        super(ExcConstants.NO_MATCH);
    }
}
