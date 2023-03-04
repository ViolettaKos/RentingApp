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

            try (PreparedStatement ps = connection.prepareStatement(INSERT_ORDER)) {
                prepareStForInsert(order, ps);
                ps.execute();
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DAOException(e);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    /**
     * Checks whether the date range of an order overlaps with the date range of any existing orders for the same car.
     *
     * @param order the order to check
     * @throws DAOException if an error occurs while accessing the database
     * @throws DAODuplicatedDateException if the order's date range overlaps with another order's date range for the same car
     */
    private void checkDates(Order order) throws DAOException {
        List<LocalDate> dateList = null;
        LOG.trace("FROM: " + order.getFrom() + " TO: " + order.getTo());
        List<LocalDate> orderDateList = getListFromTo(order.getFrom(), order.getTo());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_FROM_TO)) {
            ps.setInt(1, order.getCar_id());
            ResultSet rs = ps.executeQuery();
            String s, e;
            while (rs.next()) {
                s = rs.getString(FROM);
                e = rs.getString(TO);
                dateList = getListFromTo(s, e);
            }
            if (dateList != null && orderDateList.stream().anyMatch(dateList::contains)) {
                throw new DAODuplicatedDateException();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     Returns a list of LocalDate objects generated from the start and end dates given as string inputs.
     @param s the starting date as a string in the format "yyyy-MM-dd"
     @param e the ending date as a string in the format "yyyy-MM-dd"
     @return a list of LocalDate objects representing each date between the start and end dates (inclusive)
     */
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

    /**
     Retrieves a list of sorted orders from the database based on a specified command and limits the results to a certain range of records.
     @param command the command to be used for sorting the cars
     @param start the starting index of the range of records to be retrieved
     @param recordsPerPage the number of records to be retrieved per page
     @return a list of Car objects that are sorted according to the specified command and limited to the specified range of records
     @throws DAOException if there is an error accessing the database
     */
    @Override
    public List<Order> sortOrdersDB(String command, int start, int recordsPerPage) throws DAOException {
        List<Order> sortedOrders = new ArrayList<>();
        command = command + " " + LIMIT;
        LOG.trace("Command with limit: " + command);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(String.format(SORT_ORDERS, command))) {
            ps.setInt(1, start);
            ps.setInt(2, recordsPerPage);

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


    /**
     * Retrieves a list of all the dates that a car with the given ID is reserved for but has not yet been returned or rejected.
     *
     * @param car_id the ID of the car to retrieve the reserved dates for
     * @return a list of all the reserved dates for the car
     * @throws DAOException if there is an error accessing the database
     */
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
                    totalDates = getListFromTo(s, e);
                }
            }
        } catch (SQLException e) {
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
        ps.setString(1, order.getLogin());
        ps.setInt(2, order.getTotal_price());
        ps.setInt(3, order.getCar_id());
        ps.setDate(4, Date.valueOf(order.getFrom()));
        ps.setDate(5, Date.valueOf(order.getTo()));
        ps.setInt(6, (int) order.getTotal_days());
        ps.setBoolean(7, order.isOption());
    }

    private OrderInfo newOrderInfo(ResultSet rs) throws SQLException {
        return new OrderInfo(rs.getInt(ORDER_ID), rs.getInt(CAR_ID), rs.getString(LOGIN), rs.getString(FROM),
                rs.getString(TO), rs.getInt(TOTAL_DAYS), rs.getBoolean(OPTION), rs.getBoolean(IS_PAYED),
                rs.getBoolean(IS_REJECTED), rs.getString(BRAND), rs.getString(NAME),
                rs.getString(QUALITY), rs.getInt(PRICE), rs.getInt(TOTAL_PRICE), rs.getString(REASON_REJECT), rs.getBoolean(IS_RETURNED));
    }
}
