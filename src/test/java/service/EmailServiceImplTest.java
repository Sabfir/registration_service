package service;

import com.registration.service.EmailService;
import com.sun.mail.smtp.SMTPSSLTransport;
import com.sun.mail.smtp.SMTPSendFailedException;
import configuration.SpringTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import com.registration.core.User;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Address;
import javax.mail.Message;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestConfiguration.class)
public class EmailServiceImplTest {
    final String EMAIL = "test@ukr.net";

    @InjectMocks
    EmailService emailService;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private SMTPSSLTransport transport;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        emailService.prepareService();
    }

    @Test
    public void testSendConfirmEmail() throws Exception {
        User user = new User(EMAIL, "123456");
        emailService.sendConfirmEmail(user);

        verify(templateEngine,times(1)).process(anyString(), any(Context.class));
        verify(transport,times(1)).sendMessage(any(Message.class), new Address[]{});
    }
}
