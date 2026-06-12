package com.rumpus.buildshift.data.User;

import com.rumpus.common.Blob.BlobUtil;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.util.Pair;
import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserFactory;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import java.sql.Blob;

public class UserRowMapper extends AbstractJdbcRowMapper<User> {

    private UserRowMapper() {
    }

    protected static UserRowMapper create() {
        return new UserRowMapper();
    }

    @Override
    protected Function<Pair<ResultSet, Integer>, User> initMapperFunction() {
        return ((Pair<ResultSet, Integer> resultSetAndRow) -> {
            ResultSet rs = resultSetAndRow.getFirst();
            // int row = resultSetAndRow.getSecond();

            UserFactory userFactory = new UserFactory();
            User user = userFactory.createEmpty();
            try {
                user.setId(UUID.fromString(rs.getString(ID)));
                user.setEmail(rs.getString(EMAIL));
                user.setUsername(rs.getString(USERNAME));
                // user.setPassword(rs.getString(PASSWORD));

                Blob blob = rs.getBlob(USER_META_DATA);
                if (blob != null) {
                    UserMetaData metaData = UserMetaData
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
