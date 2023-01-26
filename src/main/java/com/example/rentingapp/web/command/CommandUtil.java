package com.example.rentingapp.web.command;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.util.StringJoiner;

import static com.example.rentingapp.web.command.constants.Commands.*;
import static com.example.rentingapp.web.command.constants.Path.*;

public class CommandUtil {

    private static final Logger LOG = Logger.getLogger(CommandUtil.class);

    public static String redirectCommand(String command, String... parameters) {
        String base = CONTROLLER_PAGE + "?" + COMMAND + "=" + command;
        LOG.trace("Base: "+base);
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        LOG.trace("stringJoiner: "+ stringJoiner);
        stringJoiner.setEmptyValue("");
        LOG.trace("stringJoiner: "+ stringJoiner);
        for (int i = 0; i < parameters.length; i+=2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + stringJoiner;
    }

    public static void transferStringFromSessionToRequest(HttpServletRequest request, String attributeName) {
        LOG.trace("attributeName: "+attributeName);
        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        LOG.trace("attributeValue: "+attributeValue);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    public static String getPath(HttpServletRequest request){
        return (String) request.getSession().getAttribute(CURRENT_PATH);
    }

}
