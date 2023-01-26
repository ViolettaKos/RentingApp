package com.example.project.service.impl;

import com.example.project.exception.ServiceException;
import com.example.project.model.User;
import com.example.project.service.UserService;
import com.example.project.utils.Validator;
import org.apache.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private final Validator validator=new Validator();

    @Override
    public void add(User user) throws ServiceException {

    }
}
