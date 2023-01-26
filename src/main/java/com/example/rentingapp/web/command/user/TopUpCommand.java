package com.example.rentingapp.web.command.user;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import static com.example.rentingapp.web.command.CommandUtil.*;
import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class TopUpCommand implements Command {

    private static final Logger LOG = Logger.getLogger(TopUpCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        return CommandType.GET == commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) throws ServiceException {
        int amount= Integer.parseInt(req.getParameter("amount"));
        String path = Path.PROFILE_PAGE;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Model.LOGGED);
        user.setMoney(user.getMoney()+amount);
        UserService userService = ServiceFactory.getUserService();
        boolean res = userService.updateMoney(user.getUsername(), amount);
        LOG.trace("Is money added? "+res);
        if(!res) {
            LOG.trace("Error in adding money");
            req.getSession().setAttribute(Path.CURRENT_PATH, Path.ERROR_PAGE);
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return redirectCommand(Commands.TOP_UP);
    }

    private String doGet(HttpServletRequest request) {
        LOG.trace("Path: " + getPath(request));
        return getPath(request);
    }
}
