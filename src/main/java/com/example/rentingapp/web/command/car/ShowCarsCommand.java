package com.example.rentingapp.web.command.car;

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

public class ShowCarsCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, CommandType commandType) throws ServiceException {
        pagination(req, sortCommand(req), CAR);
        req.setAttribute(COMMAND, req.getParameter(COMMAND));
        return Path.BOOK_PAGE;
    }


}
