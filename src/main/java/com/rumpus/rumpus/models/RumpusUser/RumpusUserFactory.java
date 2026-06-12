package com.rumpus.rumpus.models.RumpusUser;

import com.rumpus.common.User.AbstractUserFactory;

public class RumpusUserFactory extends AbstractUserFactory<RumpusUser, RumpusUserMetaData> {

    @Override
    public RumpusUser createEmpty() {
        return new RumpusUser();
    }

    @Override
    public RumpusUserMetaData createMetaData() {
        return new RumpusUserMetaData();
    }
}
