package com.rumpus.rumpus.models;

import java.util.Map;

import com.rumpus.common.CommonUser;

public class RumpusUser extends CommonUser<RumpusUser> {

    private static final String NAME = "RumpusUser";

    private RumpusUser() {
        super(NAME);
    }

    public static RumpusUser create(String username, String password, String email) {
        RumpusUser user = new RumpusUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
    public static RumpusUser createFromMap(Map<String, String> userMap) {
        RumpusUser user = new RumpusUser();
        user.setUsername(userMap.containsKey(USERNAME) ? userMap.get(USERNAME) : EMPTY_FIELD);
        user.setUserPassword(userMap.containsKey(PASSWORD) ? userMap.get(PASSWORD) : EMPTY_FIELD);
        user.setEmail(userMap.containsKey(EMAIL) ? userMap.get(EMAIL) : EMPTY_FIELD);
        user.setId(userMap.containsKey(ID) ? userMap.get(ID) : EMPTY_FIELD);
        return user;
    }
    public static RumpusUser createEmptyUser() {
        return new RumpusUser();
    }
}
