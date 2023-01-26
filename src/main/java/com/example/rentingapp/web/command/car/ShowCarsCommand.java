package com.example.rentingapp.web.command.base;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import com.example.rentingapp.web.command.user.UserRegistrCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.constants.Model.CAR;

public class ShowCarsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UserRegistrCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        CarsService carsService= ServiceFactory.getCarsService();
        request.setAttribute(CAR, carsService.getCars());
        return Path.BOOK_PAGE;
    }


}
