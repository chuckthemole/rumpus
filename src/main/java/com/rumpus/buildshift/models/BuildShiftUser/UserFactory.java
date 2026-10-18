package com.rumpus.buildshift.models.BuildShiftUser;

import java.util.UUID;

import com.rumpus.common.User.AbstractUserFactory;
import com.rumpus.common.User.CommonUserDetails;

public class UserFactory extends AbstractUserFactory<User, UserMetaData> {

    @Override
    public UserMetaData createMetaData() {
        return new UserMetaData();
    }

    @Override
    public User createUser(
            UUID userId,
            String username,
            String email,
            String encodedPassword,
            UserMetaData userMeta) {

        final CommonUserDetails userDetails = CommonUserDetails.createWithAll(
                userId,
                username,
                encodedPassword,
                true,
                null,
                false,
                false,
                false);
        User user = new User(userId, email, userDetails, userMeta);
        return user;
    }

}
