package com.example.rentingapp.web.filters;

import com.example.rentingapp.web.filters.Access.Access;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.io.IOException;

import static com.example.rentingapp.dao.DAOImpl.constants.Fields.ROLE;
import static com.example.rentingapp.web.command.constants.Commands.COMMAND;
import static com.example.rentingapp.web.command.constants.Model.LOGGED;
import static com.example.rentingapp.web.command.constants.Model.MESSAGE;
import static com.example.rentingapp.web.command.constants.Path.LOGIN_PAGE;

public class AuthorizationFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AuthorizationFilter.class);
    private static final String NO_ACCESS="no.access";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String servletPath = req.getServletPath();
        String command=req.getParameter(COMMAND);
        String role= (String) req.getSession().getAttribute(ROLE);
        LOG.trace("Role in filter: "+role);
        LOG.trace("servletPath: " + servletPath);
        if (role != null && isNoAccess(servletPath, command, role)) {
            LOG.trace("no access for user");
            req.getSession().removeAttribute(ROLE);
            req.getSession().removeAttribute(LOGGED);
            req.setAttribute(MESSAGE, NO_ACCESS);
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isNoAccess(String servletPath, String command, String role) {
        return Access.getAccess(servletPath, command, role).checkAccess();
    }
}
