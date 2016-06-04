package com.registration.controller;

import com.registration.core.User;
import com.registration.util.TemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.registration.service.JmsService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {
    @Autowired
    private JmsService jmsService;

    @Autowired
    private TemplateEngine templateEngine;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String registrationMain() {
          return "registration";
    }

    @RequestMapping(value = "/registrationForm", method = RequestMethod.GET)
    public String registrationForm(Model model) {
        return "fragments/fragment :: registration";
    }

    @RequestMapping(value = "/validation", method = RequestMethod.POST)
    @ResponseBody
    public String validateCredentials(HttpServletRequest request) {
        jmsService.sendMessage(new User(request.getParameter("email"), request.getParameter("password")));
        return TemplateBuilder.processTemplate("fragments/success", new Context());
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "fragments/success :: success";
    }

    @RequestMapping(value = "/welcomePage/{hashCodeMessage}", method = RequestMethod.GET)
    public String welcomePage(@PathVariable String hashCodeMessage) {
        return "fragments/fragment :: welcomepage";
    }
}
