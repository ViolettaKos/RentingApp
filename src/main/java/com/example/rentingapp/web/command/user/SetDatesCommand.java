package com.example.rentingapp.web.command.user;

import com.example.rentingapp.dao.DAOImpl.OrderDAOImpl;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.CAR_ID;


public class SetDatesCommand implements Command {
    private static final String DATES = "dates";


    /**
     * This method is used to execute the order page command. It gets the dates which are not available for booking for a
     * specific car and sets them as an attribute of the request.
     *
     * @param req         The HTTP request from the client.
     * @param resp        The HTTP response from the server.
     * @param commandType The type of command to execute.
     * @return A string representing the path to the order page.
     * @throws ServiceException if there is an error executing the command.
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, CommandType commandType) throws ServiceException {
        OrderService orderService = ServiceFactory.getOrderService();
        int car_id;
        if (req.getParameter(CAR_ID) != null)
            car_id = Integer.parseInt(req.getParameter(CAR_ID));
        else {
            car_id = (int) req.getSession().getAttribute(CAR_ID);
            req.getSession().removeAttribute(CAR_ID);
        }
        List<LocalDate> dates;
        try {
            dates = orderService.getDatesByCar(car_id);
        } catch (ServiceException e) {
            return Path.ERROR_PAGE;
        }
        String[] myArr = new String[1];
        if (!dates.isEmpty()) {
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("M/d/uuuu");
            myArr = new String[dates.size()];
            for (int i = 0; i < dates.size(); i++) {
                String text = dates.get(i).format(formatters).replaceAll("/", "-");
                myArr[i] = text;
            }
        } else
            myArr[0] = "1/1/2000";
        req.setAttribute(DATES, myArr);
        req.setAttribute(CAR_ID, car_id);

        CommandUtil.setAttrToReq(req, Model.MESSAGE);
        return Path.ORDER_PAGE;
    }
}
