package com.registration.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
