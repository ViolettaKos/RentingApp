package com.example.rentingapp.web.listeners;

import com.example.rentingapp.utils.EmailSender;
import jakarta.servlet.ServletContext;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class EmailContext {
    private static final Logger LOG = Logger.getLogger(EmailContext.class);
    private final EmailSender emailSender;
    private static EmailContext emailContext;

    private EmailContext(ServletContext sc, String file) {
        Properties properties = loadProperties(file);
        emailSender = new EmailSender(properties);
    }

    private static Properties loadProperties(String file) {
        Properties properties = new Properties();
        try (InputStream resource = EmailContext.class.getClassLoader().getResourceAsStream(file)) {
            properties.load(resource);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        return properties;
    }

    public static void setContext(ServletContext sc, String file) {
        emailContext = new EmailContext(sc, file);
    }

    public static EmailContext getEmailContext() {
        return emailContext;
    }

    public EmailSender getEmailSender() {
        return emailSender;
    }
}
