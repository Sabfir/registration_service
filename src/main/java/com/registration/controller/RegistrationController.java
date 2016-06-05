package com.registration.controller;

import com.registration.core.EndPoints;
import com.registration.core.User;
import com.registration.dao.UserDao;
import com.registration.util.StringEncryptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.registration.service.JmsService;
import javax.validation.Valid;

/**
 * The RegistrationController class implements managing requests and creating response.
 * You can invoke different REST functionality by using different url
 *
 * @author  Alex Pinta, Oleh Pinta
 */
@Controller
public class RegistrationController {
    final static Logger logger = Logger.getLogger(RegistrationController.class);
    @Autowired
    private JmsService jmsService;
    @Autowired
    private UserDao userDao;
    @Autowired
    StringEncryptor stringEncryptor;

    /**
     * This method is used to open main page.
     */
    @RequestMapping(value = {EndPoints.BASE_URL, "/"}, method = RequestMethod.GET)
    public String registrationMain() {
        return "index";
    }

    /**
     * This method is used to open registration page with the credential fields.
     * It binds model with the view
     */
    @RequestMapping(value = EndPoints.REGISTRATION_FORM, method = RequestMethod.GET)
    public String registrationForm(User user) {
        return "fragments/registration";
    }

    /**
     * This method is used to validate given credentials.
     * It returns registration page in case of errors found
     * It returns success page in case of valid credentials
     */
    @RequestMapping(value = EndPoints.VALIDATION, method = RequestMethod.POST)
    public String validateCredentials(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("Errors in password or email verification");
            return "fragments/registration";
        }
        logger.info("Password and email validated successfully");
        jmsService.sendMessage(user);
        return "fragments/success";
    }

    /**
     * This method is used to redirect to the welcome page with the content.
     * It invokes while user press mailed link
     */
    @RequestMapping(value = EndPoints.CONFIRM_PAGE, method = RequestMethod.GET)
    public String succesRedirection(@PathVariable String hashCodeMessage) {
        String email = stringEncryptor.decrypt(hashCodeMessage);
        if (email != null) {
            userDao.changeConfirmation(email, true);
            logger.info("User confirmed successfully " + email);
            return "redirect:"+EndPoints.WELCOME_PAGE;
        }
        return "fragments/error";
    }

    /**
     * This method is used to open welcome page with the content.
     * It should be opened after successfully confirmed user
     */
    @RequestMapping(value = EndPoints.WELCOME_PAGE, method = RequestMethod.GET)
    public String welcomePage() {
        return "welcome";
    }
}
