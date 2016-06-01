package com.registration.dao;

import com.registration.dao.helper.SqlInitializer;
import com.zaxxer.hikari.HikariDataSource;
import core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void createUser(String email, String password) {
        String SQL = "insert into users (email, password) values (?, ?)";
        jdbcTemplateObject.update(SQL, email, password);
        //TODO logging ("Created new user email = " + email);
    }

    @Override
    public User getUser(String email) {
        String SQL = "select * from users where email = ?";
        User user = jdbcTemplateObject.queryForObject(SQL, new Object[]{email}, new UserMapper());
        return user;
    }

    @Override
    public List<User> listUsers() {
        String SQL = "select * from users";
        List <User> userList = jdbcTemplateObject.query(SQL, new UserMapper());
        return userList;
    }


    @Override
    public void deleteUser(String email){
        String SQL = "delete from users where email = ?";
        jdbcTemplateObject.update(SQL, email);
        //TODO logging ("Deleted Record with email = " + email );
        return;
    }

    @Override
    public void truncateTable(){
        String SQL = "delete from users";
        jdbcTemplateObject.update(SQL);
        //TODO logging ("Deleted all Record with email = " + email );
        return;
    }

    @Override
    public void changePassword(String email, String password){
        String SQL = "update users set password = ? where email = ?";
        jdbcTemplateObject.update(SQL, password, email);
        System.out.println("Changed password for email " + email);
        return;
    }

    @Override
    public void changeConfirmation(String email, boolean isConfirmed){
        String SQL = "update users set is_confirmed = ? where email = ?";
        jdbcTemplateObject.update(SQL, isConfirmed, email);
        System.out.println("Changed password for email " + email);
        return;
    }

    private class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("email"), rs.getString("password"));
            user.setIsConfirmed(rs.getBoolean("is_confirmed"));
            return user;
        }
    }

    public UserDaoImpl(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;

        try {
            final Connection connection = this.jdbcTemplateObject.getDataSource().getConnection();
            SqlInitializer.initializeDatabase(connection);
        } catch (SQLException e) {
            //TODO logging problem with spring jdbcTemplate initialization
        }
    }

    public UserDaoImpl() {}

}
