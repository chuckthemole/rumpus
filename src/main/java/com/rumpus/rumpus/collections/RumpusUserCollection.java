package com.rumpus.rumpus.collections;

import java.util.List;

import com.rumpus.common.User.CommonUserCollection;
import com.rumpus.rumpus.models.RumpusUser;

public class RumpusUserCollection extends CommonUserCollection<RumpusUser, List<RumpusUser>> {

    public RumpusUserCollection(List<RumpusUser> users) {
        super(users);
    }
}
