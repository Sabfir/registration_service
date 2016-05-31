package service;

import configuration.SpringTestConfiguration;
import core.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestConfiguration.class)
public class JmsServiceImplTest {
    @Autowired
    JmsService jmsService;

    @Test
    public void testReceiveMessage() throws Exception {

    }

    @Test
    public void testSendMessage() throws Exception {
        User user = new User("alex_pinta@ukr.net", "123456");
        jmsService.sendMessage(user);
    }
}