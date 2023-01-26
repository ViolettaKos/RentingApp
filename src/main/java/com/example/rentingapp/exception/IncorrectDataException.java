package com.example.rentingapp.exception;

public class IncorrectDataException extends ServiceException{
    public IncorrectDataException() {
        super(ExcConstants.INC_DATA);
    }
}
