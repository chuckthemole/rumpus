package com.rumpus.rumpus.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.function.Function;

import com.rumpus.common.Mapper;
import com.rumpus.common.IApiDB;
import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.models.Auth;

public class AuthDao extends RumpusDao<Auth> implements IAuthDao {
    private static final String NAME = "authDao";
    private static final String TABLE = "auth";

    public AuthDao() {
        super(TABLE, NAME);
    }
    public AuthDao(IApiDB<Auth> api) {
        super(api, TABLE, NAME);
    }
    
    public static AuthDao create() {
        return new AuthDao();
    }
    public static AuthDao create(IApiDB<Auth> api) {
        return new AuthDao(api);
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

    @Override
    public Mapper<Auth> getMapper() {
        LOG.info("AuthDao::getMapper()");
        return mapper();
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
