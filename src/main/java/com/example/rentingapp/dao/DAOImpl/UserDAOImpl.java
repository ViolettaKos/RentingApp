package com.example.rentingapp.dao.DAOImpl;


import com.example.rentingapp.dao.UserDAO;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.Car;
import com.example.rentingapp.model.User;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.rentingapp.dao.DAOImpl.constants.CarStatements.GET_NUMBER_OF_RECORDS_CAR;
import static com.example.rentingapp.dao.DAOImpl.constants.CarStatements.SORT_CARS;
import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.dao.DAOImpl.constants.UserStatements.*;

public class UserDAOImpl implements UserDAO {

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);
    private final DataSource dataSource;
    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        LOG.trace("Datasource in constructor: "+dataSource.toString());
    }

    @Override
    public void addUser(User user) throws DAOException {
        try (Connection connection=dataSource.getConnection();
             PreparedStatement ps= connection.prepareStatement(INSERT_USER)) {
            prepareStForInsert(user, ps);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateUser(User user) throws DAOException {
        try (Connection connection=dataSource.getConnection();
             PreparedStatement ps= connection.prepareStatement(UPDATE_USER)) {
            prepareStForUpdate(user, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public Optional<User> getUserByLogin(String login) throws DAOException {
        User user = null;
        try (Connection connection=dataSource.getConnection();
             PreparedStatement ps= connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            ps.setString(1, login);
            LOG.trace("Statement to get user from database by login: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            LOG.trace("Getting user...");
            if (rs.next()) {
                user = newUser(rs);
                LOG.trace("Obtained user: " + user);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean checkIfExists(String username) throws DAOException {
        try (Connection connection=dataSource.getConnection();
             PreparedStatement ps= connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            ps.setString(1, username);
            LOG.trace("Statement to get user from database by login: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                LOG.trace("No such user in DB");
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean changeAmount(int amount, String login) throws DAOException {
        LOG.trace("Entering changeAmount");
        LOG.trace("Amount of money: " + amount);
        if (amount > 0) {
            LOG.trace("Adding money in process");
            addMoney(amount, login);
        } else {
            LOG.trace("Removing money in process");
            removeMoney(amount, login);
        }
        return true;
    }

    @Override
    public List<User> sortUsersDB(String command, int start, int recordsPerPage) throws DAOException {
        LOG.trace("sortUsersDB method");
        List<User> sortedUsers = new ArrayList<>();
        command =command + " " + LIMIT;
        try ( Connection connection = dataSource.getConnection();
              PreparedStatement ps = connection.prepareStatement(String.format(SORT_USERS, command))) {
            ps.setInt(1, start);
            ps.setInt(2, recordsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sortedUsers.add(newUser(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return sortedUsers;
    }

    @Override
    public int getNumberOfRows(String filter) throws DAOException {
        LOG.trace("getNumberOfRows method");
        int records = 0;
        LOG.trace("Filter: " + filter);
        String command = String.format(GET_NUMBER_OF_RECORDS_USER, filter);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(command)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                records = rs.getInt(NUM_OF_REC);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return records;
    }

    @Override
    public void updateStatus(String login, boolean action) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_STATUS)) {
            ps.setBoolean(1, action);
            ps.setString(2, login);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updatePass(String username, String newPass) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_PASS)) {
            ps.setString(1, newPass);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    private void removeMoney(int amount, String login) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_MONEY_MINUS)) {
            LOG.trace("Amount of money to remove: " + amount);
            amount = Math.abs(amount);
            ps.setString(1, String.valueOf(amount));
            ps.setString(2, String.valueOf(amount));
            ps.setString(3, login);
            LOG.trace("Statement to update amount of money: " + ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private void addMoney(int amount, String login) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_MONEY_PLUS)) {
            LOG.trace("Amount of money to add: " + amount);
            ps.setString(1, String.valueOf(amount));
            ps.setString(2, String.valueOf(amount));
            ps.setString(3, login);
            LOG.trace("Statement to update amount of money: " + ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    private void prepareStForInsert(User obj, PreparedStatement ps) throws SQLException {
        ps.setString(1, obj.getEmail());
        LOG.trace("Email: " + obj.getEmail());
        ps.setString(2, obj.getTelephone());
        LOG.trace("Number: " + obj.getTelephone());
        ps.setString(3, obj.getPassword());
        LOG.trace("Password: " + obj.getPassword());
        ps.setString(4, obj.getFirstName());
        LOG.trace("First name: " + obj.getFirstName());
        ps.setString(5, obj.getLastName());
        LOG.trace("Last name: " + obj.getLastName());
        ps.setString(6, obj.getUsername());
        LOG.trace("Username: " + obj.getUsername());
        ps.setString(7, obj.getRole());
        LOG.trace("Role: " + obj.getRole());
    }

    private void prepareStForUpdate(User obj, PreparedStatement ps) throws SQLException{
        ps.setString(5, obj.getEmail());
        LOG.trace("Email: " + obj.getEmail());
        ps.setString(6, obj.getTelephone());
        LOG.trace("Number: " + obj.getTelephone());
        ps.setString(4, obj.getPassword());
        LOG.trace("Password: " + obj.getPassword());
        ps.setString(1, obj.getFirstName());
        LOG.trace("First name: " + obj.getFirstName());
        ps.setString(2, obj.getLastName());
        LOG.trace("Last name: " + obj.getLastName());
        ps.setString(3, obj.getUsername());
        LOG.trace("Username: " + obj.getUsername());
        ps.setInt(7, obj.getId());
        LOG.trace("id: " + obj.getId());
    }

    private User newUser(ResultSet rs) throws SQLException {
        User user=new User();
        user.setId(rs.getInt(USER_ID));
        user.setFirstName(rs.getString(FIRST_NAME));
        user.setLastName(rs.getString(LAST_NAME));
        user.setUsername(rs.getString(LOGIN));
        user.setPassword(rs.getString(PASS));
        user.setTelephone(rs.getString(TELEPHONE));
        user.setEmail(rs.getString(EMAIL));
        user.setBlocked(rs.getBoolean(BLOCKED));
        user.setMoney(rs.getInt(MONEY));
        user.setRole(rs.getString(ROLE));
        return user;
    }
}
