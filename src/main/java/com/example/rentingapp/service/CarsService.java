package com.example.rentingapp.service;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Car;

import java.util.List;

public interface CarsService {
    List<Car> getCars() throws ServiceException;

    List<Car> sortCars(String command, int currentPage, int recordsPerPage) throws ServiceException;

    int getNumberOfRows(String command) throws ServiceException;

    Car getCarById(String parameter) throws ServiceException;

    void updateAvailability(int car_id, boolean isAvailable) throws ServiceException;
}
