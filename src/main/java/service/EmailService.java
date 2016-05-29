package service;

import core.User;

/**
 * Created by alex on 29.05.16.
 */
public interface EmailService {
    void prepareService();
    void sendConfirmEmail(User user);
}
