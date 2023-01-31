package com.example.rentingapp.web.command;


import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.util.StringJoiner;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.dao.DAOImpl.constants.Fields.REC_PER_PAGE;
import static com.example.rentingapp.web.command.constants.Commands.*;
import static com.example.rentingapp.web.command.constants.Model.*;
import static com.example.rentingapp.web.command.constants.Path.*;

public class CommandUtil {

    private static final Logger LOG = Logger.getLogger(CommandUtil.class);

    public static String redirectCommand(String command, String... parameters) {
        String base = CONTROLLER_PAGE + "?" + COMMAND + "=" + command;
        LOG.trace("Base: "+base);
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        LOG.trace("stringJoiner: "+ stringJoiner);
        stringJoiner.setEmptyValue("");
        LOG.trace("stringJoiner: "+ stringJoiner);
        for (int i = 0; i < parameters.length; i+=2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + stringJoiner;
    }

    public static void setAttrToReq(HttpServletRequest request, String attributeName) {
        LOG.trace("attributeName: "+attributeName);
        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        LOG.trace("attributeValue: "+attributeValue);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    public static String getPath(HttpServletRequest request){
        return (String) request.getSession().getAttribute(CURRENT_PATH);
    }

    public static String sortCommand(HttpServletRequest req) {
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

    public static void pagination(HttpServletRequest req, String command, String entity) throws ServiceException {
        int currentPage, recordsPerPage;
        if (req.getParameter(CURR_PAGE) == null || req.getParameter(REC_PER_PAGE) == null) {
            currentPage=1;
            recordsPerPage=6;
        } else {
            currentPage = Integer.parseInt(req.getParameter(CURR_PAGE));
            recordsPerPage = Integer.parseInt(req.getParameter(REC_PER_PAGE));
        }
        int rows=0;
        switch (entity) {
            case CAR: {
                rows=getRowsCars(req, command, currentPage, recordsPerPage);
                break;
            }
            case USER: {
                rows=getRowsUsers(req, command, currentPage, recordsPerPage);
                break;
            }
            case MANAGER: {
                rows=getRowsManagers(req, command, currentPage, recordsPerPage);
            }
            case ORDERS: {
                rows=getRowsOrder(req, command, currentPage, recordsPerPage);
            }
        }

        int nOfPages= (int) Math.ceil((double) rows/recordsPerPage);

        req.setAttribute(NUM_OF_PAGES, nOfPages);
        req.setAttribute(CURR_PAGE, currentPage);
        req.setAttribute(REC_PER_PAGE, recordsPerPage);
    }

    private static int getRowsOrder(HttpServletRequest req, String command, int currentPage, int recordsPerPage) throws ServiceException {
        OrderService orderService=ServiceFactory.getOrderService();
        req.setAttribute(ORDERS, orderService.sortOrders(command, currentPage, recordsPerPage));
        return orderService.getNumberOfRows(command);
    }

    private static int getRowsCars(HttpServletRequest req, String command, int currentPage, int recordsPerPage) throws ServiceException {
        CarsService carsService= ServiceFactory.getCarsService();
        req.setAttribute(CAR, carsService.sortCars(command, currentPage, recordsPerPage));
        return carsService.getNumberOfRows(command);
    }

    private static int getRowsUsers(HttpServletRequest req, String command, int currentPage, int recordsPerPage) throws ServiceException {
        UserService userService = ServiceFactory.getUserService();
        command=WHERE_ROLE+USER_DB;
        if(req.getParameter(BUTTON)!=null) {
            switch (req.getParameter(BUTTON)){
                case TRUE: {
                    command=command+AND+BLOCKED+EQ+true;
                    break;
                }
                case FALSE: {
                    command=command+AND+BLOCKED+EQ+false;
                    break;
                }
            }
        }
        req.setAttribute(USER, userService.sortUsers(command, currentPage, recordsPerPage));
        return userService.getNumberOfRows(command);
    }

    private static int getRowsManagers(HttpServletRequest req, String command, int currentPage, int recordsPerPage) throws ServiceException {
        UserService userService = ServiceFactory.getUserService();
        command=WHERE_ROLE+MANAGER_DB+" "+command;
        req.setAttribute(MANAGER, userService.sortUsers(command, currentPage, recordsPerPage));
        return userService.getNumberOfRows(command);
    }

}
