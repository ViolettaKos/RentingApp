package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.utils.EmailSender;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.listeners.EmailContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.constants.Commands.*;
import static com.example.rentingapp.web.command.constants.EmailConstants.*;

public class UpdateUserStatusCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateUserStatusCommand.class);
    private final EmailSender emailSender;

    public UpdateUserStatusCommand(EmailContext emailContext) {
        emailSender = emailContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response, CommandType commandType) throws ServiceException {
        String login = req.getParameter(LOGIN);
        doAction(req, login);
        return CommandUtil.redirectCommand(SHOW_ADMIN_USERS);
    }

    private void doAction(HttpServletRequest req, String login) throws ServiceException {
        UserService userService = ServiceFactory.getUserService();
        User user = userService.getByLogin(login);
        boolean action = Boolean.parseBoolean(req.getParameter(ACTION));
        LOG.trace("To block? " + action);
        sendInfoAccount(user, action);
        userService.updateStatus(login, action);
        user.setBlocked(action);
    }

    private void sendInfoAccount(User user, boolean action) {
        if (action) {
            String body = String.format(MESSAGE_BLOCKED, user.getFirstName());
            new Thread(() -> emailSender.send(user.getEmail(), TOPIC_BLOCKED, body)).start();
        } else {
            String body = String.format(MESSAGE_UNBLOCKED, user.getFirstName());
            new Thread(() -> emailSender.send(user.getEmail(), TOPIC_UNBLOCKED, body)).start();
        }
    }

}
