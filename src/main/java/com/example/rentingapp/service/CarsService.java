package com.example.rentingapp.service;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Car;

import java.util.List;
import java.util.Set;

public interface CarsService {

    List<Car> sortCars(String command, int currentPage, int recordsPerPage) throws ServiceException;

    int getNumberOfRows(String command) throws ServiceException;

    Car getCarById(String parameter) throws ServiceException;

    void updateAvailability(int car_id, boolean isAvailable) throws ServiceException;

    Set<String> getBrands() throws ServiceException;

    void addCar(Car car) throws ServiceException;

    String getLastId() throws ServiceException;

    void updateCar(Car car) throws ServiceException;

    void deleteCarById(int car_id) throws ServiceException;
}
