package com.example.rentingapp.dao;


import com.example.rentingapp.dao.DAOImpl.CarDAOImpl;
import com.example.rentingapp.dao.DAOImpl.OrderDAOImpl;
import com.example.rentingapp.dao.DAOImpl.UserDAOImpl;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

/**
 * The DAOFactory class is responsible for creating instances of data access objects (DAOs)
 * that work with the application's underlying data source. This class extends the AbstractDAO class
 * and provides concrete implementations of the getUserDAO(), getCarDAO(), and getOrderDAO() methods.
 * <p>
 * Each DAO method returns a singleton instance of the DAO. This ensures that there is only
 * one instance of each DAO per application, which helps to conserve resources and ensure data
 * consistency.
 */
public class DAOFactory extends AbstractDAO {
    private final DataSource dataSource;
    private UserDAO userDAO;
    private CarDAO carDAO;
    private OrderDAO orderDAO;

    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl(dataSource);
        }
        return userDAO;
    }

    @Override
    public CarDAO getCarDAO() {
        if (carDAO == null) {
            carDAO = new CarDAOImpl(dataSource);
        }
        return carDAO;
    }

    @Override
    public OrderDAO getOrderDAO() {
        if (orderDAO == null) {
            orderDAO = new OrderDAOImpl(dataSource);
        }
        return orderDAO;
    }

    public DAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
