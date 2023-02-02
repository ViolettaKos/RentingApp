package com.example.rentingapp.web.command.user;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.OrderInfo;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.constants.Commands.SHOW_MY_ORDERS;
import static com.example.rentingapp.web.command.constants.Model.COMMENT;
import static com.example.rentingapp.web.command.constants.Model.ID;

public class GetReasonCommand implements Command {
    private static final Logger LOG = Logger.getLogger(GetReasonCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.trace("Start executing command");
        int order_id= Integer.parseInt(req.getParameter(ID));
        LOG.trace("order id: "+order_id);
        OrderService orderService= ServiceFactory.getOrderService();
        OrderInfo orderInfo=orderService.getOrderInfo(order_id);
        req.getSession().setAttribute(COMMENT, orderInfo.getComment());
        return CommandUtil.redirectCommand(SHOW_MY_ORDERS);
    }
}
