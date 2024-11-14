package com.rumpus.rumpus.service;

import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

/**
 * RumpusUserService
 * 
 * This class is the main service for the RumpusUser user model.
 * <p>
 * This implementation is used to create a new {@link com.rumpus.common.Service.IUserService} instance in {@link com.rumpus.rumpus.config.RumpusUserConfig}.
 */
public class RumpusUserService extends AbstractUserService<RumpusUser, RumpusUserMetaData> implements IRumpusUserService {

    public RumpusUserService(IRumpusUserDao userDao) {
        super(userDao);
    }

    @Override
    public String getKey() {
        return "This is a cool key!";
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
