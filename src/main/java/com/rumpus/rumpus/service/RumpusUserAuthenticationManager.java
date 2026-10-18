package com.rumpus.rumpus.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.User.IAuthorityDao;
import com.rumpus.common.Dao.User.IUserAuthorityDao;
import com.rumpus.common.Dao.User.IUserDao;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Service.User.UserSecurityService;
import com.rumpus.common.User.AbstractCommonAuthManager;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

public class RumpusUserAuthenticationManager
        extends
            AbstractCommonAuthManager<RumpusUser, RumpusUserMetaData> {

    public RumpusUserAuthenticationManager(
            IUserDao<RumpusUser, RumpusUserMetaData> rumpusUserDao,
            IUserAuthorityDao rumpusUserAuthorityDao,
            IAuthorityDao rumpusAuthorityDao,
            UserSecurityService userSecurityService,
            PasswordEncoder passwordEncoder) {
        super(
                rumpusUserDao,
                rumpusUserAuthorityDao,
                rumpusAuthorityDao,
                userSecurityService,
                passwordEncoder);
    }

    @Override
    public boolean userIsAuthenticated(String name, String password) {
        LOG_THIS("userIsAuthenticated: looking up user");

        final UserDetails details = this.loadUserByUsername(name);

        LOG_THIS("User lookup completed");

        if (details == null) {
            LOG_THIS("User details are null");
            return false;
        }

        LOG_THIS("Username matches: " + details.getUsername().equals(name));
        LOG_THIS("Account enabled: " + details.isEnabled());
        LOG_THIS("Stored password present: "
                + (details.getPassword() != null
                        && !details.getPassword().isBlank()));

        final boolean passwordMatches = getPasswordEncoder().matches(password,
                details.getPassword());

        LOG_THIS("Password encoder matches: " + passwordMatches);

        return details.getUsername().equals(name)
                && details.isEnabled()
                && passwordMatches;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG_THIS("loadUserByUsername");
        return this.userSecurityService.loadUserByUsername(username);
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(RumpusUserAuthenticationManager.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(RumpusUserAuthenticationManager.class, level, args);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

}
