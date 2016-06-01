package com.registration.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;

public class RegistrationForm {
    @Email
    private String email;
    @Min(3)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
