package com.rumpus.rumpus.data;

import com.rumpus.rumpus.models.Auth;
import com.rumpus.rumpus.models.User;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("database")
public interface IUserDao extends IRumpusDao<User> {
    User get(String name);
}
