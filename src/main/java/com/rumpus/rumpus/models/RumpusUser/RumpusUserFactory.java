package com.rumpus.rumpus.models.RumpusUser;

import java.util.UUID;

import com.rumpus.common.User.AbstractUserFactory;
import com.rumpus.common.User.CommonUserDetails;

public class RumpusUserFactory extends AbstractUserFactory<RumpusUser, RumpusUserMetaData> {

    @Override
    public RumpusUserMetaData createMetaData() {
        return new RumpusUserMetaData();
    }

    @Override
    public RumpusUser createUser(
            UUID userId,
            String username,
            String email,
            String encodedPassword,
            RumpusUserMetaData metaData) {

        final CommonUserDetails userDetails = CommonUserDetails.createWithAll(
                userId,
                username,
                encodedPassword,
                true,
                null,
                false,
                false,
                false);

        return new RumpusUser(userId, email, userDetails, metaData);
    }
}
