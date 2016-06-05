package com.registration.service;

import com.google.gson.Gson;
import com.registration.dao.UserDao;
import com.registration.core.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.File;

/**
 * The JmsServiceImpl class implements java JmsService.
 * You can receive and send message using it
 *
 * @author  Alex Pinta, Oleh Pinta
 */
@Service
@Configurable
@Transactional
public class JmsServiceImpl implements JmsService {
    final static Logger logger = Logger.getLogger(JmsServiceImpl.class);
    final String DESTINATION_QUEUE = "email-confirmation";
    final Gson gson = new Gson();

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDao userDao;

    public JmsServiceImpl() {
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }

    /**
     * This method is used to receive email confirmation.
     * It commits session in case of success
     */
    @Override
    @JmsListener(destination = DESTINATION_QUEUE)
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED,
                    rollbackFor = DataAccessException.class)
    public void receiveMessage(String message, Session session) {
        User user = gson.fromJson(message, User.class);
        try {
            if (emailService.isServiceAccessible()) {
                userDao.createUser(user.getEmail(), user.getPassword());
                if (emailService.sendConfirmEmail(user)) {
                    session.commit();
                    logger.info("Email confirmed successfully");
                } else {
                    session.rollback();
                    logger.info("Email not confirmed yet");
                }
            }
        } catch (DataAccessException e) {
            logger.error("Data access exception while sending confirm email", e);
        } catch (JMSException e) {
            logger.error("Can\'t rollback session while sending confirm email", e);
        }
    }

    /**
     * This method is used to send mail.
     * It gets user as an attribute, prepare and send message through mail
     */
    @Override
    public void sendMessage(final User user) {
        this.jmsTemplate.send(DESTINATION_QUEUE, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(gson.toJson(user));
            }
        });
    }

    @Override
   public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
