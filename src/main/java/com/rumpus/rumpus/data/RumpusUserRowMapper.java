package com.rumpus.rumpus.data;

import com.rumpus.common.Blob.BlobUtil;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserFactory;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import java.sql.Blob;

public class RumpusUserRowMapper extends AbstractJdbcRowMapper<RumpusUser> {

    private RumpusUserRowMapper() {
    }

    protected static RumpusUserRowMapper create() {
        return new RumpusUserRowMapper();
    }

    @Override
    protected Function<Pair<ResultSet, Integer>, RumpusUser> initMapperFunction() {
        return ((Pair<ResultSet, Integer> resultSetAndRow) -> {
            ResultSet rs = resultSetAndRow.getFirst();
            // int row = resultSetAndRow.getSecond();

            RumpusUserFactory userFactory = new RumpusUserFactory();
            RumpusUser user = userFactory.createEmpty();

            try {
                user.setEmail(rs.getString(EMAIL));
                user.setUsername(rs.getString(USERNAME));
                // user.setEncodedPassword(rs.getString(PASSWORD));
                user.setId(UUID.fromString(rs.getString(ID)));

                Blob blob = rs.getBlob(USER_META_DATA);
                if (blob != null) {
                    RumpusUserMetaData metaData = RumpusUserMetaData
                            .createFromStream(BlobUtil.getObjectInputStream(blob).get());
                    user.setMetaData(metaData);
                } else {
                    user.setMetaData(userFactory.createMetaData());
                }
            } catch (SQLException e) {
                final String log = LogBuilder
                        .logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace())
                        .toString();
                LOG(LogLevel.ERROR, log);
            }
            return user;
        });
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
