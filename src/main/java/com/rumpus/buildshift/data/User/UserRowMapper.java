package com.rumpus.buildshift.data.User;

import com.rumpus.common.Blob.BlobUtil;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.util.Pair;
import com.rumpus.rumpus.IRumpus;
import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;

import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import java.sql.Blob;

public class UserRowMapper extends AbstractJdbcRowMapper<User> {

    private UserRowMapper() {}

    protected static UserRowMapper create() {
        return new UserRowMapper();
    }

    @Override
    protected Function<Pair<ResultSet, Integer>, User> initMapperFunction() {
        return ((Pair<ResultSet, Integer> resultSetAndRow) -> {
            ResultSet rs = resultSetAndRow.getFirst();
            // int row = resultSetAndRow.getSecond();
            Map<String, Object> UserMap = new HashMap<>();
            try {
                UserMap.put(ID, rs.getString(ID));
                UserMap.put(USERNAME, rs.getString(USERNAME));
                // UserMap.put(PASSWORD, rs.getString(PASSWORD));
                UserMap.put(EMAIL, rs.getString(EMAIL));
                Blob blob = rs.getBlob(USER_META_DATA);
                if (blob != null) {
                    // UserMap.put(USER_META_DATA, BlobUtil.<UserMetaData>getObjectFromBlob(blob).get());
                    UserMetaData metaData = UserMetaData.createFromStream(BlobUtil.getObjectInputStream(blob).get());
                    UserMap.put(USER_META_DATA, metaData);
                } else {
                    UserMap.put(USER_META_DATA, UserMetaData.createEmpty());
                }
            } catch (SQLException e) {
                final String log = LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).toString();
                LOG(LogLevel.ERROR, log);
            }
            // return User.createFromMap(UserMap);
            return User.create((String) UserMap.get(USERNAME), "", (String) UserMap.get(EMAIL));
        });
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
