package com.registration.controller;


import com.registration.model.RegistrationForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
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
        /*if (bindingResult.hasErrors()) {
            return "fragments/fragment :: registration";
        }*/


//        ResponseEntity res = new ResponseEntity(model, HttpStatus.OK);

        model.addAttribute("email", registrationForm.getEmail());
        model.addAttribute("password", registrationForm.getPassword());

        return "fragments/success :: success";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "fragments/success :: success";
    }
}
