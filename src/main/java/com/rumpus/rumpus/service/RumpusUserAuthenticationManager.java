package com.rumpus.rumpus.service;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.IUserDao;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.User.AbstractCommonAuthManager;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;

/**
 * TODO: is this being used? if not, delete it
 * Look more into this.
 */
public class RumpusUserAuthenticationManager extends AbstractCommonAuthManager<RumpusUser, RumpusUserMetaData> {

    public static final String NAME = "RumpusUserAuthenticationManager";

    public RumpusUserAuthenticationManager(IUserDao<RumpusUser, RumpusUserMetaData> rumpusUserDao) {
        super(NAME, rumpusUserDao);
    }

    @Override
    public boolean userIsAuthenticated(String name, String password) {
        LOG_THIS("userIsAuthenticated");
        final UserDetails details = loadUserByUsername(name);
        if(details == null) {
            return false;
        }
        if(!details.getUsername().equals(name)) { // prolly don't need to do this since loading by username
            LOG_THIS("User name is not equal");
            return false;
        }
        if(!details.getPassword().equals(password)) {
            LOG_THIS("Password is not equal");
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG_THIS("loadUserByUsername");
        return this.userDao.loadUserByUsername(username);
    }

    @Override
    public Set<GrantedAuthority> getAuthorities(String username) {
        LOG_THIS("getAuthorities");
        final Set<GrantedAuthority> authorities = new HashSet<>(this.loadUserByUsername(username).getAuthorities());
        return authorities;
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(RumpusUserAuthenticationManager.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(RumpusUserAuthenticationManager.class, level, args);
    }
    
}
