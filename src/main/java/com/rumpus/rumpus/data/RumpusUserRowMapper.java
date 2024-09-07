package com.rumpus.rumpus.data;

import com.rumpus.common.Blob.AbstractBlob;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.IRumpus;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;

import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class RumpusUserRowMapper extends AbstractJdbcRowMapper<RumpusUser> {

    private static final String NAME = "RumpusUserRowMapper";

    private RumpusUserRowMapper() {
        super(NAME);
    }

    protected static RumpusUserRowMapper create() {
        return new RumpusUserRowMapper();
    }

    @Override
    @SuppressWarnings(UNCHECKED)
    protected Function<Pair<ResultSet, Integer>, RumpusUser> initMapperFunction() {
        return ((Pair<ResultSet, Integer> resultSetAndRow) -> {
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
    }
}
