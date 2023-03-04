package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.IncorrectDataException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Car;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.web.command.CommandUtil.redirectCommand;
import static com.example.rentingapp.web.command.constants.Commands.SHOW_ADMIN_CARS;
import static com.example.rentingapp.web.command.constants.Model.CAR;
import static com.example.rentingapp.web.command.constants.Model.ID;
import static com.example.rentingapp.web.command.constants.Path.*;

public class EditCarCommand implements Command {
    private static final Logger LOG = Logger.getLogger(EditCarCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        return CommandType.GET == commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) {
        String path = ADMIN_CARS_PAGE;
        try {
            if (checkIsEmpty(req))
                throw new IncorrectDataException();
            CarsService carsService = ServiceFactory.getCarsService();
            String brand = req.getParameter(BRAND);
            String name = req.getParameter(NAME);
            String quality = req.getParameter(QUALITY);
            int price = Integer.parseInt(req.getParameter(PRICE));
            String car_id = req.getParameter(ID);

            Car car = carsService.getCarById(car_id);
            car.setId(Integer.parseInt(car_id));
            car.setName(name);
            car.setPrice(price);
            car.setBrand(brand);
            car.setQuality_class(quality);

            carsService.updateCar(car);
        } catch (ServiceException e) {
            LOG.error("Error in extracting parts");
            path = ERROR_PAGE;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return redirectCommand(SHOW_ADMIN_CARS);
    }

    private String doGet(HttpServletRequest req) throws ServiceException {
        CarsService carsService = ServiceFactory.getCarsService();
        req.setAttribute(CAR, carsService.getCarById(req.getParameter(ID)));
        return EDIT_CAR_PAGE;
    }

    private static boolean checkIsEmpty(HttpServletRequest req) {
        return !req.getParameter(BRAND).isEmpty() || !req.getParameter(NAME).isEmpty() || !req.getParameter(PRICE).isEmpty()
                || !req.getParameter(QUALITY).isEmpty();
    }
}
