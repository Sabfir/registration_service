package com.registration.core;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class User {
    @NotNull @Email/*(message="Please provide a valid email address")*/
    private String email;
    @NotNull
    @Pattern(regexp = ".*[\\d]+.*[\\d]+.*",
            message = "Password has to consist letters, special symbols, number")
    private String password;

    private boolean isConfirmed;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean isConfirmed() {
        return this.isConfirmed;
    }

    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
