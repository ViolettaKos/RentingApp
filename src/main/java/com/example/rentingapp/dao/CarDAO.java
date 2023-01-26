package com.example.rentingapp.dao;

import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Car;

import java.util.List;

public interface CarDAO {

    List<Car> getCars() throws DAOException;

    List<Car> sortCarsDB(String command, int start, int recordsPerPage) throws DAOException;

    int getNumberOfRows(String command) throws DAOException;

    Car getById(int id) throws DAOException;

    void updateAvailability(int car_id, boolean isAvailable) throws DAOException;
}
