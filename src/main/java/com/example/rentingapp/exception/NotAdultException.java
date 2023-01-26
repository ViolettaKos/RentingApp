package com.example.rentingapp.exception;

public class NotAdultException extends ServiceException{
    public NotAdultException(){super(ExcConstants.MIN_AGE);}
}
