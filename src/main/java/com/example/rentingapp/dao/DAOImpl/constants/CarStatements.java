package com.example.rentingapp.dao.DAOImpl.constants;

public interface CarStatements {
    String SELECT_ALL_CARS =
            "SELECT * FROM car LIMIT 10";

    String SORT_CARS="SELECT * FROM car %s";

    String GET_NUMBER_OF_RECORDS_CAR =
            "SELECT COUNT(car_id) AS numberOfRecords FROM CAR %s";
    String SELECT_BY_ID_CAR=
            "SELECT * FROM car WHERE car_id=?";
    String UPDATE_AVAILABILITY_BY_ID =
            "UPDATE car SET isAvailable = ? WHERE car_id=?";
}
