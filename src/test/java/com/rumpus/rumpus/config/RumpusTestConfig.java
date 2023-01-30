package com.rumpus.rumpus.config;

import java.lang.reflect.InvocationTargetException;

import javax.sql.DataSource;    
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.rumpus.common.IApiDB;
import com.rumpus.common.ApiDBJdbc;
import com.rumpus.rumpus.data.AuthDao;
import com.rumpus.rumpus.data.IAuthDao;
import com.rumpus.rumpus.data.IRumpusDaoManager;
import com.rumpus.rumpus.data.IUserDao;
import com.rumpus.rumpus.data.RumpusDaoManager;
import com.rumpus.rumpus.data.UserDao;
import com.rumpus.rumpus.database_loader.RumpusLoader;
import com.rumpus.rumpus.models.Auth;
import com.rumpus.rumpus.models.User;
import com.rumpus.rumpus.service.IUserService;
import com.rumpus.rumpus.service.UserService;
import com.rumpus.rumpus.ui.IRumpusIO;
import com.rumpus.rumpus.ui.RumpusIO;
import com.rumpus.rumpus.ui.RumpusView;

@TestConfiguration
// @ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
@PropertySource("classpath:application.properties")
public class RumpusTestConfig {

    @Autowired
	Environment environment;

    // DB look in database.properties
    private final String URL = "url";
	private final String USER = "username";
	private final String DRIVER = "driver";
	private final String PASSWORD = "password";
    
    @Bean
    public IRumpusIO io() {
        return new RumpusIO();
    }

    @Bean
    public RumpusView view() {
        return new RumpusView(io());
    }

    @Bean
	public DataSource dataSource() {
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
    public IUserService rumpusUserService() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return new UserService(rumpusUserDao());
    }

    @Bean
    @DependsOn({"rumpusUserDao", "rumpusAuthDao"})
    IRumpusDaoManager rumpusDaoManager() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        // List<RumpusDao<?>> list = new ArrayList<>();
        // list.add(rumpusUserDao());
        // list.add(rumpusAuthDao());
        return new RumpusDaoManager(rumpusUserDao(), rumpusAuthDao());
    }

    @Bean
    @ConditionalOnProperty(prefix = "database", name = "loader", havingValue = "true")
    @DependsOn({"rumpusDaoManager"})
    public RumpusLoader rumpusLoader() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return new RumpusLoader(rumpusUserDao(), rumpusAuthDao());
    }

    // @Bean
    // public RumpusRestController controller() throws IllegalAccessException, InvocationTargetException, InstantiationException {
    //     return new RumpusRestController(rumpusUserService(), view());
    // }
}
