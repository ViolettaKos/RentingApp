package com.example.rentingapp.dao.DAOImpl;

import com.example.rentingapp.dao.OrderDAO;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.OrderInfo;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.dao.DAOImpl.constants.OrderStatements.*;

public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOG = Logger.getLogger(OrderDAOImpl.class);
    private final DataSource dataSource;

    public OrderDAOImpl(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    @Override
    public void insertOrder(Order order) throws DAOException {
        try (Connection connection=dataSource.getConnection();
             PreparedStatement ps= connection.prepareStatement(INSERT_ORDER)) {
            prepareStForInsert(order, ps);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<OrderInfo> getInfoOrderByLogin(String username) throws DAOException {
        List<OrderInfo> orderInfos=new ArrayList<>();
        try (Connection connection=dataSource.getConnection();
             PreparedStatement ps= connection.prepareStatement(SELECT_BY_LOGIN_ORDER_AND_REL)) {
            ps.setString(1, username);
            ResultSet rs= ps.executeQuery();
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
        try (Connection connection=dataSource.getConnection();
             PreparedStatement ps= connection.prepareStatement(UPDATE_PAYMENT)) {
            ps.setInt(1, order_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
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
        return new OrderInfo(rs.getInt(ORDER_ID), rs.getInt(CAR_ID), rs.getString(LOGIN), rs.getDate(FROM),
                rs.getDate(TO), rs.getInt(TOTAL_DAYS), rs.getBoolean(OPTION), rs.getBoolean(IS_PAYED),
                rs.getBoolean(IS_REJECTED), rs.getBoolean(AVAILABLE), rs.getString(BRAND), rs.getString(NAME),
                rs.getString(QUALITY), rs.getInt(PRICE), rs.getInt(TOTAL_PRICE), rs.getString(REASON_REJECT));
    }
}