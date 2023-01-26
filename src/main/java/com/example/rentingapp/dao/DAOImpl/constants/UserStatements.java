package com.example.rentingapp.dao.DAOImpl.constants;

public interface UserStatements {

    String INSERT_USER = "INSERT INTO user (email, telephone, password, first_name, last_name, login, role) "
            + "VALUES (?,?,?,?,?,?,?)";

    String SELECT_USER_BY_LOGIN =
            "SELECT * FROM user WHERE login=?";

    String UPDATE_USER =
            "UPDATE user SET first_name=?, last_name=?, login=?, password=?, email=?, telephone=? WHERE user_id=?";

    String UPDATE_MONEY_MINUS =
            "UPDATE user SET money=IF(user.money IS NULL OR money = '', ? ,money - ?) WHERE login=?";

    String UPDATE_MONEY_PLUS =
            "UPDATE user SET money=IF(user.money IS NULL OR money = '', ? ,money + ?) WHERE login=?";
}
