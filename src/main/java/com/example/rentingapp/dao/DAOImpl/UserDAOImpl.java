package com.example.project.dao.DAOImpl;

import com.example.project.dao.UserDAO;
import com.example.project.exception.DAOException;
import com.example.project.model.User;

import javax.sql.DataSource;

public class UserDAOImpl implements UserDAO {
    private final DataSource dataSource;

    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User addUser() throws DAOException {
        return null;
    }
}
