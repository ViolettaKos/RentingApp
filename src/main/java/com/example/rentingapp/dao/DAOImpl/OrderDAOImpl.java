package com.example.rentingapp.dao.DAOImpl;

import com.example.rentingapp.dao.OrderDAO;
import com.example.rentingapp.exception.DAODuplicatedDateException;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.exception.ExcConstants;
import com.example.rentingapp.model.Car;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.OrderInfo;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.rentingapp.dao.DAOImpl.constants.CarStatements.GET_NUMBER_OF_RECORDS_CAR;
import static com.example.rentingapp.dao.DAOImpl.constants.CarStatements.SORT_CARS;
import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.dao.DAOImpl.constants.OrderStatements.*;

public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOG = Logger.getLogger(OrderDAOImpl.class);
    private final DataSource dataSource;

    public OrderDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insertOrder(Order order) throws DAOException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            checkDates(order);
            LOG.trace("Connection state1: "+connection.getAutoCommit());
            try (PreparedStatement ps = connection.prepareStatement(INSERT_ORDER)) {
                prepareStForInsert(order, ps);
                ps.execute();
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                LOG.trace("Connection state2: "+connection.getAutoCommit());
                throw new DAOException(e);
        }
            connection.commit();
            connection.setAutoCommit(true);
            LOG.trace("Connection state3: "+connection.getAutoCommit());
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private void checkDates(Order order) throws DAOException {
        List<LocalDate> dateList = null;
        LOG.trace("FROM: "+order.getFrom());
        LOG.trace("TO: "+order.getTo());
        List<LocalDate> orderDateList=getListFromTo(order.getFrom(), order.getTo());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_FROM_TO)) {
            ps.setInt(1, order.getCar_id());
            ResultSet rs = ps.executeQuery();
            String s, e;
            while (rs.next()) {
                s = rs.getString(FROM);
                e = rs.getString(TO);
                dateList=getListFromTo(s, e);
            }
            if (dateList != null && orderDateList.stream().anyMatch(dateList::contains)) {
                throw new DAODuplicatedDateException();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static List<LocalDate> getListFromTo(String s, String e) {
        List<LocalDate> totalDates = new ArrayList<>();
        LocalDate start = LocalDate.parse(s);
        LocalDate end = LocalDate.parse(e);
        while (!start.isAfter(end)) {
            totalDates.add(start);
            start = start.plusDays(1);
        }
        return totalDates;
    }

    @Override
    public List<OrderInfo> getInfoOrderByLogin(String username) throws DAOException {
        List<OrderInfo> orderInfos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_LOGIN_ORDER_AND_REL)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderInfos.add(newOrderInfo(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return orderInfos;
    }

    @Override
    public void updatePayment(int order_id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_PAYMENT)) {
            ps.setInt(1, order_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Order> sortOrdersDB(String command, int start, int recordsPerPage) throws DAOException {
        LOG.trace("sortOrdersDB method");
        List<Order> sortedOrders = new ArrayList<>();
        command = command + " " + LIMIT;
        LOG.trace("Command with limit: " + command);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(String.format(SORT_ORDERS, command))) {
            ps.setInt(1, start);
            ps.setInt(2, recordsPerPage);
            LOG.trace("Statement: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sortedOrders.add(newOrder(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return sortedOrders;
    }

    @Override
    public int getNumberOfRows(String filter) throws DAOException {
        int records = 0;
        LOG.trace("Filter: " + filter);
        String command = String.format(GET_NUMBER_OF_RECORDS_ORDER, filter);
        LOG.trace("Command: " + command);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(command)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                records = rs.getInt(NUM_OF_REC);
            LOG.trace("numberOfRecords: " + records);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return records;
    }

    @Override
    public OrderInfo getOrderInfo(int order_id) throws DAOException {
        OrderInfo orderInfo = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_ORDER_INFO_AND_REL)) {
            ps.setInt(1, order_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderInfo = newOrderInfo(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return orderInfo;
    }

    @Override
    public void rejectOrder(int order_id, String comment) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(REJECT_AND_COMMENT)) {
            ps.setString(1, comment);
            ps.setInt(2, order_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<LocalDate> getDatesByCar(int car_id) throws DAOException {
        List<LocalDate> totalDates = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(ALL_DATES)) {
            ps.setInt(1, car_id);
            ResultSet rs = ps.executeQuery();
            String s, e;

            while (rs.next()) {
                if (!rs.getBoolean(IS_REJECTED) && !rs.getBoolean(IS_RETURNED)) {
                    s = rs.getString(FROM);
                    e = rs.getString(TO);
                    totalDates=getListFromTo(s, e);
                }
            }
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return totalDates;
    }

    @Override
    public Order getOrderById(int order_id) throws DAOException {
        Order order = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ORDER)) {
            ps.setInt(1, order_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                order = newOrder(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return order;
    }

    @Override
    public void updateReturn(int order_id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_RETURN)) {
            ps.setInt(1, order_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Order newOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt(ORDER_ID));
        order.setComment(rs.getString(REASON_REJECT));
        order.setFrom(rs.getString(FROM));
        order.setLogin(rs.getString(LOGIN));
        order.setOption(rs.getBoolean(OPTION));
        order.setPayed(rs.getBoolean(IS_PAYED));
        order.setRejected(rs.getBoolean(IS_REJECTED));
        order.setTo(rs.getString(TO));
        order.setCar_id(rs.getInt(CAR_ID));
        order.setTotal_days(rs.getInt(TOTAL_DAYS));
        order.setTotal_price(rs.getInt(TOTAL_PRICE));
        order.setReturned(rs.getBoolean(IS_RETURNED));
        return order;
    }

    private void prepareStForInsert(Order order, PreparedStatement ps) throws SQLException {
        LOG.trace("Preparing statements for insert");
        ps.setString(1, order.getLogin());
        LOG.trace("Login: " + order.getLogin());
        ps.setInt(2, order.getTotal_price());
        LOG.trace("Total price: " + order.getTotal_price());
        ps.setInt(3, order.getCar_id());
        LOG.trace("Car id: " + order.getCar_id());
        ps.setDate(4, Date.valueOf(order.getFrom()));
        LOG.trace("From: " + order.getFrom());
        ps.setDate(5, Date.valueOf(order.getTo()));
        LOG.trace("To: " + order.getTo());
        ps.setInt(6, (int) order.getTotal_days());
        LOG.trace("Total days: " + (int) order.getTotal_days());
        ps.setBoolean(7, order.isOption());
        LOG.trace("Option: " + order.isOption());
    }

    private OrderInfo newOrderInfo(ResultSet rs) throws SQLException {
        return new OrderInfo(rs.getInt(ORDER_ID), rs.getInt(CAR_ID), rs.getString(LOGIN), rs.getString(FROM),
                rs.getString(TO), rs.getInt(TOTAL_DAYS), rs.getBoolean(OPTION), rs.getBoolean(IS_PAYED),
                rs.getBoolean(IS_REJECTED), rs.getString(BRAND), rs.getString(NAME),
                rs.getString(QUALITY), rs.getInt(PRICE), rs.getInt(TOTAL_PRICE), rs.getString(REASON_REJECT), rs.getBoolean(IS_RETURNED));
    }
}
