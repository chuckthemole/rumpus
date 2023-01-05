package com.rumpus.rumpus.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.rumpus.rumpus.models.Auth;

public class AuthDao implements IAuthDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String NAME = "auth";

    // @Autowired
    public AuthDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Auth get(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Auth> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Auth add(Auth auth) {
        final String sql = "INSERT INTO auth(id, authLevel) VALUES(?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, auth.getId());
            statement.setString(2, auth.getLevel().toString());
            return statement;
        }, keyHolder);
        auth.setId(keyHolder.getKey().intValue());

        return auth;
    }

    @Override
    public boolean remove(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String name() {
        return NAME;
    }
    
}
