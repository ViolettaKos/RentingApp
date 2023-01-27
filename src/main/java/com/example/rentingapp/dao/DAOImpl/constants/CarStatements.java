package com.example.rentingapp.dao.DAOImpl.constants;

public interface CarStatements {
    String SORT_CARS="SELECT * FROM car %s";

    String GET_NUMBER_OF_RECORDS_CAR =
            "SELECT COUNT(car_id) AS numberOfRecords FROM CAR %s";
    String SELECT_BY_ID_CAR=
            "SELECT * FROM car WHERE car_id=?";
    String UPDATE_AVAILABILITY_BY_ID =
            "UPDATE car SET isAvailable = ? WHERE car_id=?";
    String SELECT_BRANDS =
            "SELECT brand FROM car";
    String INSERT_CAR =
            "INSERT INTO car (brand, quality_class, name, price)" +
                    " VALUES (?, ?, ?, ?)";
    String SELECT_LAST_ID = "SELECT LAST_INSERT_ID()";
    String UPDATE_BY_ID_CAR =
            "UPDATE car SET brand = ?, quality_class = ?, name = ?, price = ? WHERE car_id=?";
    String DELETE_CAR =
            "DELETE FROM car WHERE car_id=?";
}
