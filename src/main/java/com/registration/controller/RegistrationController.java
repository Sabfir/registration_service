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
/*@SessionAttributes("user")*/
public class RegistrationController {
    @Autowired
    private JmsService jmsService;

    @Autowired
    private TemplateEngine templateEngine;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String registrationMain(User user) {
       // model.addAttribute("showRegistration", true);
        return "index";
    }

    @RequestMapping(value = "/registrationForm", method = RequestMethod.GET)
    public String registrationForm(@ModelAttribute("user") User user, Model model) {
        //model.addAttribute("user", new User("email@gmail.com", "password123"));
        //model.addAttribute("showRegistration", true);
        //User user = new User("", "");
        //model.addAttribute("user", user);

        return "fragments/registration";
    }


    @RequestMapping(value = "/validation", method = RequestMethod.POST)
    public String validateCredentials(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "fragments/registration";
        }

        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
        return "fragments/success";
    }

    @RequestMapping(value = "/validation1", method = RequestMethod.POST)
    @ResponseBody
    //public String validateCredentials(@Valid ModelAttribute("user") User user, BindingResult bindingResult) {
    public String validateCredentials(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // TODO logging
            System.out.println("Some errors occurred while validate user");

            String errors = "";
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;

                    errors += fieldError.getField();
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;

                    errors += objectError.toString();
                }
            }

            Context context = new Context();
            context.setVariable("passworderror", "some error");
            context.setVariable("user", user);
            context.setVariable("errors", errors);
            return TemplateBuilder.processTemplate("fragments/registration", context);
        } else {
            // TODO logging
            System.out.println("User validated successfully");

            jmsService.sendMessage(user);
            return TemplateBuilder.processTemplate("fragments/success", new Context());
        }
    }

    @RequestMapping(value = "/welcomePage/{hashCodeMessage}", method = RequestMethod.GET)
    public String welcomePage(@PathVariable String hashCodeMessage) {
        return "fragments/welcome";
    }
}
