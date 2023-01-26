package com.example.project.web.command.base;

import com.example.project.web.command.Command;
import com.example.project.web.command.CommandType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class LoginCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) {
        LOG.debug("Start executing Command");
        String res=null;
        if(commandType == CommandType.POST)
            res=doPost(request, response);
        LOG.trace("Parameter res -> "+res);
        return res;
    }

    // #todo
    private String doPost(HttpServletRequest request, HttpServletResponse response) {

        return null;
    }
}
