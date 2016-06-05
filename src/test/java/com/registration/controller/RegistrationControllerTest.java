package com.registration.controller;

import com.registration.core.EndPoints;
import com.registration.service.JmsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;
    private MockMvc mockMvc;
    private BindingResult result;
    @Mock
    private JmsService jmsService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
        result = mock(BindingResult.class);
    }

    @Test
    public void testRegistrationMain() throws Exception {
        this.mockMvc.perform(get(EndPoints.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("index"));
    }

    @Test
    public void testRegistrationForm() throws Exception {
        this.mockMvc.perform(get(EndPoints.REGISTRATION_FORM))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("fragments/registration"));
    }

    @Test
    public void testValidateCredentials_ValidData() throws Exception {
        final String VALID_EMAIL = "test@ukr.net";
        final String VALID_PASSWORD = "23sdfsf!wer";
        this.mockMvc.perform(post(EndPoints.VALIDATION)
                .param("email", VALID_EMAIL)
                .param("password", VALID_PASSWORD))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("fragments/success"));
    }

    @Test
    public void testValidateCredentials_InvalidData() throws Exception {
        final String INVALID_EMAIL = "test@";
        final String INVALID_PASSWORD = "2sdfsf!wer";
        this.mockMvc.perform(post(EndPoints.VALIDATION)
                .param("email", INVALID_EMAIL)
                .param("password", INVALID_PASSWORD))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("fragments/registration"));
    }

    @Test
    public void testWelcomePage() throws Exception {
        final String GUID = "0000-0000000-0000";
        this.mockMvc.perform(get(EndPoints.WELCOME_PAGE.replace("{hashCodeMessage}", GUID)))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("welcome"));
    }
}
