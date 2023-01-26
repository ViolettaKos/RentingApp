package com.example.project.web.controller;

import com.example.project.exception.ServiceException;
import com.example.project.web.command.CommandFactory;
import com.example.project.web.command.CommandType;
import com.example.project.web.command.constants.Path;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet("/controller")
public class FrontController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(FrontController.class);
    private static final String COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp, CommandType.GET);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp, CommandType commandType) throws ServletException, IOException {
        LOG.debug("Start proccessing request");
        LOG.trace("Parameter command -> "+req.getParameter(COMMAND));
        String path= null;
        try {
            path = CommandFactory.defineCommand(req.getParameter(COMMAND)).execute(req, resp, commandType);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        LOG.trace("Parameter path -> "+path);
        if(path==null) {
            resp.sendRedirect(Path.MAIN_PAGE);
        } else {
            if(commandType==CommandType.GET) {
                RequestDispatcher dispatcher=req.getRequestDispatcher(path);
                dispatcher.forward(req, resp);
            } else if (commandType==CommandType.POST) {
                resp.sendRedirect(path);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp, CommandType.POST);
    }
}
