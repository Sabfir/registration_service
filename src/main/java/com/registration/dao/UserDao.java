package com.registration.dao;

import com.registration.core.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface UserDao {

    void createUser(String email, String password) throws DataAccessException;

    User getUser(String email) throws DataAccessException;

    List<User> listUsers() throws DataAccessException;

    void deleteUser(String email) throws DataAccessException;

    void truncateTable() throws DataAccessException;

    void changePassword(String email, String password) throws DataAccessException;

    void changeConfirmation(String email, boolean isConfirmed) throws DataAccessException;
}
