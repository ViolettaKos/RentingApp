package com.example.rentingapp.web.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.apache.log4j.Logger;


public class LocaleFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LocaleFilter.class.getName());

    private String defaultLocale;
    private static final String LOCALE = "locale";

    @Override
    public void init(FilterConfig filterConfig) {
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
    }


    /**
     This filter sets the locale for the user's session based on their preference in the request parameter "locale".
     If the parameter is not present or empty, it checks if there is already a locale set in the session. If not, it sets the default locale.
     It then redirects the user to the referring page with the updated locale or proceeds with the filter chain.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String locale = httpRequest.getParameter(LOCALE);

        if (locale != null && !locale.isEmpty()) {
            LOGGER.trace("Locale not null");
            httpRequest.getSession().setAttribute(LOCALE, locale);
            httpResponse.sendRedirect(httpRequest.getHeader("referer"));
        } else {
            String sessionLocale = (String) httpRequest.getSession().getAttribute(LOCALE);
            LOGGER.trace("sessionLocale: " + sessionLocale);
            if (sessionLocale == null || sessionLocale.isEmpty()) {
                httpRequest.getSession().setAttribute(LOCALE, defaultLocale);
            }
            chain.doFilter(request, response);
        }
    }
}

