package com.registration.service;

import com.registration.core.User;
import com.registration.dao.UserDao;

import javax.jms.Session;

public interface JmsService {
    void receiveMessage(String message, Session session);

    void sendMessage(User user);

    void setEmailService(EmailService emailService);

    void setUserDao(UserDao userDao);
}
