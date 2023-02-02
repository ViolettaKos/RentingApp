package com.example.rentingapp.service.impl;

import com.example.rentingapp.dao.AbstractDAO;
import com.example.rentingapp.dao.CarDAO;
import com.example.rentingapp.dao.UserDAO;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Car;
import com.example.rentingapp.service.CarsService;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class CarsServiceImpl implements CarsService {
    private static final Logger LOG = Logger.getLogger(CarsServiceImpl.class);
    private final CarDAO carDAO;
    public CarsServiceImpl() {
        carDAO=AbstractDAO.getInstance().getCarDAO();
    }
    public CarsServiceImpl(CarDAO carDAO) {
        this.carDAO=carDAO;
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
    public Set<String> getBrands() throws ServiceException {
        try {
            return carDAO.getBrands();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addCar(Car car) throws ServiceException {
        try {
           carDAO.insertCar(car);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String getLastId() throws ServiceException {
        try {
            return carDAO.getLastId();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCar(Car car) throws ServiceException {
        try {
            carDAO.updateCar(car);
        } catch (DAOException e) {
            LOG.trace("Error in updating car");
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCarById(int car_id) throws ServiceException {
        try {
            carDAO.deleteCar(car_id);
        } catch (DAOException e) {
            LOG.trace("Error in deleting car");
            throw new ServiceException(e);
        }
    }
}
