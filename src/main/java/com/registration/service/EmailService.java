package com.registration.service;

import com.registration.core.User;

import javax.mail.NoSuchProviderException;

public interface EmailService {
    void prepareService() throws NoSuchProviderException;
    boolean sendConfirmEmail(User user);
    boolean isServiceAccessible();
}
