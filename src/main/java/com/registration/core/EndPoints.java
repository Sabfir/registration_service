package com.registration.core;

public interface EndPoints {
    //URLs for RegistrationController
    String BASE_URL = "/registration";
    String REGISTRATION_FORM = "/registration/form";
    String VALIDATION = "/registration/validation";
    String WELCOME_PAGE = "/registration/welcomePage/{hashCodeMessage}";
}
