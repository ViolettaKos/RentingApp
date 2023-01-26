package com.example.project.dao;

import com.example.project.dao.DAOImpl.UserDAOImpl;

import javax.sql.DataSource;

public class DAOFactory extends AbstractDAO {


    private final DataSource dataSource;
    private UserDAO userDAO;

    public DAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserDAO getUserDAO() {
        userDAO=new UserDAOImpl(dataSource);
        return userDAO;
    }
}
