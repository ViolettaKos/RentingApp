package com.example.rentingapp.service;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.OrderInfo;

import java.util.List;

public interface OrderService {
    void putOrder(Order order) throws ServiceException;

    List<OrderInfo> getOrdersByLogin(String username) throws ServiceException;

    void updatePayment(int order_id) throws ServiceException;
}
