package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.LOGIN;
import static com.example.rentingapp.web.command.constants.Model.MANAGER;

public class DisplayInfoManager implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        UserService userService= ServiceFactory.getUserService();
        req.setAttribute(MANAGER, userService.getByLogin(req.getParameter(LOGIN)));
        return Path.EDIT_MANAGER_PAGE;
    }
}
