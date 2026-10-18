package com.rumpus.rumpus.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumpus.common.Service.User.AbstractUserServiceJpa;
import com.rumpus.common.User.UserAuthority;
import com.rumpus.rumpus.data.User.IRumpusUserDaoJpa;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

public class RumpusUserServiceJpa extends AbstractUserServiceJpa<RumpusUser, RumpusUserMetaData> {

    @Autowired
    public RumpusUserServiceJpa(IRumpusUserDaoJpa userDaoJpa) {
        super(userDaoJpa);
    }

    @Override
    public RumpusUser createUserWithUsername(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'createUserWithUsername'");
    }

    @Override
    public UserAuthority getUserRole(UUID userId, String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserRole'");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
