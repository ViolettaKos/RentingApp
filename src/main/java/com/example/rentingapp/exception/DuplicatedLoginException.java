package com.example.rentingapp.exception;

public class DuplicatedLogin extends ServiceException {

    public DuplicatedLogin() {
        super(ExcConstants.DUPLICATE_LOGIN);
    }
}
