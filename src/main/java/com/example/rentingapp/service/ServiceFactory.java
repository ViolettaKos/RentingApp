package com.example.rentingapp.service;


import com.example.rentingapp.service.impl.CarsServiceImpl;
import com.example.rentingapp.service.impl.OrderServiceImpl;
import com.example.rentingapp.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();
    private final UserService userService = new UserServiceImpl();
    private final CarsService carsService=new CarsServiceImpl();
    private final OrderService orderService=new OrderServiceImpl();

    private static ServiceFactory getInstance() {
        if (instance == null){
            instance = new ServiceFactory();
        }
        return instance;
    }

    public static UserService getUserService() {
        return getInstance().userService;
    }
    public static CarsService getCarsService() { return getInstance().carsService;}
    public static OrderService getOrderService() { return getInstance().orderService;}
}
