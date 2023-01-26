package com.example.rentingapp.web.command.user;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.web.command.constants.Model.LOGGED;

import com.example.rentingapp.exception.IncorrectDataException;
import com.example.rentingapp.exception.NotAdultException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.utils.Validator;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class MakeOrderCommand implements Command {
    private static final Logger LOG = Logger.getLogger(MakeOrderCommand.class);
    private final Validator validator=new Validator();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        return CommandType.GET==commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) throws ServiceException {
        String age=req.getParameter(AGE);
        String from=req.getParameter(FROM);
        String to=req.getParameter(TO);
        boolean option= Boolean.parseBoolean(req.getParameter(OPTION));
        int car_id= Integer.parseInt(req.getParameter(CAR_ID));
        User user = (User) req.getSession().getAttribute(LOGGED);
        String login=user.getUsername();
        int price= Integer.parseInt(req.getParameter(PRICE));

        String path=Path.MY_ORDERS;
        try {

            validator.checkAge(age);
            validator.checkDate(from, to);
            long days=validator.countDays(from, to);
            int total_price= (int) (days*price);
            Order order=new Order(login, false, false, total_price, car_id, from, to, days, option, "");
            OrderService orderService = ServiceFactory.getOrderService();
            orderService.putOrder(order);

            CarsService carsService=ServiceFactory.getCarsService();
            carsService.updateAvailability(car_id, false);

        } catch (NotAdultException | IncorrectDataException e) {
            LOG.trace("Error in executing command");
            req.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            path= Path.ORDER_PAGE;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return CommandUtil.redirectCommand(Commands.SHOW_MY_ORDERS);
    }

    private String doGet(HttpServletRequest req) {
        CommandUtil.transferStringFromSessionToRequest(req, Model.MESSAGE);
        LOG.trace("Path: "+CommandUtil.getPath(req));
        return CommandUtil.getPath(req);
    }
}
