package com.example.rentingapp.web.command.user;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.web.command.constants.EmailConstants.*;
import static com.example.rentingapp.web.command.constants.Model.LOGGED;

import com.example.rentingapp.exception.IncorrectDataException;
import com.example.rentingapp.exception.NotAdultException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.utils.EmailSender;
import com.example.rentingapp.utils.Validator;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import com.example.rentingapp.web.listeners.EmailContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MakeOrderCommand implements Command {
    private static final Logger LOG = Logger.getLogger(MakeOrderCommand.class);
    private final Validator validator=new Validator();
    private final EmailSender emailSender;
    private static final String NOT_AVAILABLE="not.available";
    public MakeOrderCommand(EmailContext emailContext) {
        emailSender=emailContext.getEmailSender();
    }


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
            long days=countDays(from, to);
            int total_price= (int) (days*price);
            Order order=new Order(login, false, false, total_price, car_id, from, to, days, option, "");
            OrderService orderService = ServiceFactory.getOrderService();
            CarsService carsService=ServiceFactory.getCarsService();

            if(carsService.getCarById(String.valueOf(car_id)).isAvailable()) {
                // #todo fix all this, think about when car will be available
            orderService.putOrder(order);
            sendConfirmation(user);
            carsService.updateAvailability(car_id, false); }
            else {
                LOG.trace("Car is not available");
                req.getSession().setAttribute(Model.MESSAGE, NOT_AVAILABLE);
                path= Path.ORDER_PAGE;
                req.getSession().setAttribute(Path.CURRENT_PATH, path);
                return path;
            }

        } catch (NotAdultException | IncorrectDataException e) {
            LOG.trace("Error in executing command");
            req.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            path= Path.ORDER_PAGE;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return CommandUtil.redirectCommand(Commands.SHOW_MY_ORDERS);
    }

    private void sendConfirmation(User user) {
        String body = String.format(MESSAGE_ORDER, user.getFirstName());
        new Thread(() -> emailSender.send(user.getEmail(), TOPIC_ORDER, body)).start();
    }

    private String doGet(HttpServletRequest req) {
        CommandUtil.transferStringFromSessionToRequest(req, Model.MESSAGE);
        LOG.trace("Path: "+CommandUtil.getPath(req));
        return CommandUtil.getPath(req);
    }

    public long countDays(String from, String to) throws IncorrectDataException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        long days;
        if(from.equals(to)) {
            return 1;
        } else {
            try {
                Date fromDate=sdf.parse(from);
                Date toDate=sdf.parse(to);
                days=toDate.getTime()-fromDate.getTime();
            } catch (ParseException e) {
                LOG.trace("Exception while parsing dates");
                throw new IncorrectDataException();
            }
            return days/1000/60/60/24; }
    }
}
