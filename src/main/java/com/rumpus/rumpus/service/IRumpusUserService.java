package com.rumpus.rumpus.service;

import com.rumpus.common.Service.IUserService;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

public interface IRumpusUserService extends IUserService<RumpusUser, RumpusUserMetaData> {
    // public RumpusUser updateUser(String userToUpdate, RumpusUser updatedUser);
}
