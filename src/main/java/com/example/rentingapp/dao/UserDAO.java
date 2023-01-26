package com.example.rentingapp.dao;


import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.User;

import java.util.Optional;

public interface UserDAO {

    void addUser(User user) throws DAOException;

    void updateUser(User user) throws DAOException;

    Optional<User> getUserByLogin(String login) throws DAOException;

    boolean checkIfExists(String username) throws DAOException;

    boolean changeAmount(int amount, String login) throws DAOException;
}
