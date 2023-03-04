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
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Path;
import com.example.rentingapp.web.listeners.EmailContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.CAR_ID;
import static com.example.rentingapp.web.command.constants.Commands.*;
import static com.example.rentingapp.web.command.constants.EmailConstants.*;
import static com.example.rentingapp.web.command.constants.Model.*;

public class RejectOrderCommand implements Command {
    private static final Logger LOG = Logger.getLogger(RejectOrderCommand.class);
    private final EmailSender emailSender;

    public RejectOrderCommand(EmailContext emailContext) {
        emailSender = emailContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        int order_id = Integer.parseInt(req.getParameter(ID));
        if (req.getParameter(COMMENT).isEmpty())
            return CommandUtil.redirectCommand(REJECT, ID, String.valueOf(order_id), CAR_ID, req.getParameter(CAR_ID));
        OrderService orderService = ServiceFactory.getOrderService();

        String comment = req.getParameter(COMMENT);
        orderService.rejectOrder(order_id, comment);

        OrderInfo orderInfo = orderService.getOrderInfo(order_id);
        UserService userService = ServiceFactory.getUserService();
        User user = userService.getByLogin(orderInfo.getLogin());
        orderInfo.setComment(comment);

        req.setAttribute(ORDER_INFO, orderService.getOrderInfo(order_id));
        req.getSession().setAttribute(ID, order_id);
        sendReject(user, comment);
        return CommandUtil.redirectCommand(DISPLAY_ORDER);
    }

    private void sendReject(User user, String reason) {
        String body = String.format(MESSAGE_REJECTED, user.getFirstName(), reason);
        new Thread(() -> emailSender.send(user.getEmail(), TOPIC_REJECTED, body)).start();
    }
}
