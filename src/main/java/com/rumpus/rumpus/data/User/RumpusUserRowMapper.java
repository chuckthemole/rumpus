package com.rumpus.rumpus.data.User;

import com.rumpus.common.Blob.BlobUtil;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
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

    public static RumpusUserRowMapper create() {
        return new RumpusUserRowMapper();
    }

    @Override
    protected Function<Pair<ResultSet, Integer>, RumpusUser> initMapperFunction() {
        return resultSetAndRow -> {
            ResultSet rs = resultSetAndRow.getFirst();
            int row = resultSetAndRow.getSecond();

            try {
                UUID userId = UUID.fromString(rs.getString(ID));
                String email = rs.getString(EMAIL);
                String username = rs.getString(USERNAME);
                // String password = rs.getString(PASSWORD);

                RumpusUserFactory userFactory = new RumpusUserFactory();

                Blob blob = rs.getBlob(USER_META_DATA);
                RumpusUserMetaData metaData;

                if (blob != null) {
                    metaData = RumpusUserMetaData.createFromStream(
                            BlobUtil.getObjectInputStream(blob)
                                    .orElseThrow(() -> new IllegalStateException(
                                            "Unable to read user metadata for user ID: "
                                                    + userId)));
                } else {
                    metaData = userFactory.createMetaData();
                }

                return userFactory.createUser(
                        userId, username, email, "", metaData);

            } catch (SQLException | IllegalArgumentException e) {
                throw new IllegalStateException(
                        "Failed to map user from database row " + row, e);
            }
        };
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
