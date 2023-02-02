package com.example.rentingapp.web.command.user;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.constants.Model.*;

public class ShowMyOrdersCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ShowMyOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        OrderService orderService= ServiceFactory.getOrderService();
        User user= (User) req.getSession().getAttribute(LOGGED);
        req.setAttribute(ORDERS, orderService.getOrdersByLogin(user.getUsername()));
        if (req.getSession().getAttribute(COMMENT)!=null)
            req.setAttribute(COMMENT, req.getSession().getAttribute(COMMENT));
        req.getSession().removeAttribute(COMMENT);
        return Path.MY_ORDERS;
    }


}
