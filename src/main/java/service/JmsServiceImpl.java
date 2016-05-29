package service;

import com.google.gson.Gson;
import core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.File;

@Service
public class JmsServiceImpl {
    final String DESTINATION_QUEUE = "email-confirmation";
    final Gson gson = new Gson();

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    EmailService emailService;

    public JmsServiceImpl() {
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }

    @JmsListener(destination = DESTINATION_QUEUE, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String message) {
        User user = gson.fromJson(message, User.class);
        emailService.sendConfirmEmail(user);
    }

    public void sendMessage(final User user) {
        this.jmsTemplate.send(DESTINATION_QUEUE, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(gson.toJson(user));
            }
        });
    }
}
