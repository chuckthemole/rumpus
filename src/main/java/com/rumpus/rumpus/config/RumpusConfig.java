package com.rumpus.rumpus.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.rumpus.common.IApiDB;
import com.rumpus.common.IO.IRumpusIO;
import com.rumpus.common.IO.RumpusIO;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.common.Session.CommonSessionRepository;
import com.rumpus.common.views.IViewLoader;
import com.google.gson.Gson;
import com.rumpus.common.ApiDBJdbc;
import com.rumpus.common.ApiDBJdbcUsers;
import com.rumpus.common.CommonUser;
import com.rumpus.common.CommonGson;
import com.rumpus.rumpus.data.AuthDao;
import com.rumpus.rumpus.data.IAuthDao;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.data.IUserDao;
import com.rumpus.rumpus.data.RumpusDaoManager;
import com.rumpus.rumpus.data.RumpusUserDao;
import com.rumpus.rumpus.data.UserDao;
import com.rumpus.rumpus.database_loader.RumpusLoader;
import com.rumpus.rumpus.gson.RumpusGson;
import com.rumpus.rumpus.models.Auth;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IRumpusUserService;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.service.RumpusUserService;
import com.rumpus.rumpus.service.UserService;
import com.rumpus.rumpus.ui.RumpusView;
import com.rumpus.rumpus.views.RumpusViewLoader;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusConfig extends Config { // AbstractHttpSessionApplicationInitializer

    @Autowired
	Environment environment;

    // DB look in database.properties
    private final String URL = "url";
	private final String USER = "username";
	private final String DRIVER = "driver";
	private final String PASSWORD = "password";
    

    @Bean
    public RumpusView view() {
        return new RumpusView();
    }

    @Bean
    public IViewLoader viewLoader() {
        return new RumpusViewLoader();
    }

    // * * * Not using right now
    // @Bean
    // public PlatformTransactionManager transactionManager() {
    //     return new DataSourceTransactionManager(dataSource());
    // }

    // @Bean
    // public TransactionTemplate transactionTemplate() {
    //     return new TransactionTemplate(transactionManager());
    // }
    // * * * Not using right now


    // @Bean
    // public Session session() {
    //     return new CommonSession();
    // }

    // @Bean
    // public FindByIndexNameSessionRepository<Session> sessionRepository() {
    //     JdbcIndexedSessionRepository session = new JdbcIndexedSessionRepository(null, null);
    // }

    // @Bean CommonSessionRepository<?> sessionRepository() {
    //     return new JdbcIndexedSessionRepository(null, null);
    // }

    // * * Working in here
    // @Bean
    // public ReactiveSessionRepository<?> reactiveSessionRepository() {
    //     return new ReactiveMapSessionRepository(new ConcurrentHashMap<>());
    // }
    // @Bean
    // public SessionRepository<? extends Session> sessionRepo() {
    //     return new JdbcIndexedSessionRepository(new JdbcTemplate(), new TransactionTemplate());
    // }
    // * * In here

    @Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty(URL));
		driverManagerDataSource.setUsername(environment.getProperty(USER));
		driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
		driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
		return driverManagerDataSource;
	}

    @Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

		jdbcUserDetailsManager.setDataSource(dataSource());
        return jdbcUserDetailsManager;

        // CommonJdbcUserManager manager = new CommonJdbcUserManager(dataSource);
        // manager.manager().setUsersByUsernameQuery(SET_USERS_QUERY);
		// return manager.manager();
	}

    @Bean
    public IRumpusUserDao rumpusUserDao() {
        IRumpusUserDao userDao = new RumpusUserDao();
        // ApiDBJdbcUsers<RumpusUser> userApiDBJdbc = new ApiDBJdbcUsers<>(jdbcUserDetailsManager(), userDao.getTable(), userDao.getMapper());
        // Map<String, String> queries = Map.of(CREATE_USER, SET_USERS_QUERY);
        // userApiDBJdbc.setQueriesFromMap(queries);
        IApiDB<RumpusUser> userApiDB = new ApiDBJdbcUsers<>(jdbcUserDetailsManager(), userDao.getTable(), userDao.getMapper());
        userDao.setApiDB(userApiDB);
        return userDao;
    }
    // @Bean
    // public IUserDao userDao() {
    //     IUserDao userDao = new UserDao();
    //     IApiDB<User> userApiDB = new ApiDBJdbc<>(dataSource(), userDao.getTable(), userDao.getMapper());
    //     userDao.setApiDB(userApiDB);
    //     return userDao;
    // }

    @Bean
    public IAuthDao rumpusAuthDao() {
        IAuthDao authDao = new AuthDao();
        IApiDB<Auth> authApiDB = new ApiDBJdbc<>(dataSource(), authDao.getTable(), authDao.getMapper());
        authDao.setApiDB(authApiDB);
        return authDao;
    }

    @Bean
    public IRumpusUserService rumpusUserService() {
        return new RumpusUserService(rumpusUserDao());
    }
    // @Bean
    // public IUserService userService() {
    //     return new UserService(userDao());
    // }

    // @Bean
    // @DependsOn({"rumpusUserDao", "rumpusAuthDao"})
    // RumpusDaoManager rumpusDaoManager() {
    //     // List<RumpusDao<?>> list = new ArrayList<>();
    //     // list.add(rumpusUserDao());
    //     // list.add(rumpusAuthDao());
    //     return new RumpusDaoManager(userDao(), rumpusAuthDao());
    // }

    // @Bean
    // @DependsOn({"rumpusDaoManager"})
    // @ConditionalOnProperty(prefix = "database", name = "loader", havingValue = "true")
    // public RumpusLoader rumpusLoader() {
    //     return new RumpusLoader(userDao(), rumpusAuthDao());
    // }

    // @Bean
    // public Gson gson() {
    //     RumpusGson gson = new RumpusGson();
    //     return gson.getGson();
    // }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jdbcUserDetailsManager());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
