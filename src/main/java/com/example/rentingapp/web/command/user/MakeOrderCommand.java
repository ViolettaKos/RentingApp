package com.example.rentingapp.web.command.user;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.web.command.constants.EmailConstants.*;
import static com.example.rentingapp.web.command.constants.Model.LOGGED;

import com.example.rentingapp.exception.IncorrectDataException;
import com.example.rentingapp.exception.NotAdultException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Car;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MakeOrderCommand implements Command {
    private static final Logger LOG = Logger.getLogger(MakeOrderCommand.class);
    private final Validator validator=new Validator();
    private final EmailSender emailSender;
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
        LOG.trace("From: "+from);
        LOG.trace("To: "+to);
        boolean option= Boolean.parseBoolean(req.getParameter(OPTION));
        int car_id=Integer.parseInt(req.getParameter(CAR_ID));
        User user = (User) req.getSession().getAttribute(LOGGED);
        String login=user.getUsername();

        String path=Path.MY_ORDERS;
        try {
            CarsService carsService=ServiceFactory.getCarsService();
            Car car=carsService.getCarById(String.valueOf(car_id));
            validator.checkAge(age);
            validator.checkDate(from, to);
            long days=countDays(from, to);
            int total_price= (int) (days*car.getPrice());

            Order order=new Order(login, false, false, total_price, car_id, from, to, days, option, "", false);
            OrderService orderService = ServiceFactory.getOrderService();
            orderService.putOrder(order);
            sendConfirmation(user);

        } catch (ServiceException e) {
            LOG.trace("Error in executing command");
            req.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            return CommandUtil.redirectCommand(Commands.SET_DATES, CAR_ID, String.valueOf(car_id));
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        req.getSession().removeAttribute(CAR_ID);
        return CommandUtil.redirectCommand(Commands.SHOW_MY_ORDERS);
    }

    private void sendConfirmation(User user) {
        String body = String.format(MESSAGE_ORDER, user.getFirstName());
        new Thread(() -> emailSender.send(user.getEmail(), TOPIC_ORDER, body)).start();
    }

    private String doGet(HttpServletRequest req) {
        CommandUtil.setAttrToReq(req, Model.MESSAGE);
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
