package com.example.rentingapp.web.command.manager;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.admin.ShowAdminCarsCommand;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.CommandUtil.pagination;
import static com.example.rentingapp.web.command.CommandUtil.sortCommand;
import static com.example.rentingapp.web.command.constants.Commands.COMMAND;
import static com.example.rentingapp.web.command.constants.Model.ORDERS;

public class ViewOrdersCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ViewOrdersCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        pagination(req, sortCommand(req), ORDERS);
        req.setAttribute(COMMAND, req.getParameter(COMMAND));
        return Path.MNG_ORDERS_PAGE;
    }
}
