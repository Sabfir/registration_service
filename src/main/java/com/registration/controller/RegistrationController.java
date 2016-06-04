package com.registration.controller;

import com.registration.core.User;
import com.registration.util.TemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.registration.service.JmsService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private JmsService jmsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String registrationMain() {
        return "index";
    }

    @RequestMapping(value = "/registrationForm", method = RequestMethod.GET)
    public String registrationForm(User user) {
        return "fragments/registration";
    }


    @RequestMapping(value = "/validation", method = RequestMethod.POST)
    public String validateCredentials(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/registration";
        }

        System.out.println("User email: " + user.getEmail());
        jmsService.sendMessage(user);
        return "fragments/success";
    }

    @RequestMapping(value = "/welcomePage/{hashCodeMessage}", method = RequestMethod.GET)
    public String welcomePage(@PathVariable String hashCodeMessage) {
        return "fragments/welcome";
    }
}
