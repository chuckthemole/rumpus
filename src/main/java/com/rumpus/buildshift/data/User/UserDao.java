package com.rumpus.buildshift.data.User;

import javax.sql.DataSource;

import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;
import com.rumpus.common.Dao.User.jdbc.UserDaoJdbc;

public class UserDao extends UserDaoJdbc<User, UserMetaData> implements IUserDao {

    public UserDao(DataSource dataSource, String usersTable, String userDetailsTable) {
        super(dataSource, usersTable, userDetailsTable, UserRowMapper.create());
    }
}
