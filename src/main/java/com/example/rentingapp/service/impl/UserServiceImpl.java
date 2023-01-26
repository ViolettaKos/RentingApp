package com.example.rentingapp.service.impl;


import com.example.rentingapp.dao.AbstractDAO;
import com.example.rentingapp.dao.UserDAO;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.exception.DuplicatedLoginException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.exception.UserNotExistsException;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.utils.HashingPassword;
import com.example.rentingapp.utils.Validator;
import org.apache.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private final Validator validator = new Validator();
    private final UserDAO userDAO = AbstractDAO.getInstance().getUserDAO();


    @Override
    public void add(User user) throws ServiceException {
        LOG.trace("Add method");
        validateUser(user);
        user.setPassword(HashingPassword.hash(user.getPassword()));
        try {
            userDAO.addUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(User user) throws ServiceException {
        LOG.trace("update method");
        validateUser(user);
        user.setPassword(HashingPassword.hash(user.getPassword()));
        try {
            userDAO.updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void checkIfExists(String username) throws ServiceException {
        LOG.trace("checkIfExists method");
        try {
            if(userDAO.checkIfExists(username))
                throw new DuplicatedLoginException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getByLogin(String login) throws ServiceException {
        LOG.trace("getByLogin method");
        try {
            return userDAO.getUserByLogin(login).orElseThrow(UserNotExistsException::new);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User login(String login, String pass) throws ServiceException {
        LOG.trace("login method");
        User user;
        try {
            user = userDAO.getUserByLogin(login).orElseThrow(UserNotExistsException::new);
            HashingPassword.verify(user.getPassword(), pass);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public boolean updateMoney(String login, int amount) throws ServiceException {
        LOG.trace("updateMoney method");
        try {
            return userDAO.changeAmount(amount, login);
        } catch (DAOException e) {
            LOG.error("Cannot add money: " + e);
            throw new ServiceException(e);
        }
    }

    private void validateUser(User user) throws ServiceException {
        validator.validateEmail(user.getEmail());
        validator.validateData(user.getFirstName());
        validator.validateData(user.getLastName());
        validator.validateTelephone(user.getTelephone());
        validator.validateLogin(user.getUsername());
    }
}
