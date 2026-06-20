package com.rumpus.buildshift.service;

import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;

public interface IUserService
        extends
            com.rumpus.common.Service.User.IUserService<User, UserMetaData> {
}
