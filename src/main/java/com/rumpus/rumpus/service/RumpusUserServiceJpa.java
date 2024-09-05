package com.rumpus.rumpus.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumpus.common.Service.AbstractUserServiceJpa;
import com.rumpus.rumpus.data.IRumpusUserDaoJpa;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;

public class RumpusUserServiceJpa extends AbstractUserServiceJpa<RumpusUser, RumpusUserMetaData> {

    protected static final String NAME = "RumpusUserServiceJpa";

    @Autowired
    public RumpusUserServiceJpa(IRumpusUserDaoJpa userDaoJpa) {
        super(NAME, userDaoJpa);
    }

    @Override
    public RumpusUser createUserWithUsername(String username) {
        RumpusUser user = RumpusUser.createEmptyUser();
        user.setUsername(username);
        return user;
    }
}
