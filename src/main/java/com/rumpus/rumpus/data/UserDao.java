package com.rumpus.rumpus.data;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.rumpus.rumpus.models.User;
import com.rumpus.common.Mapper;
import com.rumpus.common.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Profile("database")
public class UserDao extends RumpusDao<User> implements IUserDao {
    private static final String NAME = "userDao";
    private static final String TABLE = "user";

    // @Autowired
    public UserDao() {
        super(NAME, TABLE, mapper(), add());
    }

    public static UserDao create() {
        return new UserDao();
    }

    public final static Function<User, User> add() {
        return (User user) -> {
            final String sql = "INSERT INTO user(id, name) VALUES(?, ?);";
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection conn) -> {
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, user.getId());
                statement.setString(2, user.getName());
                // statement.setInt(3, user.getAuth());
                return statement;
            }, keyHolder);
            user.setId(keyHolder.getKey().longValue());
            return user;
        };
    }

    @Override
    public User get(String name) {
        final String sql = "SELECT * FROM user WHERE name = ?;";
        return jdbcTemplate.queryForObject(sql, mapper, name);
    }

    private final static Mapper<User> mapper() {
        Mapper<User> m = new Mapper<>();
        m.setMapFunc((Pair<ResultSet, Integer> resultSetAndRow) -> {
            ResultSet rs = resultSetAndRow.getFirst();
            // int row = resultSetAndRow.getSecond();
            User u = User.createNewUser();
            HashMap<String, String> map = new HashMap<>();
            try {
                map.put("auth_id", Integer.toString(rs.getInt("auth_id")));
                map.put("user_id", Integer.toString(rs.getInt("user_id")));
                map.put("name", rs.getString("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            u.init(map);
            u.create();
            return u;
        });
        return m;
    }

    // // Row Mappers
    // private static final class UserMapper implements RowMapper<User> {
    //     @Override
    //     public User mapRow(ResultSet rs, int i) throws SQLException {
    //         return rs.getInt("auth_id") != 0 ?
    //             User.createUser(rs.getInt("user_id"), rs.getString("name"), rs.getInt("auth_id")) : 
    //             User.createUser(rs.getInt("user_id"), rs.getString("name"));
    //     }
    // }
}