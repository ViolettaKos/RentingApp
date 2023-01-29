package com.example.rentingapp.dao;

import com.example.rentingapp.dao.DAOImpl.UserDAOImpl;
import com.example.rentingapp.exception.DAOException;
import com.example.rentingapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static com.example.rentingapp.constants.Constants.*;
import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOTest {
    DataSource dataSource;
    Connection connection;
    UserDAO userDAO;

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        userDAO = new UserDAOImpl(dataSource);
    }

    @Test
    void addUser() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            assertDoesNotThrow(() -> userDAO.addUser(createUser()));
        }
    }

    @Test
    void addUserEXC() throws SQLException {
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.addUser(createUser()));
    }

    @Test
    void updateUser() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            assertDoesNotThrow(() -> userDAO.updateUser(createUser()));
        }
    }

    @Test
    void updateUserEXC() throws SQLException {
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.updateUser(createUser()));
    }

    @Test
    void getUserByLogin() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            assertEquals(createUserOptional(), userDAO.getUserByLogin(createUser().getUsername()));
        }
    }

    @Test
    void getUserByLoginEXC() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> userDAO.getUserByLogin(createUser().getUsername()));
        }
    }

    @Test
    void checkIfExists() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            assertTrue(userDAO.checkIfExists(createUser().getUsername()));
            assertFalse(userDAO.checkIfExists("Login"));
        }
    }

    @Test
    void checkIfExistsEXC() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> userDAO.checkIfExists(createUser().getUsername()));

        }
    }

    @Test
    void changeAmount() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            assertTrue(userDAO.changeAmount(AMOUNT_VAL, LOGIN_VAL));
            assertTrue(userDAO.changeAmount(AMOUNT_VAL_MINUS, LOGIN_VAL));
        }
    }

    @Test
    void changeAmountEXC() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeUpdate()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> userDAO.changeAmount(AMOUNT_VAL, LOGIN_VAL));
            assertThrows(DAOException.class, () -> userDAO.changeAmount(AMOUNT_VAL_MINUS, LOGIN_VAL));
        }
    }

    @Test
    void updateStatus() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeUpdate()).thenReturn(1);
            prepareResults(rs);
            assertDoesNotThrow(() -> userDAO.updateStatus(LOGIN_VAL, true));
        }
    }

    @Test
    void updateStatusEXC() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeUpdate()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> userDAO.updateStatus(LOGIN_VAL, true));
        }
    }

    @Test
    void sortUsersDBTest() throws SQLException, DAOException {
        ResultSet rs = mock(ResultSet.class);
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenReturn(rs);
            prepareResults(rs);
            List<User> users = userDAO.sortUsersDB("", 0, 12);
            assertEquals(1, users.size());
            assertEquals(createUser(), users.get(0));
        }
    }

    @Test
    void sortUsersDBEXCTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> userDAO.sortUsersDB("", 0, 12));
        }
    }

    @Test
    void getNumberOfRowsTest() throws SQLException, DAOException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            ResultSet rs = mock(ResultSet.class);
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true);
            when(rs.getInt(NUMBER_OF_RECORDS)).thenReturn(12);
            int records = userDAO.getNumberOfRows("");
            assertEquals(12, records);
        }
    }

    @Test
    void getNumberOfRowsEXCTest() throws SQLException {
        try (PreparedStatement ps = prepareStatements(dataSource)) {
            when(ps.executeQuery()).thenThrow(new SQLException());
            assertThrows(DAOException.class, () -> userDAO.getNumberOfRows(""));
        }
    }

    private void prepareResults(ResultSet rs) throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getString(FIRST_NAME)).thenReturn(FIRSTNAME_VAL);
        when(rs.getString(LAST_NAME)).thenReturn(LASTNAME_VAL);
        when(rs.getString(LOGIN)).thenReturn(LOGIN_VAL);
        when(rs.getString(EMAIL)).thenReturn(EMAIL_VAL);
        when(rs.getString(PASS)).thenReturn(PASSWORD_VAL);
        when(rs.getString(TELEPHONE)).thenReturn(NUMBER_VAL);
        when(rs.getBoolean(BLOCKED)).thenReturn(UNBLOCKED_VAL);
        when(rs.getString(ROLE)).thenReturn(ROLE_USER_VAL);
        when(rs.getInt(MONEY)).thenReturn(AMOUNT_VAL);
        when(rs.getInt(USER_ID)).thenReturn(USER_ID_VAL);
    }

    private Optional<User> createUserOptional() {
        User user = new User();
        user.setId(USER_ID_VAL);
        user.setFirstName(FIRSTNAME_VAL);
        user.setLastName(LASTNAME_VAL);
        user.setUsername(LOGIN_VAL);
        user.setPassword(PASSWORD_VAL);
        user.setTelephone(NUMBER_VAL);
        user.setEmail(EMAIL_VAL);
        user.setBlocked(UNBLOCKED_VAL);
        user.setMoney(AMOUNT_VAL);
        user.setRole(ROLE_USER_VAL);
        return Optional.ofNullable(user);
    }

    private User createUser() {
        User user = new User();
        user.setId(USER_ID_VAL);
        user.setFirstName(FIRSTNAME_VAL);
        user.setLastName(LASTNAME_VAL);
        user.setUsername(LOGIN_VAL);
        user.setPassword(PASSWORD_VAL);
        user.setTelephone(NUMBER_VAL);
        user.setEmail(EMAIL_VAL);
        user.setBlocked(UNBLOCKED_VAL);
        user.setMoney(AMOUNT_VAL);
        user.setRole(ROLE_USER_VAL);
        return user;
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
