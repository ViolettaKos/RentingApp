package com.example.rentingapp.dao;

import com.example.rentingapp.dao.DAOImpl.OrderDAOImpl;
import com.example.rentingapp.exception.DAODuplicatedDateException;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.OrderInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static com.example.rentingapp.constants.Constants.*;
import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class OrderDAOTest {
    DataSource dataSource;
    Connection connection;
    OrderDAO orderDAO;
    ResultSet rs;

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        orderDAO=new OrderDAOImpl(dataSource);
        rs=mock(ResultSet.class);
    }

    @Test
    void insertOrderTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString(FROM)).thenReturn(FROM_NEW_VAL);
            when(rs.getString(TO)).thenReturn(TO_NEW_VAL);
            assertDoesNotThrow(() -> orderDAO.insertOrder(createOrder()));
        }
    }

    @Test
    void insertOrderExcDuplicateTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString(FROM)).thenReturn(FROM_VAL);
            when(rs.getString(TO)).thenReturn(TO_VAL);
            assertThrows(DAODuplicatedDateException.class, () ->  orderDAO.insertOrder(createOrder()));

            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () ->  orderDAO.insertOrder(createOrder()));
        }
    }

    @Test
    void insertOrderEXCTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString(FROM)).thenReturn(FROM_NEW_VAL);
            when(rs.getString(TO)).thenReturn(TO_NEW_VAL);

            when(ps.execute()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () ->  orderDAO.insertOrder(createOrder()));
        }
    }

    @Test
    void updatePaymentTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeUpdate()).thenReturn(1);
            prepareResults(rs);
            assertDoesNotThrow(() -> orderDAO.updatePayment(ORDER_ID_VAL));
        }
    }

    @Test
    void updatePaymentEXCTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeUpdate()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> orderDAO.updatePayment(ORDER_ID_VAL));
        }
    }

    @Test
    void sortOrderDBTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            List<Order> orders = orderDAO.sortOrdersDB("", 0, 3);
            assertEquals(1, orders.size());
            assertEquals(createOrder(), orders.get(0));
        }
    }

    @Test
    void sortOrderDBEXCTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> orderDAO.sortOrdersDB("", 0, 12));
        }
    }

    @Test
    void getNumberOfRowsTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true);
            when(rs.getInt(NUMBER_OF_RECORDS)).thenReturn(12);
            int records = orderDAO.getNumberOfRows("");
            assertEquals(12, records);
        }
    }

    @Test
    void getNumberOfRowsEXCTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> orderDAO.getNumberOfRows(""));
        }
    }

    @Test
    void getInfoOrderTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            assertEquals(createOrderInfo(), orderDAO.getOrderInfo(ORDER_ID_VAL));
        }
    }

    @Test
    void getInfoOrderEXC() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            prepareResults(rs);
            assertThrows(DAOException.class, () -> orderDAO.getOrderInfo(ORDER_ID_VAL));
        }
    }

    @Test
    void rejectOrderTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeUpdate()).thenReturn(1);
            prepareResults(rs);
            assertDoesNotThrow(() -> orderDAO.rejectOrder(ORDER_ID_VAL, REASON_VAL));
        }
    }

    @Test
    void rejectOrderEXC() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeUpdate()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> orderDAO.rejectOrder(ORDER_ID_VAL, REASON_VAL));
        }
    }

    @Test
    void getInfoOrdersByLoginTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            List<OrderInfo> orderInfos = orderDAO.getInfoOrderByLogin(LOGIN_VAL);
            assertEquals(1, orderInfos.size());
            assertEquals(createOrderInfo(), orderInfos.get(0));
        }
    }

    @Test
    void getInfoOrdersByLoginEXC() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> orderDAO.getInfoOrderByLogin(LOGIN_VAL));
        }
    }

    @Test
    void getOrderByIdTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            assertEquals(createOrder(), orderDAO.getOrderById(ORDER_ID_VAL));
        }
    }

    @Test
    void getOrderByIdEXC() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            prepareResults(rs);
            assertThrows(DAOException.class, () -> orderDAO.getOrderById(ORDER_ID_VAL));
        }
    }

    @Test
    void updateReturnTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeUpdate()).thenReturn(1);
            prepareResults(rs);
            assertDoesNotThrow(() -> orderDAO.updateReturn(ORDER_ID_VAL));
        }
    }

    @Test
    void updateReturnEXCTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeUpdate()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> orderDAO.updateReturn(ORDER_ID_VAL));
        }
    }

    @Test
    void getDatesByCarTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true).thenReturn(false);
            when(rs.getString(FROM)).thenReturn(FROM_VAL);
            when(rs.getString(TO)).thenReturn(TO_VAL);
            when(rs.getBoolean(IS_RETURNED)).thenReturn(false);
            when(rs.getBoolean(IS_REJECTED)).thenReturn(false);
            List<LocalDate> orders = orderDAO.getDatesByCar(CAR_ID_VAL);
            assertEquals(4, orders.size());
            assertEquals(FROM_VAL, orders.get(0).toString());
        }
    }

    @Test
    void getDatesByCarEXCTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> orderDAO.getDatesByCar(CAR_ID_VAL));
        }
    }

    private void prepareResults(ResultSet rs) throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getString(REASON_REJECT)).thenReturn(REASON_VAL);
        when(rs.getString(FROM)).thenReturn(FROM_VAL);
        when(rs.getString(TO)).thenReturn(TO_VAL);
        when(rs.getString(LOGIN)).thenReturn(LOGIN_VAL);
        when(rs.getInt(CAR_ID)).thenReturn(CAR_ID_VAL);
        when(rs.getInt(ORDER_ID)).thenReturn(ORDER_ID_VAL);
        when(rs.getInt(TOTAL_PRICE)).thenReturn(TOTAL_PRICE_VAL);
        when(rs.getInt(TOTAL_DAYS)).thenReturn(TOTAL_DAYS_VAL);
        when(rs.getBoolean(IS_REJECTED)).thenReturn(false);
        when(rs.getBoolean(IS_PAYED)).thenReturn(false);
        when(rs.getBoolean(IS_REJECTED)).thenReturn(false);
        when(rs.getBoolean(OPTION)).thenReturn(false);


        when(rs.getString(LOGIN)).thenReturn(LOGIN_VAL);
        when(rs.getString(BRAND)).thenReturn(BRAND_VAL);
        when(rs.getString(QUALITY)).thenReturn(QUALITY_VAL);
        when(rs.getString(NAME)).thenReturn(NAME_VAL);
        when(rs.getInt(PRICE)).thenReturn(PRICE_VAL);
        when(rs.getString(LOGIN)).thenReturn(LOGIN_VAL);
    }

    private Order createOrder() {
        Order order=new Order();
        order.setTotal_price(TOTAL_PRICE_VAL);
        order.setTotal_days(TOTAL_DAYS_VAL);
        order.setTo(TO_VAL);
        order.setFrom(FROM_VAL);
        order.setCar_id(CAR_ID_VAL);
        order.setRejected(false);
        order.setPayed(false);
        order.setOption(false);
        order.setComment(REASON_VAL);
        order.setId(ORDER_ID_VAL);
        order.setLogin(LOGIN_VAL);
        return order;
    }

    private OrderInfo createOrderInfo() {
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setTotal_price(TOTAL_PRICE_VAL);
        orderInfo.setTotal_days(TOTAL_DAYS_VAL);
        orderInfo.setTo(TO_VAL);
        orderInfo.setFrom(FROM_VAL);
        orderInfo.setCar_id(CAR_ID_VAL);
        orderInfo.setRejected(false);
        orderInfo.setPayed(false);
        orderInfo.setOption(false);
        orderInfo.setComment(REASON_VAL);
        orderInfo.setOrder_id(ORDER_ID_VAL);
        orderInfo.setLogin(LOGIN_VAL);
        orderInfo.setBrand(BRAND_VAL);
        orderInfo.setName(NAME_VAL);
        orderInfo.setQuality_class(QUALITY_VAL);
        orderInfo.setCar_price(PRICE_VAL);
        orderInfo.setComment(REASON_VAL);
        return orderInfo;
    }

    private PreparedStatement prepareStatements(DataSource dataSource) throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(dataSource.getConnection().prepareStatement(isA(String.class))).thenReturn(preparedStatement);

        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        doNothing().when(preparedStatement).setBoolean(isA(int.class), isA(boolean.class));
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));

        when(preparedStatement.execute()).thenReturn(true);
        return preparedStatement;
    }
}
