package com.registration.service;

import com.registration.core.User;

import javax.jms.Session;

public interface JmsService {
    void receiveMessage(String message, Session session);

    void sendMessage(User user);
}
