package com.rumpus.rumpus.data.User;

import javax.sql.DataSource;

import com.rumpus.common.Dao.User.ApiDBJdbcUsers;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

public class RumpusUserDao extends ApiDBJdbcUsers<RumpusUser, RumpusUserMetaData>
        implements
            IRumpusUserDao {

    private static final String TABLE = "user";

    public RumpusUserDao(DataSource dataSource) {
        super(dataSource, TABLE, RumpusUserRowMapper.create());
    }
}
