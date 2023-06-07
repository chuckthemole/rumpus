package com.rumpus.rumpus.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.Dao;
import com.rumpus.common.IApiDB;
import com.rumpus.common.Mapper;
import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.models.RumpusUser;

public class RumpusUserDao extends Dao<RumpusUser> implements IRumpusUserDao {

    private static final String NAME = "RumpusUserDao";
    private static final String TABLE = "user";
    private static final String META_TABLE = "user_meta_info";

    public RumpusUserDao() {
        super(TABLE, META_TABLE, NAME);
        this.api = null;
    }
    public RumpusUserDao(IApiDB<RumpusUser> api) {
        super(TABLE, META_TABLE, NAME);
        this.api = api;
    }

    @Override
    public Mapper<RumpusUser> getMapper() {
        LOG.info("UserDao::getMapper()");
        return mapper();
    }

    private final static Mapper<RumpusUser> mapper() {
        Mapper<RumpusUser> mapper = new Mapper<>();
        mapper.setMapFunc((Pair<ResultSet, Integer> resultSetAndRow) -> {
            ResultSet rs = resultSetAndRow.getFirst();
            // int row = resultSetAndRow.getSecond();
            Map<String, String> map = new HashMap<>();
            try {
                map.put(AUTH_ID, Integer.toString(rs.getInt(AUTH_ID)));
                map.put(ID, rs.getString(ID));
                map.put(USERNAME, rs.getString(USERNAME));
                // map.put(PASSWORD, rs.getString(PASSWORD));
                map.put(EMAIL, rs.getString(EMAIL));
            } catch (SQLException e) {
                LOG.info("RumpusUserDao()::mapper()");
                e.printStackTrace();
            }
            return RumpusUser.create(map);
        });
        return mapper;
    }

}
