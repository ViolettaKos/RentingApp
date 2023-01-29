package com.example.rentingapp.web.filters;

import com.example.rentingapp.web.filters.Access.Access;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.io.IOException;

import static com.example.rentingapp.web.command.constants.Commands.COMMAND;
import static com.example.rentingapp.web.command.constants.Model.LOGGED;
import static com.example.rentingapp.web.command.constants.Model.MESSAGE;
import static com.example.rentingapp.web.command.constants.Path.LOGIN_PAGE;

public class AuthenticationFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class);
    private static final String NO_ACCESS="no.access";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String servletPath = req.getServletPath();
        String command=req.getParameter(COMMAND);
        LOG.trace("servletPath: " + servletPath);
        if (req.getSession().getAttribute(LOGGED) == null && isNoAccess(servletPath, command)) {
            LOG.trace("no access for guest");
            req.setAttribute(MESSAGE, NO_ACCESS);
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
    private boolean isNoAccess(String servletPath, String command) {
        return Access.getAccess(servletPath, command).checkAccess();
    }
}
