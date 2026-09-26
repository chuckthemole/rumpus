package com.rumpus.rumpus.data.User;

import javax.sql.DataSource;

import com.rumpus.common.Dao.User.jdbc.UserDaoJdbc;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

public class RumpusUserDao extends UserDaoJdbc<RumpusUser, RumpusUserMetaData>
        implements
            IRumpusUserDao {

    private static final String TABLE = "user"; // TODO - make this configurable

    public RumpusUserDao(DataSource dataSource) {
        super(dataSource, TABLE, RumpusUserRowMapper.create());
    }
}
