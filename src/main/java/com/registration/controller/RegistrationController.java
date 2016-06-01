package com.registration.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.JmsService;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    JmsService jmsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String registrationMain() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ResponseEntity registrationPage() {
        System.out.println("111");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/registrationform", method = RequestMethod.GET)
    public String registrationForm(Model model) {
        System.out.println("Registrationform controller called");

        /*if (model.containsAttribute("showfragment")) {
            Map<String, Object> modelMap = model.asMap();
            System.out.println("showfragment = " + modelMap.get("showfragment"));
        }

        model.addAttribute("showfragment", true);*/

        return "fragments/fragment :: reg";
    }
}
