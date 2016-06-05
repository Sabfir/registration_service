package com.registration.controller;

import com.registration.core.EndPoints;
import com.registration.core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.registration.service.JmsService;
import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private JmsService jmsService;

    @RequestMapping(value = {EndPoints.BASE_URL, "/"}, method = RequestMethod.GET)
    public String registrationMain() {
        return "index";
    }

    @RequestMapping(value = EndPoints.REGISTRATION_FORM, method = RequestMethod.GET)
    public String registrationForm(User user) {
        return "fragments/registration";
    }


    @RequestMapping(value = EndPoints.VALIDATION, method = RequestMethod.POST)
    public String validateCredentials(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/registration";
        }
        jmsService.sendMessage(user);
        return "fragments/success";
    }

    @RequestMapping(value = EndPoints.WELCOME_PAGE, method = RequestMethod.GET)
    public String welcomePage(@PathVariable String hashCodeMessage) {
        return "fragments/welcome";
    }
}
