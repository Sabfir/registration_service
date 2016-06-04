package service;

import com.registration.service.JmsService;
import configuration.SpringTestConfiguration;
import com.registration.core.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
