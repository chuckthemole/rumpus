package com.rumpus.rumpus.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.transaction.PlatformTransactionManager;

@TestConfiguration
@EnableJdbcHttpSession
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "properties")
@org.springframework.context.annotation.PropertySource(value = "classpath:properties.yml", factory = com.rumpus.common.Config.Properties.YamlPropertySourceFactory.class)
public class JdbcHttpSessionTestConfig extends AbstractHttpSessionApplicationInitializer {
    private static final String TABLE_NAME = "session";
    
    @Autowired
	public DataSource dataSource;

    public JdbcHttpSessionTestConfig() {
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
