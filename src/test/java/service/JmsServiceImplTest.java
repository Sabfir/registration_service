package service;

import com.registration.dao.UserDao;
import com.registration.service.EmailService;
import com.registration.service.JmsService;
import configuration.SpringTestConfiguration;
import com.registration.core.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestConfiguration.class)
public class JmsServiceImplTest {
    final String EMAIL = "test@ukr.net";
    final String PASSWORD = "132321";
    @Autowired
    JmsService jmsService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserDao userDao;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        jmsService.setEmailService(emailService);
        jmsService.setUserDao(userDao);
    }
    @Test
    public void testSendMessage() throws Exception {
        User user = new User(EMAIL, PASSWORD);
        when(emailService.isServiceAccessible()).thenReturn(true);
        jmsService.sendMessage(user);
        verify(emailService,times(1)).sendConfirmEmail(any(User.class));
        verify(userDao,times(1)).createUser(anyString(), anyString());
    }

    @Test
    public void testSendMessage_DaoServiceIsNotAvailable() throws Exception {
        User user = new User(EMAIL, PASSWORD);
        when(emailService.isServiceAccessible()).thenReturn(true);
        doThrow(DataAccessException.class).when(userDao).createUser(anyString(), anyString());
        jmsService.sendMessage(user);
        verify(emailService,times(0)).sendConfirmEmail(any(User.class));
    }
}
