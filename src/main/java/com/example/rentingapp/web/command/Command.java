package com.example.project.web.command;

import com.example.project.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {

    String execute(HttpServletRequest request, HttpServletResponse response, CommandType commandType) throws ServiceException;
}
