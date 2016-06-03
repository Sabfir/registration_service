package com.registration.service;

import com.registration.util.TemplateBuilder;
import com.sun.mail.smtp.*;
import com.registration.core.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@Service
@Configurable
@ConfigurationProperties(prefix="service.email", locations = "classpath:application.yml")
public class EmailServiceImpl implements EmailService {
    private String emailAddress;
    private String password;
    private String smtpHost;
    private String smtpPort;
    private Session session;
    private boolean serviceStatus = true;
    private SMTPSSLTransport smtpTransport;

    private EmailServiceImpl() {}

    //problem with setting fields by spring boot during processing constructor class
    @Override
    @PostConstruct
    public void prepareService() {
        Properties props = System.getProperties();
        props.put("mail.smtps.host", smtpHost);
        props.put("mail.smtps.port", smtpPort);
        props.put("mail.smtps.auth", true);
        props.put("mail.smtps.user", emailAddress);
        session = Session.getDefaultInstance(props,null);

        try {
            smtpTransport = (SMTPSSLTransport)session.getTransport("smtps");
            smtpTransport.connect(smtpHost, emailAddress, password);
        } catch (MessagingException e) {
            serviceStatus = false;
            //TODO logging inner problem with an access to provider
        }
        smtpTransport.setReportSuccess(true);
    }

    @Override
    public boolean sendConfirmEmail(User user) {
        final int COUNT_RENDERED_SYMBOLS = 2;
        final String SPECIAL_SYMBOLS = "*";
        final int COUNT_SPECIAL_SYMBOL = 8;
        boolean returnedCode = false;
        String lastPasswordSymbol = StringUtils.repeat(SPECIAL_SYMBOLS, COUNT_SPECIAL_SYMBOL) +
                user.getPassword().substring(user.getPassword().length()-COUNT_RENDERED_SYMBOLS);
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        context.setVariable("passwordLastSymbols",  lastPasswordSymbol);
        context.setVariable("hashCodeRegistration", UUID.randomUUID().toString());
        String content = TemplateBuilder.processTemplate("submitEmailContext", context);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setHeader("Content-Type", "text/html; charset=ISO-8859-1\r\n");
            message.setSubject("Confirmation of registration in our site");
            message.setContent(content, "text/html; charset=utf-8");

            message.saveChanges();
            smtpTransport.sendMessage(message, message.getAllRecipients());
            smtpTransport.close();
        } catch(SMTPSendFailedException e) {
            if (e.getReturnCode() == 250) {
                returnedCode = true;
                //TODO logging

            } else {
                //TODO logging
            }
        } catch (Exception e) {
            //TODO logging
        }
        return returnedCode;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String servicePassword) {
        this.password = servicePassword;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    @Override
    public boolean isServiceAccessible() {
        return serviceStatus;
    }
}