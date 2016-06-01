package com.registration.dao;

import core.User;

import javax.annotation.PostConstruct;
import java.util.List;

public interface UserDao {
//    void initialize();

    void createUser(String email, String password);

    User getUser(String email);

    List<User> listUsers();

    void deleteUser(String email);

    void truncateTable();

    void changePassword(String email, String password);

    void changeConfirmation(String email, boolean isConfirmed);
}
