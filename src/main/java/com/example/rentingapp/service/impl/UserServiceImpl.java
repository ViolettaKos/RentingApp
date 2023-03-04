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

import java.util.List;
import java.util.Random;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    private final Validator validator = new Validator();
    private final UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = AbstractDAO.getInstance().getUserDAO();
    }

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void add(User user) throws ServiceException {
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
        try {
            if (userDAO.checkIfExists(username))
                throw new DuplicatedLoginException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getByLogin(String login) throws ServiceException {
        try {
            return userDAO.getUserByLogin(login).orElseThrow(UserNotExistsException::new);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User login(String login, String pass) throws ServiceException {
        validator.validateLogin(login);
        validator.passIsEmpty(pass);
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
        try {
            return userDAO.changeAmount(amount, login);
        } catch (DAOException e) {
            LOG.error("Cannot add money: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> sortUsers(String command, int currentPage, int recordsPerPage) throws ServiceException {
        int start = currentPage * recordsPerPage - recordsPerPage;
        try {
            return userDAO.sortUsersDB(command, start, recordsPerPage);
        } catch (DAOException e) {
            LOG.error("Error in sorting users");
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNumberOfRows(String command) throws ServiceException {
        try {
            return userDAO.getNumberOfRows(command);
        } catch (DAOException e) {
            LOG.error("Error in getting number of rows");
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateStatus(String login, boolean action) throws ServiceException {
        try {
            userDAO.updateStatus(login, action);
        } catch (DAOException e) {
            LOG.error("Error in updateStatus");
            throw new ServiceException(e);
        }
    }

    @Override
    public void changePass(String username, String newPass) throws ServiceException {
        try {
            userDAO.updatePass(username, newPass);
        } catch (DAOException e) {
            LOG.error("Error in updateStatus");
            throw new ServiceException(e);
        }
    }

    @Override
    public String getNewPass() {
        return new Random().ints(10, 33, 122)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void validateUser(User user) throws ServiceException {
        validator.validateEmail(user.getEmail());
        validator.validateData(user.getFirstName());
        validator.validateData(user.getLastName());
        validator.validateTelephone(user.getTelephone());
        validator.validateLogin(user.getUsername());
    }
}
