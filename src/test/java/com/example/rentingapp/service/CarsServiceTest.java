package com.example.rentingapp.service;

import com.example.rentingapp.dao.CarDAO;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Car;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.impl.CarsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.*;

import static com.example.rentingapp.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class CarsServiceTest {
    CarsService carsService;
    CarDAO carDAO;

    @BeforeEach
    void setUp() {
        carDAO = mock(CarDAO.class);
        carsService = new CarsServiceImpl(carDAO);
    }

    @Test
    void sortCarsTest() throws DAOException, ServiceException {
        List<Car> cars = new ArrayList<>();
        cars.add(createCar());
        doReturn(cars).when(carDAO).sortCarsDB(isA(String.class), isA(Integer.class), isA(Integer.class));
        assertEquals(cars, carsService.sortCars("", 1, 1));
    }

    @Test
    void sortCarsExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(carDAO).sortCarsDB(isA(String.class), isA(Integer.class), isA(Integer.class));
        assertThrows(ServiceException.class, () -> carsService.sortCars("", 1, 1));
    }

    @Test
    void getNumberOfRowsTest() throws DAOException, ServiceException {
        doReturn(1).when(carDAO).getNumberOfRows(isA(String.class));
        assertEquals(1, carsService.getNumberOfRows(""));
    }

    @Test
    void getNumberOfRowsExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(carDAO).getNumberOfRows(isA(String.class));
        assertThrows(ServiceException.class, () -> carsService.getNumberOfRows(""));
    }

    @Test
    void getCarByIdTest() throws DAOException {
        doReturn(createCar()).when(carDAO).getById(isA(Integer.class));
        assertDoesNotThrow(() -> carsService.getCarById(String.valueOf(CAR_ID_VAL)));
    }

    @Test
    void getCarByIdExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(carDAO).getById(isA(Integer.class));
        assertThrows(ServiceException.class, () -> carsService.getCarById(String.valueOf(CAR_ID_VAL)));
    }

    @Test
    void getBrandsTest() throws DAOException {
        Set<String> brands=new HashSet<>();
        brands.add(createCar().getBrand());
        doReturn(brands).when(carDAO).getBrands();
        assertDoesNotThrow(() -> carsService.getBrands());
        assertEquals(1, brands.size());
        assertTrue(brands.contains(BRAND_VAL));
    }

    @Test
    void getBrandsExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(carDAO).getBrands();
        assertThrows(ServiceException.class, () -> carsService.getBrands());
    }

    @Test
    void addTest() throws DAOException {
        doNothing().when(carDAO).insertCar(isA(Car.class));
        assertDoesNotThrow(() -> carsService.addCar(createCar()));
    }

    @Test
    void addExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(carDAO).insertCar(isA(Car.class));
        assertThrows(ServiceException.class, () -> carsService.addCar(createCar()));
    }

    @Test
    void updateTest() throws DAOException {
        doNothing().when(carDAO).updateCar(isA(Car.class));
        assertDoesNotThrow(() -> carsService.updateCar(createCar()));
    }
    @Test
    void updateExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(carDAO).updateCar(isA(Car.class));
        assertThrows(ServiceException.class, () -> carsService.updateCar(createCar()));
    }
    @Test
    void deleteTest() throws DAOException {
        doNothing().when(carDAO).deleteCar(isA(Integer.class));
        assertDoesNotThrow(() -> carsService.deleteCarById(CAR_ID_VAL));
    }
    @Test
    void deleteExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(carDAO).deleteCar(isA(Integer.class));
        assertThrows(ServiceException.class, () -> carsService.deleteCarById(CAR_ID_VAL));
    }

    @Test
    void getLastIdTest() throws DAOException {
        doReturn("1").when(carDAO).getLastId();
        assertDoesNotThrow(() -> carsService.getLastId());
    }
    @Test
    void getLastIdExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(carDAO).getLastId();
        assertThrows(ServiceException.class, () -> carsService.getLastId());
    }
    private Car createCar() {
        Car car=new Car();
        car.setBrand(BRAND_VAL);
        car.setName(NAME_VAL);
        car.setPrice(PRICE_VAL);
        car.setId(CAR_ID_VAL);
        car.setQuality_class(QUALITY_VAL);
        return car;
    }
}
