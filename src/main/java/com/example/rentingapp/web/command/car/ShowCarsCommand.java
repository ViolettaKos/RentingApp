package com.example.rentingapp.web.command.car;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.web.command.constants.Model.*;

public class ShowCarsCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowCarsCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        pagination(req, sortCommand(req));
        return Path.BOOK_PAGE;
    }

    private String sortCommand(HttpServletRequest req) {
        String command;
        if(req.getParameter(SORT)!=null) {
            if(req.getParameter(SORT).equals(""))
                command ="";
            else
                command=ORDER_BY+req.getParameter(SORT)+ASC;
        } else
            command="";
        return command;

    }

    private void pagination(HttpServletRequest req, String command) throws ServiceException {
        int currentPage, recordsPerPage;
        if (req.getParameter(CURR_PAGE) == null || req.getParameter(REC_PER_PAGE) == null) {
            currentPage=1;
            recordsPerPage=6;
        } else {
            currentPage = Integer.parseInt(req.getParameter(CURR_PAGE));
            recordsPerPage = Integer.parseInt(req.getParameter(REC_PER_PAGE));
        }

        CarsService carsService= ServiceFactory.getCarsService();
        req.setAttribute(CAR, carsService.sortCars(command, currentPage, recordsPerPage));

        int rows = carsService.getNumberOfRows(command);

        int nOfPages= (int) Math.ceil((double) rows/recordsPerPage);

        req.setAttribute(NUM_OF_PAGES, nOfPages);
        req.setAttribute(CURR_PAGE, currentPage);
        req.setAttribute(REC_PER_PAGE, recordsPerPage);
    }


}
