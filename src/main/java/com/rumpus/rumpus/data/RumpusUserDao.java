package com.rumpus.rumpus.data;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.CommonExceptionInterceptor;
import com.rumpus.common.Mapper;
import com.rumpus.common.Blob.AbstractBlob;
import com.rumpus.common.Blob.JdbcBlob;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Dao.Dao;
import com.rumpus.common.Dao.IApiDB;
import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.models.RumpusUser;

public class RumpusUserDao extends Dao<RumpusUser> implements IRumpusUserDao {

    private static final String NAME = "RumpusUserDao";
    private static final String TABLE = "user";
    private static final String META_TABLE = "user_meta_info";
    // private static final String sqlRumpusUserInsert = "INSERT INTO user VALUES(:id, :username, :email)"; // ApiDBJdbc look at org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

    public RumpusUserDao() {
        super(TABLE, META_TABLE, NAME);
        this.api = null;
        IApiDB.registerIdSet("RumpusUser");
    }
    public RumpusUserDao(IApiDB<RumpusUser> api) {
        super(TABLE, META_TABLE, NAME);
        this.api = api;
        IApiDB.registerIdSet("RumpusUser");
    }

    @Override
    public Mapper<RumpusUser> getMapper() {
        LOG.info("UserDao::getMapper()");
        return rumpusUserMapper();
    }

    private final static Mapper<RumpusUser> rumpusUserMapper() {
        LOG.info("RumpusUserDao::rumpusUserMapper()");
        Mapper<RumpusUser> rumpusUserMapper = new Mapper<>();
        rumpusUserMapper.setMapFunc((Pair<ResultSet, Integer> resultSetAndRow) -> {
            ResultSet rs = resultSetAndRow.getFirst();
            // int row = resultSetAndRow.getSecond();
            Map<String, Object> rumpusUserMap = new HashMap<>();
            try {
                rumpusUserMap.put(ID, rs.getString(ID));
                rumpusUserMap.put(USERNAME, rs.getString(USERNAME));
                // rumpusUserMap.put(PASSWORD, rs.getString(PASSWORD));
                rumpusUserMap.put(EMAIL, rs.getString(EMAIL));
                rumpusUserMap.put(USER_META_DATA, AbstractBlob.getParams(rs.getBlob(USER_META_DATA)));
            } catch (SQLException e) {
                LOG.info("Error: rumpusUserMapping RumpusUser");
                LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
            }
            return RumpusUser.createFromMap(rumpusUserMap);
        });
        return rumpusUserMapper;
    }
}
