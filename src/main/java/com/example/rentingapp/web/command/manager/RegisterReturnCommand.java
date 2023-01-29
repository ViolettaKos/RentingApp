package com.example.rentingapp.web.command.manager;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.OrderInfo;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.utils.EmailSender;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import com.example.rentingapp.web.listeners.EmailContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.CAR_ID;
import static com.example.rentingapp.web.command.constants.EmailConstants.*;
import static com.example.rentingapp.web.command.constants.Model.*;
import static com.example.rentingapp.web.command.constants.Model.DAMAGE;

public class RegisterReturnCommand implements Command {
    private static final Logger LOG = Logger.getLogger(RejectOrderCommand.class);
    private final EmailSender emailSender;
    public RegisterReturnCommand(EmailContext emailContext) {
        emailSender=emailContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.trace("Is damaged? "+req.getParameter(DAMAGE));
        int order_id= Integer.parseInt(req.getParameter(ID));
        int car_id= Integer.parseInt(req.getParameter(CAR_ID));
        boolean isDamaged= Boolean.parseBoolean(req.getParameter(DAMAGE));
        CarsService carsService= ServiceFactory.getCarsService();
        OrderService orderService=ServiceFactory.getOrderService();
        carsService.updateAvailability(car_id, true);
        OrderInfo orderInfo=orderService.getOrderInfo(order_id);
        UserService userService=ServiceFactory.getUserService();
        User user=userService.getByLogin(orderInfo.getLogin());
        if(isDamaged) {
            String invoice=req.getParameter(MONEY);
            sendInvoice(user, invoice);
        } else {
            sendThanks(user);
        }
        req.setAttribute(ORDER_INFO, orderInfo);
        return Path.ORDER_INFO_PAGE;
    }

    private void sendThanks(User user) {
        String body = String.format(MESSAGE_RETURN_CAR, user.getFirstName());
        new Thread(() -> emailSender.send(user.getEmail(), TOPIC_RETURN_CAR, body)).start();
    }

    private void sendInvoice(User user, String invoice) {
        String body = String.format(MESSAGE_DAMAGE, user.getFirstName(), invoice);
        new Thread(() -> emailSender.send(user.getEmail(), TOPIC_DAMAGE, body)).start();
    }
}