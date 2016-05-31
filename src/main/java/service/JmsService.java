package service;

import core.User;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.Session;

public interface JmsService {
    void receiveMessage(String message, Session session);

    void sendMessage(User user);
}
