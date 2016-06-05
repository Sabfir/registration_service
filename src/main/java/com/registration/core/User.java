package com.registration.core;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * The User class is a model class that contains credentials data
 * It use Hibernate validator for the email and password verification
 *
 * @author  Alex Pinta, Oleh Pinta
 */
public class User {
    @Email
    @NotEmpty(message = "Email must be populated")
    private String email;

    @Pattern.List({
        @Pattern(regexp = ".*[\\d]+.*[\\d]+.*", message = "Invalid password! It should have at least two digits."),
        @Pattern(regexp = ".*[\\!]+.*", message = "Invalid password! It should contain symbol \"!\"."),
    })
    private String password;

    private boolean isConfirmed;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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
