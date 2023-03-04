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

/**
 The CommandUtil class contains utility methods for handling HTTP requests and responses in the Renting App web application.
 It provides methods for redirecting to a specified command with parameters, setting attributes to the request,
 getting the current path, sorting mechanism and handling pagination.
 */
public class CommandUtil {

    private static final Logger LOG = Logger.getLogger(CommandUtil.class);


    /**
     * Constructs a redirect URL to a specified command with parameters.
     *
     * @param command the command to redirect to
     * @param parameters the parameters to include in the redirect URL
     * @return the redirect URL
     */
    public static String redirectCommand(String command, String... parameters) {
        String base = CONTROLLER_PAGE + "?" + COMMAND + EQ + command;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        stringJoiner.setEmptyValue("");
        for (int i = 0; i < parameters.length; i += 2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + stringJoiner;
    }

    /**
     * Sets an attribute to the request from the session and removes it from the session.
     *
     * @param request the HTTP servlet request
     * @param attributeName the name of the attribute to set
     */
    public static void setAttrToReq(HttpServletRequest request, String attributeName) {
        LOG.trace("attributeName: " + attributeName);
        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        LOG.trace("attributeValue: " + attributeValue);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    /**
     * Gets the current path from the session.
     *
     * @param request the HTTP servlet request
     * @return the current path
     */
    public static String getPath(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(CURRENT_PATH);
    }


    /**
     * Constructs a sort command by getting sort parameter and setting order to display on page.
     *
     * @param req the HTTP servlet request
     * @return the sort command
     */
    public static String sortCommand(HttpServletRequest req) {
        String command;
        if (req.getParameter(SORT) != null) {
            if (req.getParameter(SORT).equals(""))
                command = "";
            else
                command = ORDER_BY + req.getParameter(SORT) + ASC;
        } else
            command = "";
        LOG.trace("Final command: " + command);
        return command;

    }



    /**
     * Handles pagination for a specified entity. Method gets parameters of current page user at and how many records
     * to display he has chosen. By default, start page is 1 and number of records displayed is 6. Method calculates
     * number of pages based on amount of entities to display on page and their overall amount.
     *
     * @param req the HTTP servlet request
     * @param command the command to execute
     * @param entity the entity to paginate
     * @throws ServiceException if an error occurs while getting the number of rows for the entity
     */
    public static void pagination(HttpServletRequest req, String command, String entity) throws ServiceException {
        int currentPage, recordsPerPage;
        if (req.getParameter(CURR_PAGE) == null || req.getParameter(REC_PER_PAGE) == null) {
            currentPage = 1;
            recordsPerPage = 6;
        } else {
            currentPage = Integer.parseInt(req.getParameter(CURR_PAGE));
            recordsPerPage = Integer.parseInt(req.getParameter(REC_PER_PAGE));
        }
        int rows = 0;
        switch (entity) {
            case CAR: {
                rows = getRowsCars(req, command, currentPage, recordsPerPage);
                break;
            }
            case USER: {
                rows = getRowsUsers(req, command, currentPage, recordsPerPage);
                break;
            }
            case MANAGER: {
                rows = getRowsManagers(req, command, currentPage, recordsPerPage);
                break;
            }
            case ORDERS: {
                rows = getRowsOrder(req, command, currentPage, recordsPerPage);
            }
        }

        int nOfPages = (int) Math.ceil((double) rows / recordsPerPage);

        req.setAttribute(NUM_OF_PAGES, nOfPages);
        req.setAttribute(CURR_PAGE, currentPage);
        req.setAttribute(REC_PER_PAGE, recordsPerPage);
    }

    /**
     * Gets the number of rows in the orders table and sets the sorted list of orders as a request attribute.
     *
     * @param req the HTTP servlet request
     * @param command the sorting command to execute
     * @param currentPage the current page number
     * @param recordsPerPage the number of records per page
     * @return the number of rows in the orders table
     * @throws ServiceException if an error occurs while accessing the database
     */
    private static int getRowsOrder(HttpServletRequest req, String command, int currentPage, int recordsPerPage) throws ServiceException {
        OrderService orderService = ServiceFactory.getOrderService();
        req.setAttribute(ORDERS, orderService.sortOrders(command, currentPage, recordsPerPage));
        return orderService.getNumberOfRows(command);
    }

    /**
     * Gets the number of rows in the cars table and sets the sorted list of cars as a request attribute.
     *
     * @param req the HTTP servlet request
     * @param command the sorting command to execute
     * @param currentPage the current page number
     * @param recordsPerPage the number of records per page
     * @return the number of rows in the cars table
     * @throws ServiceException if an error occurs while accessing the database
     */
    private static int getRowsCars(HttpServletRequest req, String command, int currentPage, int recordsPerPage) throws ServiceException {
        CarsService carsService = ServiceFactory.getCarsService();
        req.setAttribute(CAR, carsService.sortCars(command, currentPage, recordsPerPage));
        return carsService.getNumberOfRows(command);
    }

    /**
     * Gets sort parameter and constructs command to which users will be displayed based on user`s status(blocked or not).
     * Then gets the number of rows in the users table which satisfy created command and sets the sorted list of users as a request attribute.
     *
     * @param req the HTTP servlet request
     * @param command the sorting command to execute
     * @param currentPage the current page number
     * @param recordsPerPage the number of records per page
     * @return the number of rows in the users table
     * @throws ServiceException if an error occurs while accessing the database
     */
    private static int getRowsUsers(HttpServletRequest req, String command, int currentPage, int recordsPerPage) throws ServiceException {
        UserService userService = ServiceFactory.getUserService();
        String where = WHERE_ROLE + USER_DB;

        String filter = "";
        if (req.getParameter(BUTTON) != null) {
            switch (req.getParameter(BUTTON)) {
                case TRUE: {
                    filter = where + AND + BLOCKED + EQ + true;
                    break;
                }
                case FALSE: {
                    filter = where + AND + BLOCKED + EQ + false;
                }
            }
            command = filter + " " + command;
        } else
            command = where + command;
        LOG.trace("Command to execute: " + command);
        req.setAttribute(USER, userService.sortUsers(command, currentPage, recordsPerPage));
        return userService.getNumberOfRows(command);
    }

    /**
     * Gets the number of rows in the users table with a role of "Manager" and sets the sorted list of users as a request attribute.
     *
     * @param req the HTTP servlet request
     * @param command the sorting command to execute
     * @param currentPage the current page number
     * @param recordsPerPage the number of records per page
     * @return the number of rows in the users table with a role of "Manager"
     * @throws ServiceException if an error occurs while accessing the database
     */
    private static int getRowsManagers(HttpServletRequest req, String command, int currentPage, int recordsPerPage) throws ServiceException {
        UserService userService = ServiceFactory.getUserService();
        command = WHERE_ROLE + MANAGER_DB + " " + command;
        req.setAttribute(MANAGER, userService.sortUsers(command, currentPage, recordsPerPage));
        return userService.getNumberOfRows(command);
    }

}
