/*
package service;

import configuration.SpringTestConfiguration;
import org.junit.Test;
import static org.junit.Assert.*;
import core.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestConfiguration.class)
public class EmailServiceImplTest {

    @Autowired
    EmailService emailService;

    @Test
    public void testSendConfirmEmail() throws Exception {
        User user = new User("alex_pinta@ukr.net", "123456");
        emailService.prepareService();
        emailService.sendConfirmEmail(user);
    }
}
*/
