package com.registration.dao;

import com.registration.dao.helper.SqlInitializer;
import com.registration.core.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The UserDaoImpl class implements CRUD DAO operations.
 * You can invoke different methods to manage your DB
 *
 * @author  Alex Pinta, Oleh Pinta
 */
@Repository
public class UserDaoImpl implements UserDao {
    final static Logger logger = Logger.getLogger(UserDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    public UserDaoImpl() {}

    /**
     * This method is used to create user.
     * It receives email and password and inserts data to the DB
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createUser(String email, String password) throws DataAccessException {
        final String SQL = "insert into users (email, password) values (?, ?)";
        jdbcTemplateObject.update(SQL, email, password);
        logger.info("Created new user with email " + email);
    }

    /**
     * This method is used to get user by email.
     * It receives email and returns found user from the DB
     */
    @Override
    public User getUser(String email) throws DataAccessException {
        final String SQL = "select * from users where email = ?";
        User user = jdbcTemplateObject.queryForObject(SQL, new Object[]{email}, new UserMapper());
        return user;
    }

    /**
     * This method is used to get all users.
     * It returns all users from the users table
     */
    @Override
    public List<User> listUsers() throws DataAccessException {
        final String SQL = "select * from users";
        List <User> userList = jdbcTemplateObject.query(SQL, new UserMapper());
        return userList;
    }

    /**
     * This method is used to delete user by email.
     * It receives email and delete all users with the given email
     */
    @Override
    public void deleteUser(String email) throws DataAccessException {
        final String SQL = "delete from users where email = ?";
        jdbcTemplateObject.update(SQL, email);
        logger.info("Deleted user with email " + email);
    }

    /**
     * This method is used to delete all records from the table users.
     */
    @Override
    public void truncateTable() throws DataAccessException {
        final String SQL = "delete from users";
        jdbcTemplateObject.update(SQL);
        logger.info("Deleted all users");
    }

    /**
     * This method is used to change user password.
     * It receives email and password to be changed
     * It changes password for all users with the given email
     */
    @Override
    public void changePassword(String email, String password) throws DataAccessException {
        final String SQL = "update users set password = ? where email = ?";
        jdbcTemplateObject.update(SQL, password, email);
        logger.info("Changed password for email " + email);
    }

    /**
     * This method is used to change user confirmation.
     * It receives email and confirmation to be changed
     * It changes confirmation for all users with the given email
     */
    @Override
    public void changeConfirmation(String email, boolean isConfirmed) throws DataAccessException {
        final String SQL = "update users set is_confirmed = ? where email = ?";
        jdbcTemplateObject.update(SQL, isConfirmed, email);
        logger.info("Changed confirmation for email " + email);
    }

    /**
     * The UserMapper class is used for mapping user model and users DB table
     *
     * @author  Alex Pinta, Oleh Pinta
     */
    private class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("email"), rs.getString("password"));
            user.setIsConfirmed(rs.getBoolean("is_confirmed"));
            return user;
        }
    }

    /**
     * This method is used to initialize your DB.
     * It invokes create tables algorithms
     */
    @PostConstruct
    public void initialize() {
        try {
            final Connection connection = this.jdbcTemplateObject.getDataSource().getConnection();
            SqlInitializer.initializeDatabase(connection);
        } catch (SQLException e) {
            logger.info("Logging problem with spring jdbcTemplate initialization");
        }
    }
}
