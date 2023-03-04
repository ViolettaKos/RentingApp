package com.example.rentingapp.web.command.user;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.model.User;
import com.example.rentingapp.service.ServiceFactory;
import com.example.rentingapp.service.UserService;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;

import static com.example.rentingapp.web.command.CommandUtil.*;
import static com.example.rentingapp.web.command.constants.Model.AMOUNT;

import com.example.rentingapp.web.command.constants.Commands;
import com.example.rentingapp.web.command.constants.Model;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class TopUpCommand implements Command {

    private static final Logger LOG = Logger.getLogger(TopUpCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        return CommandType.GET == commandType ? doGet(request) : doPost(request);
    }

    /**
     * This method processes the user's request to add money to their account
     * and updates their account balance if the result is valid.
     *
     * @param req the HTTP servlet request containing the user's input data
     * @return the redirection command to be executed after processing the request
     * @throws ServiceException if there was an error processing the request
     */
    private String doPost(HttpServletRequest req) throws ServiceException {
        if (req.getParameter(AMOUNT).isEmpty())
            req.getSession().setAttribute(Path.CURRENT_PATH, Path.ERROR_PAGE);

        int amount = Integer.parseInt(req.getParameter(AMOUNT));
        String path = Path.PROFILE_PAGE;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Model.LOGGED);
        user.setMoney(user.getMoney() + amount);
        UserService userService = ServiceFactory.getUserService();
        boolean res = userService.updateMoney(user.getUsername(), amount);

        if (!res) {
            LOG.error("Error in adding money");
            req.getSession().setAttribute(Path.CURRENT_PATH, Path.ERROR_PAGE);
        }
        req.getSession().setAttribute(Path.CURRENT_PATH, path);
        return redirectCommand(Commands.TOP_UP);
    }

    private String doGet(HttpServletRequest request) {
        return getPath(request);
    }
}
