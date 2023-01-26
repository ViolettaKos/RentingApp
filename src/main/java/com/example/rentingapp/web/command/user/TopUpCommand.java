package com.example.rentingapp.web.command.base;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        String login=req.getParameter("login");
        String path = Path.PROFILE_PAGE;
        UserService userService = ServiceFactory.getUserService();
        boolean res = userService.updateMoney(login, amount);
        LOG.trace("Is money added? "+res);
        if(res) {
            req.getSession().setAttribute(Path.CURRENT_PATH, path);
        } else {
            LOG.trace("Error in adding money");
            path = Path.ERROR_PAGE;
        }
        return CommandUtil.redirectCommand(Commands.TOP_UP);
    }

    private String doGet(HttpServletRequest request) throws ServiceException {
        LOG.trace("Path: " + CommandUtil.getPath(request));
        return CommandUtil.getPath(request);
    }
}
