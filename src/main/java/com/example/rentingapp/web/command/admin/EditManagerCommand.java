package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.DuplicatedLoginException;
import com.example.rentingapp.exception.IncorrectDataException;
import com.example.rentingapp.exception.IncorrectEmailException;
import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.base.EditCommand;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.*;
import static com.example.rentingapp.web.command.constants.Commands.DISPLAY_INFO_MNG;
import static com.example.rentingapp.web.command.constants.Commands.SHOW_ADMIN_MANAGERS;
import static com.example.rentingapp.web.command.constants.Model.*;
import static com.example.rentingapp.web.command.constants.Model.LAST_NAME;
import static com.example.rentingapp.web.command.constants.Model.PASS;

public class EditManagerCommand implements Command {
    private static final Logger LOG = Logger.getLogger(EditCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        return CommandType.GET == commandType ? doGet(request) : doPost(request);
    }

    private String doPost(HttpServletRequest req) throws ServiceException {
        String firstname = req.getParameter(FIRSTNAME);
        String lastname = req.getParameter(LAST_NAME);
        String username = req.getParameter(USERNAME);
        String pass = req.getParameter(PASS);
        String email = req.getParameter(EMAIL);
        String telephone = req.getParameter(TELEPHONE);
        String path = Path.ADMIN_MNG_PAGE;
        String command = SHOW_ADMIN_MANAGERS;
        try {
            UserService userService = ServiceFactory.getUserService();
            User user = userService.getByLogin(req.getParameter(LOGIN));
            if (!username.equals(user.getUsername())) {
                userService.checkIfExists(username);
            }
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setPassword(pass);
            user.setUsername(username);
            user.setEmail(email);
            user.setTelephone(telephone);

            userService.update(user);

        } catch (IncorrectDataException | IncorrectEmailException | DuplicatedLoginException e) {
            LOG.error("Error in executing command");
            req.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            path = Path.EDIT_MANAGER_PAGE;
            command = DISPLAY_INFO_MNG;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return CommandUtil.redirectCommand(command);
    }

    private String doGet(HttpServletRequest request) {
        CommandUtil.setAttrToReq(request, Model.MESSAGE);
        return CommandUtil.getPath(request);
    }
}
