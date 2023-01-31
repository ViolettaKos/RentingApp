package com.example.rentingapp.service;

import com.example.rentingapp.dao.UserDAO;
import com.example.rentingapp.exception.*;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.impl.UserServiceImpl;
import com.example.rentingapp.utils.HashingPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.rentingapp.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    UserService userService;
    UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    void addTest() throws DAOException {
        doNothing().when(userDAO).addUser(isA(User.class));
        assertDoesNotThrow(() -> userService.add(createUser()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234", "test", "test@u", "test@test.t", "test@ua.1ua"})
    void IncorrectEmailTest(String email) throws DAOException {
        doNothing().when(userDAO).addUser(isA(User.class));
        User user = createUser();
        user.setEmail(email);
        assertThrows(IncorrectEmailException.class, () -> userService.add(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"£££", "Соломія", "test@", "login$", "login 3"})
    void IncorrectDataTest(String login) throws DAOException {
        doNothing().when(userDAO).addUser(isA(User.class));
        User user = createUser();
        user.setUsername(login);
        assertThrows(IncorrectDataException.class, () -> userService.add(user));
    }

    @ParameterizedTest
    @CsvSource({"123, 123", "alina1, alina2", "test!, test£"})
    void IncorrectNamesTest(String firstName, String lastName) throws DAOException {
        doNothing().when(userDAO).addUser(isA(User.class));
        User user = createUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        assertThrows(IncorrectDataException.class, () -> userService.add(user));
    }

    @Test
    void addExcTEst() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(userDAO).addUser(isA(User.class));
        assertThrows(ServiceException.class, () -> userService.add(createUser()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"+375931455107", "+3809607", "3333333333", "+38096f752493", "test"})
    void IncorrectTelephoneTest(String telephone) throws DAOException {
        doNothing().when(userDAO).addUser(isA(User.class));
        User user = createUser();
        user.setTelephone(telephone);
        assertThrows(IncorrectDataException.class, () -> userService.add(user));
    }

    @Test
    void updateTest() throws DAOException {
        doNothing().when(userDAO).updateUser(isA(User.class));
        assertDoesNotThrow(() -> userService.update(createUser()));
    }

    @Test
    void updateExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(userDAO).updateUser(isA(User.class));
        assertThrows(ServiceException.class, () -> userService.update(createUser()));
    }

    @Test
    void getByLoginTest() throws DAOException {
        doReturn(Optional.of(createUser())).when(userDAO).getUserByLogin(isA(String.class));
        assertDoesNotThrow(() -> userService.getByLogin(LOGIN_VAL));
    }

    @Test
    void getByLoginExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(userDAO).getUserByLogin(isA(String.class));
        assertThrows(ServiceException.class, () -> userService.getByLogin(LOGIN_VAL));
    }

    @Test
    void loginTest() throws DAOException {
        User user = createUser();
        doReturn(Optional.of(user)).when(userDAO).getUserByLogin(isA(String.class));
        user.setPassword(HashingPassword.hash(PASSWORD_VAL));
        assertDoesNotThrow(() -> userService.login(LOGIN_VAL, PASSWORD_VAL));
    }

    @Test
    void loginExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(userDAO).getUserByLogin(isA(String.class));
        assertThrows(ServiceException.class, () -> userService.login(LOGIN_VAL, PASSWORD_VAL));
    }

    @Test
    void updateMoneyTest() throws DAOException, ServiceException {
        doReturn(true).when(userDAO).changeAmount(isA(Integer.class), isA(String.class));
        assertTrue(userService.updateMoney(LOGIN_VAL, AMOUNT_VAL));
    }

    @Test
    void updateMoneyExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(userDAO).changeAmount(isA(Integer.class), isA(String.class));
        assertThrows(ServiceException.class, () -> userService.updateMoney(LOGIN_VAL, AMOUNT_VAL));
    }

    @Test
    void sortUsersTest() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        users.add(createUser());
        doReturn(users).when(userDAO).sortUsersDB(isA(String.class), isA(Integer.class), isA(Integer.class));
        assertEquals(users, userService.sortUsers("", 1, 1));
    }

    @Test
    void sortUsersExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(userDAO).sortUsersDB(isA(String.class), isA(Integer.class), isA(Integer.class));
        assertThrows(ServiceException.class, () -> userService.sortUsers("", 1, 1));
    }

    @Test
    void getNumberOfRowsTest() throws DAOException, ServiceException {
        doReturn(1).when(userDAO).getNumberOfRows(isA(String.class));
        assertEquals(1, userService.getNumberOfRows(""));
    }

    @Test
    void getNumberOfRowsExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(userDAO).getNumberOfRows(isA(String.class));
        assertThrows(ServiceException.class, () -> userService.getNumberOfRows(""));
    }

    @Test
    void updateStatusTest() throws DAOException {
        doNothing().when(userDAO).updateStatus(isA(String.class), isA(Boolean.class));
        assertDoesNotThrow(() -> userService.updateStatus(LOGIN_VAL, true));
    }

    @Test
    void updateStatusExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(userDAO).updateStatus(isA(String.class), isA(Boolean.class));
        assertThrows(ServiceException.class, () -> userService.updateStatus(LOGIN_VAL, true));
    }

    @Test
    void checkIfExistsTest() throws DAOException {
        doReturn(false).when(userDAO).checkIfExists(isA(String.class));
        assertDoesNotThrow(() -> userService.checkIfExists(LOGIN_VAL));
    }

    @Test
    void checkIfExistsExcTest() throws DAOException {
        Exception exc = new DAOException(new SQLException());
        doThrow(exc).when(userDAO).checkIfExists(isA(String.class));
        assertThrows(ServiceException.class, () -> userService.checkIfExists(LOGIN_VAL));

        doReturn(true).when(userDAO).checkIfExists(isA(String.class));
        DuplicatedLoginException loginException = assertThrows(DuplicatedLoginException.class,
                () -> userService.checkIfExists(LOGIN_VAL));
        assertEquals(loginException.getMessage(), ExcConstants.DUPLICATE_LOGIN);
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
}
