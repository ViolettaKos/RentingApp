package com.example.rentingapp.dao;

import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.OrderInfo;

import java.time.LocalDate;
import java.util.List;

public interface OrderDAO {
    void insertOrder(Order order) throws DAOException;

    List<OrderInfo> getInfoOrderByLogin(String username) throws DAOException;

    void updatePayment(int order_id) throws DAOException;

    List<Order> sortOrdersDB(String command, int start, int recordsPerPage) throws DAOException;

    int getNumberOfRows(String command) throws DAOException;

    OrderInfo getOrderInfo(int order_id) throws DAOException;

    void rejectOrder(int order_id, String comment) throws DAOException;

    List<LocalDate> getDatesByCar(int car_id) throws DAOException;

    Order getOrderById(int order_id) throws DAOException;

    void updateReturn(int order_id) throws DAOException;
}
