package com.example.rentingapp.dao.DAOImpl;

import com.example.rentingapp.dao.CarDAO;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;

import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Car;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.rentingapp.dao.DAOImpl.constants.CarStatements.*;

public class CarDAOImpl implements CarDAO {

    private static final Logger LOG = Logger.getLogger(CarDAOImpl.class);
    private final DataSource dataSource;

    public CarDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Car> getCars() throws DAOException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_ALL_CARS);
            LOG.trace("Statement to select cars: " + st);
            while (rs.next()) {
                cars.add(newCar(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return cars;
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
    public void updateAvailability(int car_id, boolean isAvailable) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_AVAILABILITY_BY_ID)) {
            ps.setBoolean(1, isAvailable);
            ps.setInt(2, car_id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Car newCar(ResultSet rs) throws SQLException {
        Car car=new Car();
        car.setId(rs.getInt(CAR_ID));
        car.setBrand(rs.getString(BRAND));
        car.setQuality_class(rs.getString(QUALITY));
        car.setName(rs.getString(NAME));
        car.setPrice(rs.getInt(PRICE));
        car.setAvailable(rs.getBoolean(AVAILABLE));
        return car;
    }
}
