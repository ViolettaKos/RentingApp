package com.example.rentingapp.service.impl;

import com.example.rentingapp.dao.AbstractDAO;
import com.example.rentingapp.dao.CarDAO;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Car;
import com.example.rentingapp.service.CarsService;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class CarsServiceImpl implements CarsService {
    private static final Logger LOG = Logger.getLogger(CarsServiceImpl.class);
    private final CarDAO carDAO = AbstractDAO.getInstance().getCarDAO();

    @Override
    public List<Car> getCars() throws ServiceException {
        LOG.trace("getCars method");
        try {
            return carDAO.getCars();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Car> sortCars(String command, int currentPage, int recordsPerPage) throws ServiceException {
        LOG.trace("sortCars method");
        int start=currentPage*recordsPerPage-recordsPerPage;
        LOG.trace("Start parameter: "+start);
        try {
            return carDAO.sortCarsDB(command, start, recordsPerPage);
        } catch (DAOException e) {
            LOG.trace("Error in sorting cars");
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNumberOfRows(String command) throws ServiceException {
        LOG.trace("getNumberOfRows method");
        try {
            return carDAO.getNumberOfRows(command);
        } catch (DAOException e) {
            LOG.trace("Error in getting number of rows");
            throw new ServiceException(e);
        }
    }

    @Override
    public Car getCarById(String id) throws ServiceException {
        LOG.trace("getCarById method");
        try {
            return carDAO.getById(Integer.parseInt(id));
        } catch (DAOException e) {
            LOG.trace("Error in getting car");
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateAvailability(int car_id, boolean isAvailable) throws ServiceException {
        LOG.trace("updateAvailability method");
        try {
            carDAO.updateAvailability(car_id, isAvailable);
        } catch (DAOException e) {
            LOG.trace("Error in updating car");
            throw new ServiceException(e);
        }
    }
}
