package com.registration.dao;

import com.registration.dao.helper.SqlInitializer;
import com.registration.core.User;
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

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createUser(String email, String password) throws DataAccessException {
        String SQL = "insert into users (email, password) values (?, ?)";
        jdbcTemplateObject.update(SQL, email, password);
        //TODO logging ("Created new user email = " + email);
    }

    @Override
    public User getUser(String email) throws DataAccessException {
        String SQL = "select * from users where email = ?";
        User user = jdbcTemplateObject.queryForObject(SQL, new Object[]{email}, new UserMapper());
        return user;
    }

    @Override
    public List<User> listUsers() throws DataAccessException {
        String SQL = "select * from users";
        List <User> userList = jdbcTemplateObject.query(SQL, new UserMapper());
        return userList;
    }


    @Override
    public void deleteUser(String email) throws DataAccessException {
        String SQL = "delete from users where email = ?";
        jdbcTemplateObject.update(SQL, email);
        //TODO logging ("Deleted Record with email = " + email );
    }

    @Override
    public void truncateTable() throws DataAccessException {
        String SQL = "delete from users";
        jdbcTemplateObject.update(SQL);
        //TODO logging ("Deleted all Record with email = " + email );
    }

    @Override
    public void changePassword(String email, String password) throws DataAccessException {
        String SQL = "update users set password = ? where email = ?";
        jdbcTemplateObject.update(SQL, password, email);
        System.out.println("Changed password for email " + email);
    }

    @Override
    public void changeConfirmation(String email, boolean isConfirmed) throws DataAccessException {
        String SQL = "update users set is_confirmed = ? where email = ?";
        jdbcTemplateObject.update(SQL, isConfirmed, email);
        System.out.println("Changed password for email " + email);
    }

    private class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("email"), rs.getString("password"));
            user.setIsConfirmed(rs.getBoolean("is_confirmed"));
            return user;
        }
    }

    @PostConstruct
    public void initialize() {
        try {
            final Connection connection = this.jdbcTemplateObject.getDataSource().getConnection();
            SqlInitializer.initializeDatabase(connection);
        } catch (SQLException e) {
            //TODO logging problem with spring jdbcTemplate initialization
        }
    }

    public UserDaoImpl() {}

}
