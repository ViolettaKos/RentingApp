package com.example.rentingapp.dao.DAOImpl;

import com.example.rentingapp.dao.CarDAO;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;

import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Car;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.rentingapp.dao.DAOImpl.constants.CarStatements.*;

public class CarDAOImpl implements CarDAO {

    private static final Logger LOG = Logger.getLogger(CarDAOImpl.class);
    private final DataSource dataSource;

    public CarDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Car> sortCarsDB(String command, int start, int recordsPerPage) throws DAOException {
        LOG.trace("sortCarsDB method");
        List<Car> sortedCars = new ArrayList<>();
        command = command + " " + LIMIT;
        LOG.trace("Command with limit: " + command);
        try ( Connection connection = dataSource.getConnection();
              PreparedStatement ps = connection.prepareStatement(String.format(SORT_CARS, command))) {
            ps.setInt(1, start);
            ps.setInt(2, recordsPerPage);
            LOG.trace("Statement: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sortedCars.add(newCar(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return sortedCars;
    }

    @Override
    public int getNumberOfRows(String filter) throws DAOException {
        LOG.trace("getNumberOfRows method");
        int records = 0;
        LOG.trace("Filter: " + filter);
        String command = String.format(GET_NUMBER_OF_RECORDS_CAR, filter);
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
    public Car getById(int id) throws DAOException {
        LOG.trace("getById method");
        Car car = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_CAR)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                car = newCar(rs);
                LOG.trace(car.toString());
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return car;
    }

    @Override
    public Set<String> getBrands() throws DAOException {
        Set<String> brands = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_BRANDS);
            while (rs.next()) {
                brands.add(rs.getString(BRAND));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return brands;
    }

    @Override
    public void insertCar(Car car) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_CAR)) {
            prepareStForInsert(car, ps);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public String getLastId() throws DAOException {
        String id = null;
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_LAST_ID);
            while (rs.next()) {
                id=rs.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        LOG.trace("Last id: "+id);
        return id;
    }

    @Override
    public void updateCar(Car car) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_BY_ID_CAR)) {
            prepareStForUpdate(car, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteCar(int car_id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_CAR)) {
            ps.setInt(1, car_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private void prepareStForUpdate(Car car, PreparedStatement ps) throws SQLException {
        ps.setString(1, car.getBrand());
        ps.setString(2, car.getQuality_class());
        ps.setString(3, car.getName());
        ps.setInt(4,car.getPrice());
        ps.setInt(5, car.getId());
    }

    private void prepareStForInsert(Car car, PreparedStatement ps) throws SQLException {
        ps.setString(1, car.getBrand());
        ps.setString(2, car.getQuality_class());
        ps.setString(3, car.getName());
        ps.setInt(4, car.getPrice());
    }

    private Car newCar(ResultSet rs) throws SQLException {
        Car car=new Car();
        car.setId(rs.getInt(CAR_ID));
        car.setBrand(rs.getString(BRAND));
        car.setQuality_class(rs.getString(QUALITY));
        car.setName(rs.getString(NAME));
        car.setPrice(rs.getInt(PRICE));
        return car;
    }
}
