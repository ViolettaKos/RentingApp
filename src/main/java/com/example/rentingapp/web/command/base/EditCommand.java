package com.example.rentingapp.web.command.base;

import com.example.rentingapp.exception.*;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class EditCommand implements Command {
    private static final Logger LOG = Logger.getLogger(EditCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        return CommandType.GET == commandType ? doGet(request) : doPost(request);
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
        String email = req.getParameter("email");
        LOG.trace("Request parameter: email --> " + email);
        String telephone = req.getParameter("telephone");
        String path = Path.PROFILE_PAGE;
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(Model.LOGGED);
            UserService userService = ServiceFactory.getUserService();
            if (!username.equals(user.getUsername())) {
                userService.checkIfExists(username);
            }
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setPassword(pass);
            user.setUsername(username);
            user.setEmail(email);
            user.setTelephone(telephone);

            userService.update(user);

        } catch (IncorrectDataException | IncorrectEmailException | DuplicatedLoginException e) {
            LOG.trace("Error in executing command");
            req.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            path = Path.EDIT_PAGE;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return CommandUtil.redirectCommand(Commands.EDIT);
    }



    private String doGet(HttpServletRequest request) {
        CommandUtil.setAttrToReq(request, Model.MESSAGE);
        LOG.trace("Path: " + CommandUtil.getPath(request));
        return CommandUtil.getPath(request);
    }
}
