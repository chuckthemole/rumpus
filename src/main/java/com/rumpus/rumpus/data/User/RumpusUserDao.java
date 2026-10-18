package com.rumpus.rumpus.data.User;

import javax.sql.DataSource;

import com.rumpus.common.Dao.User.jdbc.UserDaoJdbc;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

public class RumpusUserDao extends UserDaoJdbc<RumpusUser, RumpusUserMetaData>
        implements
            IRumpusUserDao {

    public RumpusUserDao(DataSource dataSource, String usersTable, String userDetailsTable) {
        super(dataSource, usersTable, userDetailsTable, RumpusUserRowMapper.create());
    }
}
