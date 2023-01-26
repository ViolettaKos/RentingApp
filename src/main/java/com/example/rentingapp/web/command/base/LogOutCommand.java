package com.example.rentingapp.web.command.base;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class LogOutCommand implements Command {

    private static final Logger LOG = Logger.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        HttpSession session = request.getSession();
        if (session.getAttribute(Model.LOGGED) != null) {
            session.invalidate();
        }
        return Path.MAIN_PAGE;
    }


}
