package com.example.rentingapp.dao;



import com.example.rentingapp.dao.DAOImpl.CarDAOImpl;
import com.example.rentingapp.dao.DAOImpl.OrderDAOImpl;
import com.example.rentingapp.dao.DAOImpl.UserDAOImpl;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

public class DAOFactory extends AbstractDAO {
    private final DataSource dataSource;

    private UserDAO userDAO;

    private CarDAO carDAO;

    private OrderDAO orderDAO;


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
