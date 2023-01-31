package com.example.rentingapp.web.command.base;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;

import com.example.rentingapp.exception.ExcConstants;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.exception.UserNotExistsException;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.rentingapp.web.command.constants.Model.LOGGED;
import static com.example.rentingapp.web.command.constants.Model.MESSAGE;
import static org.junit.jupiter.api.Assertions.*;

import static com.example.rentingapp.constants.Constants.*;
import static com.example.rentingapp.constants.Constants.ROLE_USER_VAL;
import static org.mockito.Mockito.*;

public class LoginCommandTest {

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private UserService userService;


    @BeforeEach
    public void setUp() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        userService=mock(UserService.class);
    }

    @Test
    void doPostTest() throws ServiceException {
        User user=createUser();

        when(req.getParameter(PASS)).thenReturn(user.getPassword());
        when(req.getParameter(LOGIN)).thenReturn(user.getUsername());

        when(userService.login(req.getParameter(LOGIN), req.getParameter(PASS))).thenReturn(user);

        HttpSession session = mock(HttpSession.class);
        lenient().when(req.getSession()).thenReturn(session);
        lenient().when(req.getSession().getAttribute(LOGGED)).thenReturn(user);
        lenient().when(req.getSession().getAttribute(ROLE)).thenReturn(user.getRole());


        String actualPage = new LoginCommand(userService).execute(req, resp, CommandType.POST);
        String expectedPage= Path.PROFILE_PAGE;

        assertEquals(expectedPage, actualPage);
        assertEquals(user, req.getSession().getAttribute(LOGGED));
        assertEquals(user.getRole(), req.getSession().getAttribute(ROLE));
    }

    @Test
    void doPostExcTest() throws ServiceException {
        User user=createUser();

        when(req.getParameter(PASS)).thenReturn(user.getPassword());
        when(req.getParameter(LOGIN)).thenReturn(user.getUsername());

        when(userService.login(req.getParameter(LOGIN), req.getParameter(PASS))).thenThrow(new UserNotExistsException());

        HttpSession session = mock(HttpSession.class);
        lenient().when(req.getSession()).thenReturn(session);
        lenient().when(req.getSession().getAttribute(LOGIN)).thenReturn(LOGIN_VAL);
        lenient().when(req.getSession().getAttribute(MESSAGE)).thenReturn(ExcConstants.NO_USER);

        String actualPage = new LoginCommand(userService).execute(req, resp, CommandType.POST);
        String expectedPage= "controller?command=login";

        assertEquals(actualPage, expectedPage);
        assertEquals(ExcConstants.NO_USER, req.getSession().getAttribute(MESSAGE));
        assertEquals(LOGIN_VAL, req.getSession().getAttribute(LOGIN));
    }

    @Test
    void doGetTest() {
        when(req.getParameter(LOGIN)).thenReturn(createUser().getUsername());
        HttpSession session = mock(HttpSession.class);
        lenient().when(req.getSession()).thenReturn(session);
        lenient().when(req.getSession().getAttribute(LOGIN)).thenReturn(LOGIN_VAL);
        lenient().when(req.getSession().getAttribute(MESSAGE)).thenReturn(ExcConstants.NO_USER);

        String actualPage = new LoginCommand(userService).execute(req, resp, CommandType.GET);
        assertEquals(Path.LOGIN_PAGE, actualPage);
        assertNull(req.getAttribute(MESSAGE));
        assertNull(req.getAttribute(LOGIN));
        assertEquals(req.getSession().getAttribute(MESSAGE),ExcConstants.NO_USER);
        assertEquals(req.getSession().getAttribute(LOGIN), LOGIN_VAL);

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
