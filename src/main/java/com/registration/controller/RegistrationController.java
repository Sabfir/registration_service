package com.registration.controller;

import com.google.gson.Gson;
import com.registration.model.RegistrationForm;
import core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import service.JmsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;

@Controller
public class RegistrationController {
    @Autowired
    private JmsService jmsService;

    @Autowired
    private TemplateEngine templateEngine;

    private StringWriter writer = new StringWriter();
    Context context = new Context();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String registrationMain() {
        return "registration";
    }

    @RequestMapping(value = "/registrationform", method = RequestMethod.GET)
    public String registrationForm(Model model) {
//        context.setVariable("email", "enter email");
//        context.setVariable("password", "enter password");
//        templateEngine.process("fragments/fragment :: registration", context, writer);

        return "fragments/fragment :: registration";
    }

    @RequestMapping(value = "/velidation", method = RequestMethod.POST)
    @ResponseBody
    public String validate(HttpServletRequest request) {
        context.setVariable("email", request.getParameter("email"));
        context.setVariable("password", request.getParameter("password"));
        templateEngine.process("fragments/success", context, writer);

        return writer.toString();
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "fragments/success :: success";
    }
}
