package com.example.rentingapp.service;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.User;

public interface UserService {

    void add(User user) throws ServiceException;

    void update(User user) throws ServiceException;

    User getByLogin(String login) throws ServiceException;

    void checkIfExists(String username) throws ServiceException;

    User login(String login, String pass) throws ServiceException;

    boolean updateMoney(String login, int amount) throws ServiceException;
}
