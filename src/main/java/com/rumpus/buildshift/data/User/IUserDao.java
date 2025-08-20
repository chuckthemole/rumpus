package com.rumpus.buildshift.data.User;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;

@Repository
@Profile("database")
public interface IUserDao extends com.rumpus.common.Dao.IUserDao<User, UserMetaData> {
}
