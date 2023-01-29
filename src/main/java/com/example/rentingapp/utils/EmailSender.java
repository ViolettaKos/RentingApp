package com.example.rentingapp.utils;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

import static com.example.rentingapp.utils.EmailConstants.MAIL;
import static com.example.rentingapp.utils.EmailConstants.MAIL_PASS;

public class EmailSender {
    private static final Logger LOG = Logger.getLogger(EmailSender.class);
    private final Session session;
    private final String from;


    public EmailSender(Properties properties) {
        from = properties.getProperty(MAIL);
        session = getSession(from, properties);
    }


    private Session getSession(String from, Properties properties) {
        return Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, properties.getProperty(MAIL_PASS));
            }
        });
    }

    public void send(String to, String topic, String body) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.setSubject(topic);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            mimeBodyPart.setContent(body, "text/html; charset=utf-8");
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            LOG.error(e.getMessage());
        }
    }
}
