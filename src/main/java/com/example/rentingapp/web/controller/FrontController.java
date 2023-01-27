package com.example.rentingapp.web.controller;


import com.example.rentingapp.exception.ServiceException;
import com.example.rentingapp.web.command.CommandFactory;
import com.example.rentingapp.web.command.CommandType;
import com.example.rentingapp.web.command.constants.Path;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet("/controller")
@MultipartConfig
public class FrontController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(FrontController.class);
    private static final String COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("DOGET method in controller");
        processRequest(req, resp, CommandType.GET);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp, CommandType commandType) throws ServletException, IOException {
        LOG.debug("Start proccessing request");
        LOG.trace("Parameter command -> "+req.getParameter(COMMAND));
        String path;
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
                LOG.trace("Forwarding...");
                RequestDispatcher dispatcher=req.getRequestDispatcher(path);
                dispatcher.forward(req, resp);
            } else if (commandType==CommandType.POST) {
                LOG.trace("Redirecting to: "+path);
                resp.sendRedirect(path);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("DOPOST method in controller");
        processRequest(req, resp, CommandType.POST);
    }
}
