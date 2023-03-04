package com.example.rentingapp.web.command.base;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.exception.UserNotExistsException;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.utils.EmailSender;
import com.example.rentingapp.utils.HashingPassword;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import com.example.rentingapp.web.listeners.EmailContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.EMAIL;
import static com.example.rentingapp.dao.DAOImpl.constants.Fields.LOGIN;
import static com.example.rentingapp.web.command.constants.Commands.RESTORE;
import static com.example.rentingapp.web.command.constants.EmailConstants.MESSAGE_CONFIRM;
import static com.example.rentingapp.web.command.constants.EmailConstants.TOPIC_CHANGE_PASS;

public class RestorePassCommand implements Command {
    private final EmailSender emailSender;
    private static final Logger LOG = Logger.getLogger(RestorePassCommand.class);
    private static final String CHECK_EMAIL = "warn.check.email";

    public RestorePassCommand(EmailContext emailContext) {
        emailSender = emailContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        return CommandType.GET == commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) throws ServiceException {
        String username = req.getParameter(LOGIN);
        String email = req.getParameter(EMAIL);

        UserService userService = ServiceFactory.getUserService();
        User user;
        String path = Path.LOGIN_PAGE;
        try {
            user = userService.getByLogin(username);
            if (!user.getEmail().equals(email)) {
                throw new UserNotExistsException();
            }
            String newPass = userService.getNewPass();
            sendConfirmation(user, newPass);
            newPass = HashingPassword.hash(newPass);
            userService.changePass(username, newPass);
            user.setPassword(newPass);
            req.getSession().setAttribute(Model.MESSAGE, CHECK_EMAIL);

        } catch (UserNotExistsException e) {
            LOG.error("Error in executing command");
            req.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            path = Path.RESTORE_PAGE;
        }

        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return CommandUtil.redirectCommand(RESTORE);
    }

    private void sendConfirmation(User user, String newPass) {
        String body = String.format(MESSAGE_CONFIRM, user.getFirstName(), newPass);
        new Thread(() -> emailSender.send(user.getEmail(), TOPIC_CHANGE_PASS, body)).start();
    }


    private String doGet(HttpServletRequest request) {
        CommandUtil.setAttrToReq(request, Model.MESSAGE);
        return CommandUtil.getPath(request);
    }
}
