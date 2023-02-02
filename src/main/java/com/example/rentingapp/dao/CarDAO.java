package com.example.rentingapp.dao;

import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Car;

import java.util.List;
import java.util.Set;

public interface CarDAO {

    List<Car> sortCarsDB(String command, int start, int recordsPerPage) throws DAOException;

    int getNumberOfRows(String command) throws DAOException;

    Car getById(int id) throws DAOException;

    Set<String> getBrands() throws DAOException;

    void insertCar(Car car) throws DAOException;

    String getLastId() throws DAOException;

    void updateCar(Car car) throws DAOException;

    void deleteCar(int car_id) throws DAOException;
}
