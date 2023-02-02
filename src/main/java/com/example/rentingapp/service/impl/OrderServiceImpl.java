package com.example.rentingapp.service.impl;

import com.example.rentingapp.dao.AbstractDAO;
import com.example.rentingapp.dao.OrderDAO;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.OrderInfo;
import com.example.rentingapp.service.OrderService;
import org.apache.log4j.Logger;

import java.time.LocalDate;
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

    @Override
    public List<Order> sortOrders(String command, int currentPage, int recordsPerPage) throws ServiceException {
        LOG.trace("sortOrders method");
        int start=currentPage*recordsPerPage-recordsPerPage;
        LOG.trace("Start parameter: "+start);
        try {
            return orderDAO.sortOrdersDB(command, start, recordsPerPage);
        } catch (DAOException e) {
            LOG.trace("Error in sorting cars");
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNumberOfRows(String command) throws ServiceException {
        LOG.trace("getNumberOfRows method");
        try {
            return orderDAO.getNumberOfRows(command);
        } catch (DAOException e) {
            LOG.trace("Error in getting number of rows");
            throw new ServiceException(e);
        }
    }

    @Override
    public OrderInfo getOrderInfo(int order_id) throws ServiceException {
        LOG.trace("Entering getOrderInfo method");
        try {
            return orderDAO.getOrderInfo(order_id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void rejectOrder(int order_id, String comment) throws ServiceException {
        LOG.trace("Entering rejectOrder method");
        try {
            orderDAO.rejectOrder(order_id, comment);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<LocalDate> getDatesByCar(int car_id) throws ServiceException {
        LOG.trace("Entering getDatesByCar method");
        try {
            return orderDAO.getDatesByCar(car_id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
