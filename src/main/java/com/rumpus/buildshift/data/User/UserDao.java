package com.rumpus.buildshift.data.User;

import javax.sql.DataSource;

import com.rumpus.common.Dao.User.ApiDBJdbcUsers;
import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;

public class UserDao extends ApiDBJdbcUsers<User, UserMetaData> implements IUserDao {

    private static final String TABLE = "user";

    public UserDao(DataSource dataSource) {
        super(dataSource, TABLE, UserRowMapper.create());
    }
}
