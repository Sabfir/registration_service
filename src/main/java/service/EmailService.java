package service;

import core.User;

import javax.jms.Session;
import javax.mail.NoSuchProviderException;

public interface EmailService {
    void prepareService() throws NoSuchProviderException;
    boolean sendConfirmEmail(User user);
    boolean isServiceAccessible();
}
