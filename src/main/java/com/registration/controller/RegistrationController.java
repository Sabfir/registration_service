package com.registration.controller;

import com.registration.model.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.JmsService;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    JmsService jmsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String registrationMain() {
        return "registration";
    }

    @RequestMapping(value = "/registrationform", method = RequestMethod.GET)
    public String registrationForm(Model model) {
        model.addAttribute("email", "enter email");
        model.addAttribute("password", "enter password");

        return "fragments/fragment :: registration";
    }

    @RequestMapping(value = "/velidation", method = RequestMethod.GET)
    public String validate(@Valid RegistrationForm registrationForm, Model model) {
        model.addAttribute("email", registrationForm.getEmail());
        model.addAttribute("password", registrationForm.getPassword());

        return "fragments/success :: success";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "fragments/success :: success";
    }
}
