package com.example.project.web.command.user;

import com.example.project.exception.ServiceException;
import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.service.ServiceFactory;
import com.example.project.service.UserService;
import com.example.project.web.command.Command;
import com.example.project.web.command.CommandType;
import com.example.project.web.command.constants.Model;
import com.example.project.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class UserRegistrCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UserRegistrCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        return CommandType.GET==commandType ? doGet(request, response) : doPost(request, response);
    }

    private String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String firstname = req.getParameter("firstname");
        LOG.trace("Request parameter: fn --> " + firstname);
        String lastname = req.getParameter("lastname");
        LOG.trace("Request parameter: ln --> " + lastname);
        String username = req.getParameter("username");
        LOG.trace("Request parameter: username --> " + username);
        String pass = req.getParameter("pass");
        LOG.trace("Request parameter: pass --> " + pass);
        String repeated_pass = req.getParameter("repeated_pass");
        LOG.trace("Request parameter: repeated_pass --> " + repeated_pass);
        String email = req.getParameter("email");
        LOG.trace("Request parameter: email --> " + email);
        String telephone = req.getParameter("telephone");
        LOG.trace("Request parameter: telephone --> " + telephone);

        String path=null;
        try {
            User user=new User(firstname, lastname, username, email, pass, telephone, Role.USER, false, 0);
            UserService userService = ServiceFactory.getUserService();
            userService.add(user);
            req.getSession().setAttribute(Model.USER, user);
        } catch (ServiceException e) {
            LOG.trace("Error in executing command");
            req.getSession().setAttribute("msg", e.getMessage());
            path=Path.REDIRECT_USER_REGISTRATION_PAGE;
        }
        path=Path.PROFILE_PAGE;
        return path;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        LOG.trace("Forward to registration page");
        return Path.FORWARD_USER_REGISTRATION_PAGE;
    }
}
