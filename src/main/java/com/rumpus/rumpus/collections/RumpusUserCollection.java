package com.rumpus.rumpus.collections;

import java.util.Collections;
import java.util.List;

import com.rumpus.common.CommonUserCollection;
import com.rumpus.common.ModelsCollection;
import com.rumpus.rumpus.models.RumpusUser;

public class RumpusUserCollection extends CommonUserCollection<RumpusUser, List<RumpusUser>> {

    public RumpusUserCollection(List<RumpusUser> users) {
        super(users);
    }
    
    // public void sortByUsername() {
    //     Collections.sort(this.modelsList, (user1, user2) -> {
    //         return user1.getUsername().compareTo(user2.getUsername());
    //     });
    // }

    // public void sortByUserEmail() {
    //     Collections.sort(this.modelsList, (user1, user2) -> {
    //         return user1.getEmail().compareTo(user2.getEmail());
    //     });
    // }
}
