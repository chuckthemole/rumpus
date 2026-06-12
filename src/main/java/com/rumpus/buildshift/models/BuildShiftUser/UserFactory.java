package com.rumpus.buildshift.models.BuildShiftUser;

import com.rumpus.common.User.AbstractUserFactory;

public class UserFactory extends AbstractUserFactory<User, UserMetaData> {

    @Override
    public User createEmpty() {
        return new User();
    }

    @Override
    public UserMetaData createMetaData() {
        return new UserMetaData();
    }

}
