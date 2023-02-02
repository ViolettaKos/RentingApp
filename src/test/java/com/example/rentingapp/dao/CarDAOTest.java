package com.example.rentingapp.dao;

import com.example.rentingapp.dao.DAOImpl.CarDAOImpl;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Set;

import static com.example.rentingapp.constants.Constants.*;
import static com.example.rentingapp.dao.DAOImpl.constants.CarStatements.SELECT_BRANDS;
import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class CarDAOTest {
    DataSource dataSource;
    Connection connection;
    CarDAO carDAO;
    ResultSet rs;

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        carDAO = new CarDAOImpl(dataSource);
        rs=mock(ResultSet.class);
    }

    @Test
    void sortCarsDBTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            List<Car> cars = carDAO.sortCarsDB("", 0, 3);
            assertEquals(1, cars.size());
            assertEquals(createCar(), cars.get(0));
        }
    }

    @Test
    void sortCarsDBEXCTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> carDAO.sortCarsDB("", 0, 12));
        }
    }

    @Test
    void getNumberOfRowsTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true);
            when(rs.getInt(NUMBER_OF_RECORDS)).thenReturn(12);
            int records = carDAO.getNumberOfRows("");
            assertEquals(12, records);
        }
    }

    @Test
    void getNumberOfRowsEXCTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> carDAO.getNumberOfRows(""));
        }
    }

    @Test
    void getById() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            assertEquals(createCar(), carDAO.getById(createCar().getId()));
        }
    }

    @Test
    void getByIdEXC() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> carDAO.getById(createCar().getId()));
        }
    }





    @Test
    void getBrandsTest() throws SQLException, DAOException {
        try (Statement st = prepareSt(dataSource)) {
            when(st.executeQuery(SELECT_BRANDS)).thenReturn(rs);
            prepareResults(rs);
            Set<String> brands = carDAO.getBrands();
            assertEquals(1, brands.size());
            assertTrue(brands.contains(BRAND_VAL));
        }
    }

    @Test
    void getBrandsEXCTest() throws SQLException {
        try (Statement st = prepareSt(dataSource)) {
            when(st.executeQuery(SELECT_BRANDS)).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> carDAO.getBrands());
        }
    }

    @Test
    void insertCar() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            assertDoesNotThrow(() -> carDAO.insertCar(createCar()));
        }
    }

    @Test
    void insertCarEXC() throws SQLException {
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> carDAO.insertCar(createCar()));
    }

    @Test
    void updateCar() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            assertDoesNotThrow(() -> carDAO.updateCar(createCar()));
        }
    }

    @Test
    void updateCarEXC() throws SQLException {
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> carDAO.updateCar(createCar()));
    }

    @Test
    void deleteCar() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            assertDoesNotThrow(() -> carDAO.deleteCar(CAR_ID_VAL));
        }
    }

    @Test
    void deleteCarEXC() throws SQLException {
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> carDAO.deleteCar(CAR_ID_VAL));
    }

    private Car createCar() {
        Car car=new Car();
        car.setBrand(BRAND_VAL);
        car.setName(NAME_VAL);
        car.setPrice(PRICE_VAL);
        car.setId(CAR_ID_VAL);
        car.setQuality_class(QUALITY_VAL);
        return car;
    }

    private void prepareResults(ResultSet rs) throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getString(BRAND)).thenReturn(BRAND_VAL);
        when(rs.getString(QUALITY)).thenReturn(QUALITY_VAL);
        when(rs.getString(NAME)).thenReturn(NAME_VAL);
        when(rs.getInt(CAR_ID)).thenReturn(CAR_ID_VAL);
        when(rs.getInt(PRICE)).thenReturn(PRICE_VAL);
    }

    private Statement prepareSt(DataSource dataSource) throws SQLException {
        Statement statement = mock(Statement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(dataSource.getConnection().createStatement()).thenReturn(statement);
        return statement;
    }

    private PreparedStatement prepareStatements(DataSource dataSource) throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(dataSource.getConnection().prepareStatement(isA(String.class))).thenReturn(preparedStatement);

        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        doNothing().when(preparedStatement).setBoolean(isA(int.class), isA(boolean.class));
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));

        when(preparedStatement.execute()).thenReturn(true);
        return preparedStatement;
    }
}
