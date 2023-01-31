package com.example.rentingapp.web.command.admin;


import com.example.rentingapp.exception.*;
import com.example.rentingapp.model.Role;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.utils.Validator;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.constants.Commands.MANAGER_REG;
import static com.example.rentingapp.web.command.constants.Commands.SHOW_ADMIN_MANAGERS;

public class ManagerRegistrCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ManagerRegistrCommand.class);
    private final Validator validator=new Validator();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        return CommandType.GET==commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) throws ServiceException {
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
        String path= Path.ADMIN_MNG_PAGE;
        String command=SHOW_ADMIN_MANAGERS;
        User user=new User(firstname, lastname, username, email, pass, telephone, Role.MANAGER, false, 0);
        try {
            validator.isMatched(pass, repeated_pass);
            UserService userService = ServiceFactory.getUserService();
            userService.checkIfExists(username);
            userService.add(user);
        } catch (IncorrectDataException | IncorrectEmailException | DuplicatedLoginException |
                 PasswordNotMatchesException e) {
            LOG.trace("Error in executing command");
            req.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            path= Path.ADD_MNG_PAGE;
            command=MANAGER_REG;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return CommandUtil.redirectCommand(command);
    }

    private String doGet(HttpServletRequest request) {
        CommandUtil.setAttrToReq(request, Model.MESSAGE);
        LOG.trace("Path: "+CommandUtil.getPath(request));
        return CommandUtil.getPath(request);
    }
}
