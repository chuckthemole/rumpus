package com.rumpus.rumpus.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumpus.common.Service.User.AbstractUserServiceJpa;
import com.rumpus.rumpus.data.User.IRumpusUserDaoJpa;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserFactory;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

public class RumpusUserServiceJpa extends AbstractUserServiceJpa<RumpusUser, RumpusUserMetaData> {

    @Autowired
    public RumpusUserServiceJpa(IRumpusUserDaoJpa userDaoJpa) {
        super(userDaoJpa);
    }

    @Override
    public RumpusUser createUserWithUsername(String username) {
        RumpusUserFactory userFactory = new RumpusUserFactory();
        RumpusUser user = userFactory.createEmpty();
        user.setUsername(username);
        return user;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
