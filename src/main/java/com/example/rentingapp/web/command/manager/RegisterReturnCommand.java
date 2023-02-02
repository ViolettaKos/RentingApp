package com.example.rentingapp.web.command.manager;

import com.example.rentingapp.exception.ExcConstants;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.Order;
import com.example.rentingapp.model.OrderInfo;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.CarsService;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.utils.EmailSender;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import com.example.rentingapp.web.listeners.EmailContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.CAR_ID;
import static com.example.rentingapp.dao.DAOImpl.constants.Fields.ORDER_ID;
import static com.example.rentingapp.web.command.constants.Commands.DISPLAY_ORDER;
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
    public String execute(HttpServletRequest req, HttpServletResponse resp, CommandType commandType) throws ServiceException {
        int order_id= Integer.parseInt(req.getParameter(ORDER_ID));
        boolean isDamaged= Boolean.parseBoolean(req.getParameter(DAMAGE));
        LOG.trace("Is damaged? "+isDamaged);

        OrderService orderService=ServiceFactory.getOrderService();
        OrderInfo orderInfo=orderService.getOrderInfo(order_id);
        UserService userService=ServiceFactory.getUserService();
        User user=userService.getByLogin(orderInfo.getLogin());

        if(isDamaged && req.getParameter(INVOICE)!=null) {
            String invoice=req.getParameter(INVOICE);
            sendInvoice(user, invoice);
        } else if(!isDamaged) {
            sendThanks(user);
        }

        Order order=orderService.getOrderById(order_id);

        orderService.updateReturn(order_id);
        order.setReturned(true);
        orderInfo.setReturned(true);

        req.setAttribute(ORDER_INFO, orderInfo);
        req.getSession().setAttribute(ID, order_id);
        return CommandUtil.redirectCommand(DISPLAY_ORDER);
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
