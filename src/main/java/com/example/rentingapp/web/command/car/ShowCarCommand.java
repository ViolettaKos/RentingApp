package com.example.rentingapp.web.command.car;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Car;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;

import static com.example.rentingapp.web.command.constants.Path.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.constants.Model.*;

public class ShowCarCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, CommandType commandType) throws ServiceException {
        CarsService carsService = ServiceFactory.getCarsService();
        Car car = carsService.getCarById(req.getParameter(CAR_ID));
        req.setAttribute(CAR, car);
        return PRODUCT_PAGE;
    }
}
