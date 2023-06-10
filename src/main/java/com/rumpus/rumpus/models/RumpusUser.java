package com.rumpus.rumpus.models;

import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.CommonUser;

public class RumpusUser extends CommonUser<RumpusUser> {

    private static final String NAME = "RumpusUser";

    public RumpusUser() {
        super(NAME);
    }
    public RumpusUser(Map<String, String> attributes) {
        super(NAME, attributes);
    }

    public static RumpusUser create(Map<String, String> attributes) {return new RumpusUser(attributes);}
    public static RumpusUser createWithName(String name) {
        Map<String, String> map = new HashMap<>();
        map.put(USERNAME, name);
        return RumpusUser.create(map);
    }
}
