package com.example.project.service;

import com.example.project.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();


    private final UserService userService = new UserServiceImpl();

    private static ServiceFactory getInstance() {
        if (instance == null){
            instance = new ServiceFactory();
        }
        return instance;
    }

    public static UserService getUserService() {
        return getInstance().userService;
    }
}
