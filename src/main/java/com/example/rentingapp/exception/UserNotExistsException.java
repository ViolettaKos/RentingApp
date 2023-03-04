package com.example.rentingapp.exception;

public class UserNotExistsException extends ServiceException {
    public UserNotExistsException() {
        super(ExcConstants.NO_USER);
    }
}
