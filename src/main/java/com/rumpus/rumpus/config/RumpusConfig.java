package com.rumpus.rumpus.config;

import java.lang.reflect.InvocationTargetException;

import javax.sql.DataSource;    
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rumpus.rumpus.controller.RumpusRestController;
import com.rumpus.rumpus.data.AuthDao;
import com.rumpus.rumpus.data.RumpusDaoManager;
import com.rumpus.rumpus.data.UserDao;
import com.rumpus.rumpus.database_loader.RumpusLoader;
import com.rumpus.rumpus.service.UserService;
import com.rumpus.rumpus.ui.RumpusIO;
import com.rumpus.rumpus.ui.RumpusView;

@Configuration
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusConfig {

    @Autowired
	Environment environment;

    // List of DAO's
    // @Autowired
    // IUserDao userDao;
    // @Autowired
    // IAuthDao authDao;

    // DB look in database.properties
    private final String URL = "url";
	private final String USER = "username";
	private final String DRIVER = "driver";
	private final String PASSWORD = "password";
    
    @Bean
    public RumpusIO io() {
        return new RumpusIO();
    }

    @Bean
    public RumpusView view() {
        return new RumpusView(io());
    }

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
    JdbcTemplate jdbcTemplate() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public UserDao rumpusUserDao() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        UserDao userDao = new UserDao();
        userDao.setJdbcTemplate(jdbcTemplate());
        return userDao;
    }
    @Bean
    public AuthDao rumpusAuthDao() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        AuthDao authDao = new AuthDao();
        authDao.setJdbcTemplate(jdbcTemplate());
        return authDao;
    }

    @Bean
    public UserService rumpusUserService() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return new UserService(rumpusUserDao());
    }

    @Bean
    @DependsOn({"rumpusUserDao", "rumpusAuthDao"})
    RumpusDaoManager rumpusDaoManager() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        // List<RumpusDao<?>> list = new ArrayList<>();
        // list.add(rumpusUserDao());
        // list.add(rumpusAuthDao());
        return new RumpusDaoManager();
    }

    @Bean
    @DependsOn({"rumpusDaoManager"})
    @ConditionalOnProperty(prefix = "database", name = "loader", havingValue = "true")
    public RumpusLoader rumpusLoader() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return new RumpusLoader(rumpusUserDao(), rumpusAuthDao());
    }

    // @Bean
    // public RumpusRestController controller() throws IllegalAccessException, InvocationTargetException, InstantiationException {
    //     return new RumpusRestController(rumpusUserService(), view());
    // }
}
