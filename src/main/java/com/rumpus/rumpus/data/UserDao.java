package com.rumpus.rumpus.data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.rumpus.rumpus.models.User;
import com.rumpus.common.Mapper;
import com.rumpus.common.IApiDB;
import com.rumpus.common.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("database")
public class UserDao extends RumpusDao<User> implements IUserDao {
    
    private static final String NAME = "userDao";
    private static final String TABLE = "user";
    private static final String META_TABLE = "user_meta_info";
    // private static RumpusApiDB<User> apiDB;
    // private static IApi<User> api;

    // static {
    //     apiDB = new RumpusApiDB<>(TABLE, mapper());
    // }

    public UserDao() {
        super(TABLE, NAME);
    }
    public UserDao(IApiDB<User> api) {
        super(api, TABLE, NAME);
    }

    public static UserDao create() {
        return new UserDao();
    }
    public static UserDao create(IApiDB<User> api) {
        return new UserDao(api);
    }

    public final static Function<User, User> add() {
        return (User user) -> {
            // Long userId = user.getId();
            // final String sql = "INSERT INTO user(name, id) VALUES(?, ?);";
            // GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            // jdbcTemplate.update((Connection conn) -> {
            //     PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //     statement.setString(1, user.getName());
            //     statement.setLong(2, userId);
            //     // statement.setInt(3, user.getAuth());
            //     return statement;
            // }, keyHolder);
            // if(keyHolder.getKey() != null) {
            //     user.setId(keyHolder.getKey().longValue());
            // } else {
            //     user.setId(NO_ID);
            // }
            return user;
        };
    }

    @Override
    public User get(String name) {
        Map<String, String> constraint = new HashMap<>();
        constraint.put("name", name);
        return super.get(constraint).get(0);
        // final String sql = "SELECT * FROM user WHERE name = ?;";
        // return jdbcTemplate.queryForObject(sql, mapper(), name);
    }

    @Override
    public Mapper<User> getMapper() {
        LOG.info("UserDao::getMapper()");
        return mapper();
    }

    private final static Mapper<User> mapper() {
        Mapper<User> mapper = new Mapper<>();
        mapper.setMapFunc((Pair<ResultSet, Integer> resultSetAndRow) -> {
            ResultSet rs = resultSetAndRow.getFirst();
            // int row = resultSetAndRow.getSecond();
            HashMap<String, String> map = new HashMap<>();
            try {
                map.put(AUTH_ID, Integer.toString(rs.getInt(AUTH_ID)));
                map.put(USER_ID, Integer.toString(rs.getInt(USER_ID)));
                map.put(USERNAME, rs.getString(USERNAME));
                map.put(PASSWORD, rs.getString(PASSWORD));
                map.put(EMAIL, rs.getString(EMAIL));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return User.create(map);
        });
        return mapper;
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