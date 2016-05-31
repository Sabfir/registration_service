package service;

import com.google.gson.Gson;
import core.User;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.File;

@Service
public class JmsServiceImpl implements JmsService {
    final String DESTINATION_QUEUE = "email-confirmation";
    final Gson gson = new Gson();

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private EmailService emailService;

    @Autowired
    DefaultJmsListenerContainerFactory jmsListenerContainerFactory;
    @Autowired
    ApplicationContext context;
//    @Autowired
//    private ActiveMQConnectionFactory jmsConnectionFactory;

    public JmsServiceImpl() {
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }

    @Override
    @JmsListener(destination = DESTINATION_QUEUE)
    public void receiveMessage(String message, Session session) {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(jmsConnectionFactory);
        User user = gson.fromJson(message, User.class);
        try {
            if (emailService.isServiceAccessible()) {
                if (emailService.sendConfirmEmail(user)) {
                    session.commit();
                } else {
                    session.rollback();
                }
            }
        } catch (JMSException e) {
            //TODO logging can\'t rollback
        } finally {
            try {
                session.close();
            } catch (JMSException e) {
                //e.printStackTrace();
            }
        }

    }
    @Override
    public void sendMessage(final User user) {
        this.jmsTemplate.send(DESTINATION_QUEUE, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(gson.toJson(user));
            }
        });
    }
    public void closeConnectionPool() {
//        (CachingConnectionFactory)jmsTemplate.getConnectionFactory();
    }
}
