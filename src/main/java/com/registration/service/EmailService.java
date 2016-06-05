package com.registration.service;

import com.registration.core.User;
import com.registration.util.StringEncryptor;
import com.sun.mail.smtp.SMTPSSLTransport;
import org.thymeleaf.TemplateEngine;

import javax.mail.NoSuchProviderException;

public interface EmailService {
    void prepareService() throws NoSuchProviderException;
    boolean sendConfirmEmail(User user);
    boolean isServiceAccessible();

    void setTransport(SMTPSSLTransport smtpTransport);

    void setTemplateEngine(TemplateEngine templateEngine);

    void setStringEncryptor(StringEncryptor stringEncryptor);
}
