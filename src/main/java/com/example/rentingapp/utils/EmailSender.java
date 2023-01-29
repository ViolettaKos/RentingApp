package com.example.rentingapp.utils;

import com.example.rentingapp.dao.AbstractDAO;
import com.example.rentingapp.dao.DAOFactory;
import com.example.rentingapp.dao.connection.ConnectionPool;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.InputStream;
import java.util.Properties;

import static com.example.rentingapp.utils.EmailConstants.*;

public class EmailSender {
    private static final Logger LOG = Logger.getLogger(EmailSender.class);
    private final Session session;
    private final String from;


    public EmailSender(Properties properties) {
        from = properties.getProperty(MAIL);
        session = getSession(from, properties);
        LOG.trace("Email sender got session");
    }


    private Session getSession(String from, Properties properties) {
        return Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, properties.getProperty(MAIL_PASS));
            }
        });
    }

    public void send(String to, String topic, String body) {
        LOG.trace("-1");
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            LOG.trace("0");
            message.setSubject(topic);
            LOG.trace("1");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            LOG.trace("2");
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            mimeBodyPart.setContent(body, "text/html; charset=utf-8");
            LOG.trace("3");
            multipart.addBodyPart(mimeBodyPart);
            LOG.trace("4");
            message.setContent(multipart);
            LOG.trace("5");
            Transport.send(message);
            LOG.trace("Successfully sending email");
        } catch (MessagingException e) {
            LOG.error(e.getMessage());
        }
    }
}
