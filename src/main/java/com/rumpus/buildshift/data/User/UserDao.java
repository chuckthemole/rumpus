package com.rumpus.buildshift.data.User;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Dao.jdbc.ApiDBJdbcUsers;
import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;

public class UserDao extends ApiDBJdbcUsers<User, UserMetaData> implements IUserDao {

    private static final String TABLE = "user";
    private static final String META_TABLE = "user_meta_info";

    public UserDao(JdbcUserDetailsManager manager) {
        super(manager, TABLE, UserRowMapper.create());
        IDao.registerIdSet("BuildShiftUser"); // TODO: Can this be moved up to RumpusUserDao? Or even higher in common?
    }
}
