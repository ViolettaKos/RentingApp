package com.example.rentingapp.exception;

public class DuplicatedLoginException extends ServiceException {

    public DuplicatedLoginException() {
        super(ExcConstants.DUPLICATE_LOGIN);
    }
}
