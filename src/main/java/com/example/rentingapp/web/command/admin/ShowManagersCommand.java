package com.example.rentingapp.web.command.admin;

import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.web.command.Command;
import com.example.rentingapp.web.command.CommandType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShowManagersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException {
        return null;
    }
}
