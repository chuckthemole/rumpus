package com.rumpus.buildshift.data.User;

import javax.sql.DataSource;

import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;
import com.rumpus.common.Dao.User.jdbc.UserDaoJdbc;

public class UserDao extends UserDaoJdbc<User, UserMetaData> implements IUserDao {

    private static final String TABLE = "user";

    public UserDao(DataSource dataSource) {
        super(dataSource, TABLE, UserRowMapper.create());
    }
}
