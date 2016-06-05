package com.registration.core;

public interface EndPoints {
    //URLs for RegistrationController
    String BASE_URL = "/registration";
    String REGISTRATION_FORM = BASE_URL + "/registrationForm";
    String VALIDATION = BASE_URL + "/validation";
    String WELCOME_PAGE = BASE_URL + "/welcomePage/{hashCodeMessage}";
}
