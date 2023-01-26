package com.example.rentingapp.web.command.base;


import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ErrorCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ErrorCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) {
        LOG.debug("Command execution starts");
        String msg="Command do not exists!";
        request.setAttribute("msg", msg);
        return Path.ERROR_PAGE;
    }
}
