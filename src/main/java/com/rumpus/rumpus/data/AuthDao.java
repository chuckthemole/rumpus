package com.rumpus.rumpus.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import javax.print.attribute.standard.MediaSize.NA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.rumpus.common.Mapper;
import com.rumpus.common.ApiDB.IApi;
import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.models.Auth;

public class AuthDao extends RumpusDao<Auth> implements IAuthDao {
    private static final String NAME = "authDao";
    public static final String TABLE = "auth";
    // private static RumpusApiDB<Auth> apiDB;
    private static IApi<Auth> api;

    // static {
    //     apiDB = new RumpusApiDB<>(TABLE, mapper());
    // }

    // @Autowired
    public AuthDao() {
        super(api, TABLE, NAME);
    }
    
    public static AuthDao create() {
        return new AuthDao();
    }

    private final static Function<Auth, Auth> addFunction() {
        return (Auth auth) -> {
            // final String sql = "INSERT INTO auth(id, authLevel) VALUES(?, ?);";
            // GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            // jdbcTemplate.update((Connection conn) -> {
            //     PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //     statement.setLong(1, auth.getId());
            //     statement.setString(2, auth.getLevel().toString());
            //     return statement;
            // }, keyHolder);
            // auth.setId(keyHolder.getKey().longValue());
            return auth;
        };
    }

    public final static Mapper<Auth> mapper() {
        Mapper<Auth> m = new Mapper<>();
        m.setMapFunc((Pair<ResultSet, Integer> resultSetAndRow) -> {
            ResultSet rs = resultSetAndRow.getFirst();
            // int row = resultSetAndRow.getSecond();
            HashMap<String, String> map = new HashMap<>();
            try {
                map.put("auth_id", Integer.toString(rs.getInt("auth_id")));
                // map.put("auth_id", Integer.toString(rs.getInt("auth_id")));
                // map.put("name", rs.getString("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Auth.create(map);
        });
        return m;
    }
    
}
