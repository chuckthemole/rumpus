package com.rumpus.shared.config;

import com.rumpus.common.Config.User.AuthRolesConfig;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.rumpus.common.Config.Database.DatabaseConfig;
import com.rumpus.common.Dao.User.IUserAuthorityDao;
import com.rumpus.common.Dao.User.jdbc.UserAuthorityDaoJdbc;
import com.rumpus.common.Dao.User.jdbc.UserAuthorityRowMapper;

@Configuration
@Import({
        DatabaseConfig.class,
        AuthRolesConfig.class
})
public class UserConfig {

    public static final String BEAN_USER_AUTHORITY_DAO = "userAuthorityDao";

    @Bean
    @DependsOn({DatabaseConfig.BEAN_DATA_SOURCE})
    public IUserAuthorityDao userAuthorityDao(DataSource dataSource) {

        final String authoritiesTable = "authorities"; // TODO - make this configurable
        final String usersTable = "users"; // TODO - make this configurable
        // TODO - make this configurable
        final String authoritiesDefinitionTable = "authority";
        final UserAuthorityRowMapper authorityRowMapper = new UserAuthorityRowMapper();

        IUserAuthorityDao userAuthorityDao = new UserAuthorityDaoJdbc(
                dataSource,
                authoritiesTable,
                usersTable,
                authoritiesDefinitionTable,
                authorityRowMapper);

        return userAuthorityDao;
    }
}
