package com.rumpus.buildshift.service;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.IUserDao;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.User.AbstractCommonAuthManager;
import com.rumpus.buildshift.models.BuildShiftUser.User;
import com.rumpus.buildshift.models.BuildShiftUser.UserMetaData;

/**
 * TODO: is this being used? if not, delete it
 * Look more into this.
 */
public class UserAuthenticationManager extends AbstractCommonAuthManager<User, UserMetaData> {

    public UserAuthenticationManager(IUserDao<User, UserMetaData> rumpusUserDao) {
        super(rumpusUserDao);
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
        ICommon.LOG(UserAuthenticationManager.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(UserAuthenticationManager.class, level, args);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
    
}
