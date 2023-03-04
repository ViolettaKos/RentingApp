package com.example.rentingapp.web.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class EmailListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(EmailListener.class);
    private static final String PROPERTIES_FILE = "app.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EmailContext.setContext(sce.getServletContext(), PROPERTIES_FILE);
        LOG.trace("EmailSender class is ready for use");
    }

}
