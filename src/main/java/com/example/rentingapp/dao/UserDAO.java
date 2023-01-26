package com.example.project.dao;

import com.example.project.exception.DAOException;
import com.example.project.model.User;

public interface UserDAO {

    User addUser() throws DAOException;
}
