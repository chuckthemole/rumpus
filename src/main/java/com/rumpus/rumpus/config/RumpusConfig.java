package com.rumpus.rumpus.config;

import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
import com.rumpus.common.ApiDBJdbc;
import com.rumpus.rumpus.data.AuthDao;
import com.rumpus.rumpus.data.IAuthDao;
import com.rumpus.rumpus.data.IUserDao;
import com.rumpus.rumpus.data.RumpusDaoManager;
import com.rumpus.rumpus.data.UserDao;
import com.rumpus.rumpus.database_loader.RumpusLoader;
import com.rumpus.rumpus.models.Auth;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.service.UserService;
import com.rumpus.rumpus.ui.RumpusView;
import com.rumpus.rumpus.views.IViewLoader;
import com.rumpus.rumpus.views.ViewLoader;

@Configuration
// @EnableSpringWebSession
// @EnableJdbcHttpSession
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusConfig { // AbstractHttpSessionApplicationInitializer

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
        return new ViewLoader();
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
    public IUserDao rumpusUserDao() {
        IUserDao userDao = new UserDao();
        IApiDB<User> userApiDB = new ApiDBJdbc<>(dataSource(), userDao.getTable(), userDao.getMapper());
        userDao.setApiDB(userApiDB);
        return userDao;
    }

    @Bean
    public IAuthDao rumpusAuthDao() {
        IAuthDao authDao = new AuthDao();
        IApiDB<Auth> authApiDB = new ApiDBJdbc<>(dataSource(), authDao.getTable(), authDao.getMapper());
        authDao.setApiDB(authApiDB);
        return authDao;
    }

    @Bean
    public IUserService rumpusUserService() {
        return new UserService(rumpusUserDao());
    }

    @Bean
    @DependsOn({"rumpusUserDao", "rumpusAuthDao"})
    RumpusDaoManager rumpusDaoManager() {
        // List<RumpusDao<?>> list = new ArrayList<>();
        // list.add(rumpusUserDao());
        // list.add(rumpusAuthDao());
        return new RumpusDaoManager(rumpusUserDao(), rumpusAuthDao());
    }

    @Bean
    @DependsOn({"rumpusDaoManager"})
    @ConditionalOnProperty(prefix = "database", name = "loader", havingValue = "true")
    public RumpusLoader rumpusLoader() {
        return new RumpusLoader(rumpusUserDao(), rumpusAuthDao());
    }
}
