package com.example.rentingapp.dao.DAOImpl.constants;

public interface OrderStatements {
    String INSERT_ORDER = "INSERT INTO `order` (login, total_price, car_id, `from`, `to`, total_days, `option`)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";
    String SELECT_BY_LOGIN_ORDER_AND_REL =
            "SELECT * FROM `order` INNER JOIN car on `order`.car_id = car.car_id WHERE login=?";
    String UPDATE_PAYMENT = "UPDATE `order` SET isPayed=true WHERE order_id=?";
    String SORT_ORDERS="SELECT * FROM `order` %s";
    String GET_NUMBER_OF_RECORDS_ORDER =
            "SELECT COUNT(order_id) AS numberOfRecords FROM `order` %s";
    String SELECT_BY_ID_ORDER_INFO_AND_REL =
            "SELECT * FROM `order` INNER JOIN car on `order`.car_id = car.car_id WHERE order_id=?";

    String REJECT_AND_COMMENT="UPDATE `order` SET isRejected=true, reason_for_reject=? WHERE order_id=?";
}
