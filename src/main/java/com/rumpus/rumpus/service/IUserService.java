package com.rumpus.rumpus.service;

import com.rumpus.rumpus.models.Auth;
import com.rumpus.rumpus.models.User;
import java.util.List;

public interface IUserService extends IRumpusService<User> {
    User getUserByName(String name);
}