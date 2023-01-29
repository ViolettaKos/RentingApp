package com.example.rentingapp.web.command.user;

import com.example.rentingapp.dao.DAOImpl.constants.Fields;
import com.example.rentingapp.exception.*;
import com.example.rentingapp.model.Role;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.utils.EmailSender;
import com.example.rentingapp.utils.Validator;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import com.example.rentingapp.web.listeners.EmailContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.web.command.constants.Commands.COMMAND;
import static com.example.rentingapp.web.command.constants.EmailConstants.*;


public class UserRegistrCommand implements Command {
    private static final Logger LOG = Logger.getLogger(UserRegistrCommand.class);
    private final Validator validator=new Validator();
    private final EmailSender emailSender;
    public UserRegistrCommand(EmailContext emailContext) {
        emailSender=emailContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        return CommandType.GET==commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) throws ServiceException {
        String firstname = req.getParameter("firstname");
        LOG.trace("Request parameter: fn --> " + firstname);
        String lastname = req.getParameter("lastname");
        LOG.trace("Request parameter: ln --> " + lastname);
        String username = req.getParameter("username");
        LOG.trace("Request parameter: username --> " + username);
        String pass = req.getParameter("pass");
        LOG.trace("Request parameter: pass --> " + pass);
        String repeated_pass = req.getParameter("repeated_pass");
        LOG.trace("Request parameter: repeated_pass --> " + repeated_pass);
        String email = req.getParameter("email");
        LOG.trace("Request parameter: email --> " + email);
        String telephone = req.getParameter("telephone");
        String path=Path.PROFILE_PAGE;
        User user=new User(firstname, lastname, username, email, pass, telephone, Role.USER, false, 0);
        try {

            validator.isMatched(pass, repeated_pass);
            UserService userService = ServiceFactory.getUserService();
            userService.checkIfExists(username);
            userService.add(user);
            sendGreetings(user, req);
            req.getSession().setAttribute(Fields.ROLE, user.getRole());
            req.getSession().setAttribute(Model.LOGGED, user);

        } catch (IncorrectDataException | IncorrectEmailException | DuplicatedLoginException | PasswordNotMatchesException e) {
            LOG.trace("Error in executing command");
            req.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            path= Path.SIGN_UP_PAGE;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return CommandUtil.redirectCommand(Commands.USER_REG);
    }
    private String doGet(HttpServletRequest request) {
        CommandUtil.transferStringFromSessionToRequest(request, Model.MESSAGE);
        LOG.trace("Path: "+CommandUtil.getPath(request));
        return CommandUtil.getPath(request);
    }

    private void sendGreetings(User user, HttpServletRequest req) {
        String body = String.format(MESSAGE_REGISTER, user.getFirstName(), getURL(req));
        new Thread(() -> emailSender.send(user.getEmail(), TOPIC_REGISTER, body)).start();
    }

    private static String getURL(HttpServletRequest request) {
        return request.getRequestURL().toString()+MARK+
                COMMAND+EQUAL+Commands.SHOW_CARS;
    }

}
