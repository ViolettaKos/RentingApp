package com.example.rentingapp.web.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import org.apache.log4j.Logger;




public class LocaleFilter implements Filter {

    private static final Logger LOGGER=Logger.getLogger(LocaleFilter.class.getName());

    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) {
        defaultLocale=filterConfig.getInitParameter("defaultLocale");
    }



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String locale=httpRequest.getParameter("locale");
        LOGGER.trace("Locale: "+locale);
        if(locale!=null && !locale.isEmpty()) {
            LOGGER.trace( "Locale not null");
            httpRequest.getSession().setAttribute("locale", locale);
            httpResponse.sendRedirect(httpRequest.getHeader("referer"));
        } else {
            String sessionLocale = (String) httpRequest.getSession().getAttribute("locale");
            LOGGER.trace("sessionLocale: "+sessionLocale);
            if(sessionLocale==null || sessionLocale.isEmpty()){
                httpRequest.getSession().setAttribute("locale", defaultLocale);
            }
            chain.doFilter(request, response);
        }
    }
}

