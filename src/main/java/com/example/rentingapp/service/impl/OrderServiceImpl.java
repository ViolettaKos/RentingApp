package com.example.rentingapp.service.impl;

import com.example.rentingapp.dao.AbstractDAO;
import com.example.rentingapp.dao.OrderDAO;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.OrderInfo;
import com.example.rentingapp.service.OrderService;
import org.apache.log4j.Logger;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class);
    private final OrderDAO orderDAO= AbstractDAO.getInstance().getOrderDAO();

    @Override
    public void putOrder(Order order) throws ServiceException {
        LOG.trace("Entering putOrder method");
        try {
           orderDAO.insertOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderInfo> getOrdersByLogin(String username) throws ServiceException {
        LOG.trace("Entering getOrdersByLogin method");
        try {
            return orderDAO.getInfoOrderByLogin(username);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updatePayment(int order_id) throws ServiceException {
        LOG.trace("Entering changeStatus method");
        try {
            orderDAO.updatePayment(order_id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
