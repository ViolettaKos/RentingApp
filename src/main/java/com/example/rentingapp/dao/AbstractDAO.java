package com.example.project.dao;

import com.example.project.dao.DAOImpl.UserDAOImpl;
import com.example.project.dao.connection.ConnectionPool;

import javax.sql.DataSource;

public abstract class AbstractDAO {

    private static AbstractDAO instance;


    public abstract UserDAO getUserDAO();




    private static synchronized AbstractDAO getInstance() {
        if (instance == null)
            instance = new DAOFactory(ConnectionPool.getDataSource());
        return instance;
    }
}
