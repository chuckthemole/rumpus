package com.rumpus.rumpus.service;

import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;

/**
 * RumpusUserService
 * 
 * This class is the main service for the RumpusUser user model.
 * <p>
 * This implementation is used to create a new {@link com.rumpus.common.Service.IUserService} instance in {@link com.rumpus.rumpus.config.RumpusUserConfig}.
 */
public class RumpusUserService extends AbstractUserService<RumpusUser, RumpusUserMetaData> implements IRumpusUserService {

    protected static final String NAME = "RumpusUserService";

    public RumpusUserService(IRumpusUserDao userDao) {
        super(NAME, userDao);
    }

    @Override
    public String getKey() {
        return "This is a cool key!";
    }
}
