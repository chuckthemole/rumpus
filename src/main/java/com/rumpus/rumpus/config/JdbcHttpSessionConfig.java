package com.rumpus.rumpus.config;

import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.SessionRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

import com.rumpus.common.ApiDBJdbc;
import com.rumpus.common.IApiDB;
import com.rumpus.common.Session.CommonSession;

@Configuration(proxyBeanMethods = false)
@EnableJdbcHttpSession
public class JdbcHttpSessionConfig extends AbstractHttpSessionApplicationInitializer {

    private static final String TABLE_NAME = "session";
    
    @Autowired
	public DataSource dataSource;

    public JdbcHttpSessionConfig() {
        super(JdbcHttpSessionConfiguration.class);
    }

    @Bean
    @DependsOn({"dataSource"})
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    // Not using rn
    // @Bean
    // @DependsOn({"transactionManager"})
    // public TransactionTemplate transactionTemplate(PlatformTransactionManager manager) {
    //     return new TransactionTemplate(manager);
    // }
 }
