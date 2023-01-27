package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Commands;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.constants.Model.ID;

public class DeleteCarCommand implements Command {
    private static final Logger LOG = Logger.getLogger(DeleteCarCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.trace("Starting deleting car");
        int car_id = Integer.parseInt(request.getParameter(ID));
        CarsService carsService = ServiceFactory.getCarsService();
        carsService.deleteCarById(car_id);
        return CommandUtil.redirectCommand(Commands.SHOW_ADMIN_CARS);
    }
}
