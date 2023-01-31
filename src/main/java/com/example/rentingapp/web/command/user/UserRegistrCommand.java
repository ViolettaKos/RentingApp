package com.example.rentingapp.web.command.user;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
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
    private final static String REPEATED_PASS="repeated_pass";
    public UserRegistrCommand(EmailContext emailContext) {
        emailSender=emailContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        LOG.debug("Start executing Command");
        return CommandType.GET==commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) throws ServiceException {
        String firstname = req.getParameter(FIRST_NAME);
        String lastname = req.getParameter(LAST_NAME);
        String username = req.getParameter(LOGIN);
        String pass = req.getParameter(PASS);
        String repeated_pass = req.getParameter(REPEATED_PASS);
        String email = req.getParameter(EMAIL);
        String telephone = req.getParameter(TELEPHONE);
        String path=Path.PROFILE_PAGE;
        User user=new User(firstname, lastname, username, email, pass, telephone, Role.USER, false, 0);
        try {

            validator.isMatched(pass, repeated_pass);
            UserService userService = ServiceFactory.getUserService();
            userService.checkIfExists(username);
            userService.add(user);
            sendGreetings(user, req);
            req.getSession().setAttribute(ROLE, user.getRole());
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
        CommandUtil.setAttrToReq(request, Model.MESSAGE);
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
