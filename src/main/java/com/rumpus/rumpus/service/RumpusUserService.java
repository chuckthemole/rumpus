package com.rumpus.rumpus.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;

/**
 * RumpusUserService
 * 
 * This class is the main service for the RumpusUser user model.
 * <p>
 * This implementation is used to create a new {@link com.rumpus.common.Service.IUserService} instance in {@link com.rumpus.rumpus.config.RumpusUserConfig}.
 */
public class RumpusUserService extends AbstractUserService<RumpusUser, RumpusUserMetaData> implements IRumpusUserService {

    protected static final String NAME = "RumpusUserService";
    private static final String USERNAME_CONDITION = "username = ?";
    private Map<String, RumpusUser> users;

    public RumpusUserService(IRumpusUserDao userDao) {
        super(NAME, userDao);

        // keeping for faster lookup. May need to update if this.users are updated.
        // TODO: Looking at this again. Looks funky. I don't think I should be doing this.
        this.users = new HashMap<>();
        super.dao.getAll().stream().forEach(user -> {
            this.users.put(user.getEmail(), user);
            this.users.put(user.getUsername(), user);
        });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LogBuilder log = new LogBuilder(true, "RumpusUserService::loadUserByUsername\n", "Loading user '", username, "'...");
        log.info();
        return this.get(username).getUserDetails();
    }

    @Override
    public String getKey() {
        return "This is a cool key!";
    }

    // @Override
    // public RumpusUser updateUser(String userToUpdate, RumpusUser updatedUser) {
    //     return super.update(userToUpdate, updatedUser, USERNAME_CONDITION);
    // }

    // @Override
    // public int login(RumpusUser user) {
    //     LOG("UserService::login()");

    //     // check for username or user email
    //     User foundUser = null;
    //     if(this.users.containsKey(user.getUsername())) {
    //         LOG("Found user name...");
    //         foundUser = this.users.get(user.getUsername());
    //     } else if(this.users.containsKey(user.getEmail())) {
    //         LOG("Found user email...");
    //         foundUser = this.users.get(user.getEmail());
    //     } else {
    //         LOG("User credentials are not correct.");
    //         return ERROR;
    //     }
    //     if(foundUser != null && foundUser.getPassword().equals(user.getPassword())) { // check password
    //         LOG("User credentials are correct. Logging in....");
    //         // TODO login
    //     }
    //     return SUCCESS;
    // }

    // @Override
    // public User get(String name) {
    //     return IUserDao.class.cast(super.dao).get(name);
    // }
}
