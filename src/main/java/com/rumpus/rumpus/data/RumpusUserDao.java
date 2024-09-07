package com.rumpus.rumpus.data;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Dao.jdbc.ApiDBJdbcUsers;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;

public class RumpusUserDao extends ApiDBJdbcUsers<RumpusUser, RumpusUserMetaData> implements IRumpusUserDao {

    private static final String NAME = "RumpusUserDao";
    private static final String TABLE = "user";
    private static final String META_TABLE = "user_meta_info";

    public RumpusUserDao(JdbcUserDetailsManager manager) {
        super(NAME, manager, TABLE, RumpusUserRowMapper.create());
        IDao.registerIdSet("RumpusUser"); // TODO: Can this be moved up to RumpusUserDao? Or even higher in common?
    }
}
