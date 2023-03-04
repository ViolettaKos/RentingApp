package com.example.rentingapp.web.command.manager;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.example.rentingapp.web.command.constants.Model.ID;
import static com.example.rentingapp.web.command.constants.Model.ORDER_INFO;

public class DisplayOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        OrderService orderService = ServiceFactory.getOrderService();
        if (req.getParameter(ID) != null)
            req.setAttribute(ORDER_INFO, orderService.getOrderInfo(Integer.parseInt(req.getParameter(ID))));
        else
            req.setAttribute(ORDER_INFO, orderService.getOrderInfo((Integer) req.getSession().getAttribute(ID)));
        req.getSession().removeAttribute(ID);
        return Path.ORDER_INFO_PAGE;
    }
}
