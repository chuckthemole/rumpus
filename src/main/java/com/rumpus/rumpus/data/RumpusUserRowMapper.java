package com.rumpus.rumpus.data;

import com.rumpus.common.Blob.BlobUtil;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.IRumpus;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import java.sql.Blob;

public class RumpusUserRowMapper extends AbstractJdbcRowMapper<RumpusUser> {

    private static final String NAME = "RumpusUserRowMapper";

    private RumpusUserRowMapper() {
        super(NAME);
    }

    protected static RumpusUserRowMapper create() {
        return new RumpusUserRowMapper();
    }

    @Override
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
                Blob blob = rs.getBlob(USER_META_DATA);
                if (blob != null) {
                    // rumpusUserMap.put(USER_META_DATA, BlobUtil.<RumpusUserMetaData>getObjectFromBlob(blob).get());
                    RumpusUserMetaData metaData = RumpusUserMetaData.createFromStream(BlobUtil.getObjectInputStream(blob).get());
                    rumpusUserMap.put(USER_META_DATA, metaData);
                } else {
                    rumpusUserMap.put(USER_META_DATA, RumpusUserMetaData.createEmpty());
                }
            } catch (SQLException e) {
                IRumpus.LOG(RumpusUserDao.class, "Error: rumpusUserMapping RumpusUser");
                LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
            }
            return RumpusUser.createFromMap(rumpusUserMap);
        });
    }
}
