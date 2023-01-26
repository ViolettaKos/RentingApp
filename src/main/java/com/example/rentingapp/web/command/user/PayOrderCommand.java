package com.example.rentingapp.web.command.user;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.OrderService;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.utils.Validator;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.web.command.constants.Model.LOGGED;

public class PayOrderCommand implements Command {

    private final Validator validator=new Validator();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        int order_id= Integer.parseInt(req.getParameter(ORDER_ID));
        int amount= Integer.parseInt(req.getParameter(TOTAL_PRICE));
        User user = (User) req.getSession().getAttribute(LOGGED);
        int user_money=user.getMoney();
        if(validator.isEnoughMoney(amount, user_money)) {
            OrderService orderService= ServiceFactory.getOrderService();
            orderService.updatePayment(order_id);
            user.setMoney(user.getMoney()-amount);
            UserService userService=ServiceFactory.getUserService();
            userService.updateMoney(user.getUsername(), amount*(-1));
            return Path.SUCCESS_PAGE;
        } else
            return Path.FAILURE_PAGE;
    }
}
