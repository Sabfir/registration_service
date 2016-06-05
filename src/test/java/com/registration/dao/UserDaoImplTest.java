package com.registration.dao;

import configuration.SpringTestConfiguration;
import com.registration.core.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestConfiguration.class)
public class UserDaoImplTest {
    final String EMAIL = "test@test.ua";
    final String PASSWORD = "test123";

    @Autowired
    UserDao userDao;

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testCreateUser() throws Exception {
        final int COUNT_CREATED_USER = 1;
        userDao.createUser(EMAIL, PASSWORD);
        assertEquals(userDao.listUsers().size(), COUNT_CREATED_USER);
        userDao.truncateTable();
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testGetUser() throws Exception {
        userDao.createUser(EMAIL, PASSWORD);
        final User user = userDao.getUser(EMAIL);
        assertEquals(user.getEmail(), EMAIL);
        assertEquals(user.getPassword(), PASSWORD);
        assertFalse(user.isConfirmed());
        userDao.truncateTable();
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testListUsers() throws Exception {
        final int COUNT_CREATED_USER = 2;
        final String ANOTHER_EMAIL = "test2@test.ua";
        final String ANOTHER_PASSWORD = "test321";
        userDao.createUser(EMAIL, PASSWORD);
        userDao.createUser(ANOTHER_EMAIL, ANOTHER_PASSWORD);
        assertEquals(userDao.listUsers().size(), COUNT_CREATED_USER);
        userDao.truncateTable();
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testDelete() throws Exception {
        final int COUNT_CREATED_USER = 0;
        userDao.createUser(EMAIL, PASSWORD);
        userDao.deleteUser(EMAIL);
        assertEquals(userDao.listUsers().size(), COUNT_CREATED_USER);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testChangePassword() throws Exception {
        final String NEW_PASSWORD = "test_new_password";
        userDao.createUser(EMAIL, PASSWORD);
        userDao.changePassword(EMAIL, NEW_PASSWORD);
        assertEquals(userDao.getUser(EMAIL).getPassword(), NEW_PASSWORD);
        userDao.truncateTable();
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testChangeConfirmation() throws Exception {
        final boolean NEW_STATUS_CONFIRMATION = true;
        userDao.createUser(EMAIL, PASSWORD);
        userDao.changeConfirmation(EMAIL, NEW_STATUS_CONFIRMATION);
        assertEquals(userDao.getUser(EMAIL).isConfirmed(), NEW_STATUS_CONFIRMATION);
        userDao.changeConfirmation(EMAIL, !NEW_STATUS_CONFIRMATION);
        assertEquals(userDao.getUser(EMAIL).isConfirmed(), !NEW_STATUS_CONFIRMATION);
        userDao.truncateTable();
    }
}