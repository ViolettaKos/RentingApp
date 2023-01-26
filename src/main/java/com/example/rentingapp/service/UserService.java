package com.example.project.service;

import com.example.project.exception.ServiceException;
import com.example.project.model.User;

public interface UserService {

    void add(User user) throws ServiceException;
}
