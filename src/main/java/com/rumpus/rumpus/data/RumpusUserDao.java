package com.rumpus.rumpus.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.Blob.AbstractBlob;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Dao.IApiDB;
import com.rumpus.common.Dao.jdbc.ApiDBJdbcUsers;
import com.rumpus.common.Dao.jdbc.Mapper;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.IRumpus;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;

public class RumpusUserDao extends ApiDBJdbcUsers<RumpusUser, RumpusUserMetaData> implements IRumpusUserDao {

    private static final String NAME = "RumpusUserDao";
    private static final String TABLE = "user";
    private static final String META_TABLE = "user_meta_info";

    public RumpusUserDao(JdbcUserDetailsManager manager) {
        super(manager, TABLE, RumpusUserDao.rumpusUserMapper());
        IApiDB.registerIdSet("RumpusUser");
    }

    @Override
    public Mapper<RumpusUser> getMapper() {
        LOG("UserDao::getMapper()");
        return rumpusUserMapper();
    }

    @SuppressWarnings(UNCHECKED)
    private final static Mapper<RumpusUser> rumpusUserMapper() {
        com.rumpus.common.ICommon.LOG(RumpusUserDao.class, "RumpusUserDao::rumpusUserMapper()");
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
                rumpusUserMap.put(USER_META_DATA, (AbstractCommonUserMetaData<RumpusUserMetaData>) AbstractBlob.getObjectFromBlob(rs.getBlob(USER_META_DATA)));
            } catch (SQLException e) {
                IRumpus.LOG(RumpusUserDao.class, "Error: rumpusUserMapping RumpusUser");
                LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
            }
            return RumpusUser.createFromMap(rumpusUserMap);
        });
        return rumpusUserMapper;
    }
}
