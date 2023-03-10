package com.example.rentingapp.web.command.base;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;

import com.example.rentingapp.dao.DAOImpl.constants.Fields;
import com.example.rentingapp.exception.ServiceException;

import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;

import static com.example.rentingapp.web.command.CommandUtil.*;

import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class LoginCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    private final UserService userService;

    public LoginCommand() {
        this.userService = ServiceFactory.getUserService();
    }

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) {
        return CommandType.GET == commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest request) {
        String login = request.getParameter(LOGIN);
        String pass = request.getParameter(PASS);
        try {
            User user = userService.login(login, pass);
            request.getSession().setAttribute(Model.LOGGED, user);
            request.getSession().setAttribute(Fields.ROLE, user.getRole());
            return Path.PROFILE_PAGE;
        } catch (ServiceException e) {
            request.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            request.getSession().setAttribute(LOGIN, login);
        }
        return redirectCommand(Commands.LOGIN);
    }

    private String doGet(HttpServletRequest request) {
        setAttrToReq(request, LOGIN);
        setAttrToReq(request, Model.MESSAGE);
        return Path.LOGIN_PAGE;
    }
}
