package com.rumpus.rumpus.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumpus.common.Service.User.AbstractUserService;
import com.rumpus.common.Service.User.UserSecurityService;
import com.rumpus.rumpus.data.User.IRumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserFactory;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

/**
 * RumpusUserService
 *
 * This class is the main service for the RumpusUser user model.
 * <p>
 * This implementation is used to create a new
 * {@link com.rumpus.common.Service.User.IUserService} instance in
 * {@link com.rumpus.rumpus.config.RumpusUserConfig}.
 */
public class RumpusUserService extends AbstractUserService<RumpusUser, RumpusUserMetaData>
        implements
            IRumpusUserService {

    public RumpusUserService(
            IRumpusUserDao userDao,
            UserSecurityService userSecurityService,
            RumpusUserFactory userFactory,
            PasswordEncoder passwordEncoder) {
        super(userDao, userSecurityService, userFactory, passwordEncoder);
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
