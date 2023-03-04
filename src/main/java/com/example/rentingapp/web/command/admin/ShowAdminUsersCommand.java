package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.CommandUtil.pagination;
import static com.example.rentingapp.web.command.CommandUtil.sortCommand;
import static com.example.rentingapp.web.command.constants.Commands.COMMAND;
import static com.example.rentingapp.web.command.constants.Model.USER;

public class ShowAdminUsersCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        pagination(req, sortCommand(req), USER);
        req.setAttribute(COMMAND, req.getParameter(COMMAND));
        return Path.ADMIN_USERS_PAGE;
    }
}
