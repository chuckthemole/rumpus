package com.rumpus.buildshift.data.User;

import com.rumpus.common.Blob.BlobUtil;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
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
        return resultSetAndRow -> {
            ResultSet rs = resultSetAndRow.getFirst();
            int row = resultSetAndRow.getSecond();

            try {
                UUID userId = UUID.fromString(rs.getString(ID));
                String email = rs.getString(EMAIL);
                String username = rs.getString(USERNAME);
                String password = rs.getString(PASSWORD);

                UserFactory userFactory = new UserFactory();

                Blob blob = rs.getBlob(USER_META_DATA);
                UserMetaData metaData;

                if (blob != null) {
                    metaData = UserMetaData.createFromStream(
                            BlobUtil.getObjectInputStream(blob)
                                    .orElseThrow(() -> new IllegalStateException(
                                            "Unable to read user metadata for user ID: "
                                                    + userId)));
                } else {
                    metaData = userFactory.createMetaData();
                }

                return userFactory.createUser(
                        userId, username, email, password, metaData);

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
