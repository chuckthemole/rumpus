package com.rumpus.buildshift.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumpus.buildshift.data.User.IUserDao;
import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserFactory;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;
import com.rumpus.common.Service.User.AbstractUserService;
import com.rumpus.common.Service.User.UserSecurityService;

public class UserService extends AbstractUserService<User, UserMetaData> implements IUserService {

    public UserService(
            IUserDao userDao,
            UserSecurityService userSecurityService,
            UserFactory userFactory,
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
