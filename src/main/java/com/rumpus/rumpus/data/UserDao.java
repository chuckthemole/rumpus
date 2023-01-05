package com.rumpus.rumpus.data;

import java.util.List;

import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.models.Auth;
import com.rumpus.rumpus.models.RumpusModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

// @Repository
// @Profile("database")
public class UserDao implements IUserDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String NAME = "user";

    // @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {
        final String sql = "INSERT INTO user(id, name) VALUES(?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            // statement.setInt(3, user.getAuth());
            return statement;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());

        return user;
    }

    @Override
    public boolean remove(int id) {
        // TODO: Check to see if user's auth is not used by any other used, then delete auth too
        final String sql = "DELETE FROM user WHERE user_id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public User get(int id) {
        final String sql = "SELECT * FROM user WHERE user_id = ?;";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
    }

    @Override
    public User get(String name) {
        final String sql = "SELECT * FROM user WHERE name = ?;";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), name);
    }

    @Override
    public List<User> getAll() {
        final String sql = "SELECT * FROM user;";
        List<User> users = jdbcTemplate.query(sql, new UserMapper());
        return users;
    }

    // Row Mappers
    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt("auth_id") != 0 ?
                User.createUser(rs.getInt("user_id"), rs.getString("name"), rs.getInt("auth_id")) : 
                User.createUser(rs.getInt("user_id"), rs.getString("name"));
        }
    }

    @Override
    public String name() {
        return NAME;
    }
}