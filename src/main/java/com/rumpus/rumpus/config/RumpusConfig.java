package com.rumpus.rumpus.config;

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

import com.rumpus.common.IApiDB;
import com.rumpus.common.IO.IRumpusIO;
import com.rumpus.common.IO.RumpusIO;
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

@Configuration
@ComponentScan("com.rumpus.rumpus")
@PropertySource("classpath:database.properties")
public class RumpusConfig {

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
