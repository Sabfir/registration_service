package com.registration.service;

import com.registration.util.StringEncryptor;
import com.sun.mail.smtp.*;
import com.registration.core.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * The EmailServiceImpl class implements send email service.
 * You can prepare mail service and send mail using it
 *
 * @author  Alex Pinta, Oleh Pinta
 */
@Service
@Configurable
@ConfigurationProperties(prefix="service.email", locations = "classpath:application.yml")
public class EmailServiceImpl implements EmailService {
    final static Logger logger = Logger.getLogger(EmailServiceImpl.class);
    private String emailAddress;
    private String password;
    private String smtpHost;
    private String smtpPort;
    private Session session;
    private boolean serviceStatus = true;
    private SMTPSSLTransport transport;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    StringEncryptor stringEncryptor;

    public EmailServiceImpl() {}

    /**
     * This method is used to prepare service.
     * It gets properties, does connection and sets reportsuccess
     */
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
            transport = (SMTPSSLTransport)session.getTransport("smtps");
            transport.connect(smtpHost, emailAddress, password);
            logger.info("Accessed to provider successfully");
        } catch (MessagingException e) {
            serviceStatus = false;
            logger.error("Logging inner problem with an access to provider", e);
        }
        transport.setReportSuccess(true);
    }

    /**
     * This method is used to send email confirmation.
     * It gets user as an attribute, prepare and send confirmation request
     * It returns true in case of success
     */
    @Override
    public boolean sendConfirmEmail(User user) {
        final int COUNT_RENDERED_SYMBOLS = 2;
        final String SPECIAL_SYMBOLS = "*";
        final int COUNT_SPECIAL_SYMBOL = 8;
        final int EMAIL_RESPONSE_OK = 250;
        boolean returnedCode = false;
        String lastPasswordSymbol = StringUtils.repeat(SPECIAL_SYMBOLS, COUNT_SPECIAL_SYMBOL) +
                user.getPassword().substring(user.getPassword().length()-COUNT_RENDERED_SYMBOLS);
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        context.setVariable("passwordLastSymbols",  lastPasswordSymbol);
        context.setVariable("hashCodeRegistration", stringEncryptor.encrypt(user.getEmail()));
        String content = templateEngine.process("submitEmailContext", context);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setHeader("Content-Type", "text/html; charset=ISO-8859-1\r\n");
            message.setSubject("Confirmation of registration in our site");
            message.setContent(content, "text/html; charset=utf-8");

            message.saveChanges();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            logger.info("Confirm mail sent successfully");
        } catch(SMTPSendFailedException e) {
            if (e.getReturnCode() == EMAIL_RESPONSE_OK) {
                returnedCode = true;
            }
            logger.error("Send of confirmation failed with the exception", e);
        } catch (Exception e) {
            logger.error("Unknown error during sending confirmation email", e);
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

    @Override
    public void setTransport(SMTPSSLTransport smtpTransport) {
        this.transport = smtpTransport;
    }

    @Override
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void setStringEncryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }
}