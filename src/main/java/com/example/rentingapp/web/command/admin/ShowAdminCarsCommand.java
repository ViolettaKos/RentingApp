package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import static com.example.rentingapp.web.command.CommandUtil.*;
import static com.example.rentingapp.web.command.constants.Commands.COMMAND;
import static com.example.rentingapp.web.command.constants.Model.CAR;

public class ShowAdminCarsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ShowAdminCarsCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        pagination(req, sortCommand(req), CAR);
        req.setAttribute(COMMAND, req.getParameter(COMMAND));
        return Path.ADMIN_CARS_PAGE;
    }

}
