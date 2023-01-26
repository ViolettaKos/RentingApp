package com.example.rentingapp.exception;

public class UserNotExists extends ServiceException{
    public UserNotExists() {super(ExcConstants.NO_USER);}
}
