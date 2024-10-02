package com.rumpus.rumpus.collections;

import java.util.List;

import com.rumpus.common.User.AbstractCommonUserCollection;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;

public class RumpusUserCollection extends AbstractCommonUserCollection<RumpusUser, List<RumpusUser>> {

    public RumpusUserCollection(List<RumpusUser> users) {
        super(users);
    }
}
