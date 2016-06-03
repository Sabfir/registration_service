package com.registration.service;

import com.registration.util.TemplateBuilder;
import com.sun.mail.smtp.*;
import com.registration.core.User;
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
        boolean returnedCode = false;
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        context.setVariable("password", user.getPassword());
        context.setVariable("hashCodeRegistration", UUID.randomUUID().toString());
        String content = TemplateBuilder.proccessTemplate("submitEmailContext", context);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Confirmation of registration in our site");
            message.setText(content);

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