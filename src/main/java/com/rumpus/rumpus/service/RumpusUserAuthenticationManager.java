package com.rumpus.rumpus.service;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.User.AbstractCommonAuthManager;
import com.rumpus.rumpus.models.RumpusUser;

/**
 * TODO: is this being used? if not, delete it
 * Look more into this.
 */
public class RumpusUserAuthenticationManager extends AbstractCommonAuthManager<RumpusUser> {

    public static final String NAME = "RumpusUserAuthenticationManager";

    public RumpusUserAuthenticationManager(IDao<RumpusUser> dao) {
        super(NAME, dao);
    }

    @Override
    public boolean userIsAuthenticated(String name, String password) {
        LOG.info("RumpusUserAuthenticationManager::userIsAuthenticated");
        final UserDetails details = loadUserByUsername(name);
        if(details == null) {
            return false;
        }
        if(!details.getUsername().equals(name)) { // prolly don't need to do this since loading by username
            LOG.info("User name is not equal");
            return false;
        }
        if(!details.getPassword().equals(password)) {
            LOG.info("Password is not equal");
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.dao.get(username).getUserDetails();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities(String name) {
        return this.dao.get(name).getUserDetails().getAuthorities();
    }
    
}
