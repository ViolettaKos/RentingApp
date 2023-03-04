package com.example.rentingapp.web.command.base;

import com.example.rentingapp.dao.DAOImpl.constants.Fields;
import com.example.rentingapp.exception.*;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.CommandUtil;
import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.EMAIL;
import static com.example.rentingapp.dao.DAOImpl.constants.Fields.TELEPHONE;
import static com.example.rentingapp.web.command.constants.Model.*;
import static com.example.rentingapp.web.command.constants.Model.PASS;

public class EditCommand implements Command {
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
        String path = Path.PROFILE_PAGE;
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(Model.LOGGED);
            UserService userService = ServiceFactory.getUserService();
            if (!username.equals(user.getUsername())) {
                userService.checkIfExists(username);
            }
            user.setId(user.getId());
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setPassword(pass);
            user.setUsername(username);
            user.setEmail(email);
            user.setTelephone(telephone);

            userService.update(user);

            req.getSession().setAttribute(Model.LOGGED, user);
            req.getSession().setAttribute(Fields.ROLE, user.getRole());

        } catch (IncorrectDataException | IncorrectEmailException | DuplicatedLoginException e) {
            LOG.error("Error in executing command");
            req.getSession().setAttribute(Model.MESSAGE, e.getMessage());
            path = Path.EDIT_PAGE;
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return CommandUtil.redirectCommand(Commands.EDIT);
    }


    private String doGet(HttpServletRequest request) {
        CommandUtil.setAttrToReq(request, Model.MESSAGE);
        return CommandUtil.getPath(request);
    }
}
