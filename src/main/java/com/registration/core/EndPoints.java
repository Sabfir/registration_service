package com.registration.core;

/**
 * The EndPoints interface contains all urls, you can use to invoke controllers API
 *
 * @author  Alex Pinta, Oleh Pinta
 */
public interface EndPoints {
    //URLs for RegistrationController
    String BASE_URL = "/registration";
    String REGISTRATION_FORM = BASE_URL + "/registrationForm";
    String VALIDATION = BASE_URL + "/validation";
    String CONFIRM_PAGE = "/confirm/{hashCodeMessage}";
    String WELCOME_PAGE = "/success";
}
