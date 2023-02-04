package com.example.rentingapp.exception;

public class DAODuplicatedDateException extends DAOException{
    public DAODuplicatedDateException() {
        super(ExcConstants.WRONG_DATES);
    }
}
