package com.example.rentingapp.dao;

import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.OrderInfo;

import java.util.List;

public interface OrderDAO {
    void insertOrder(Order order) throws DAOException;

    List<OrderInfo> getInfoOrderByLogin(String username) throws DAOException;

    void updatePayment(int order_id) throws DAOException;
}
