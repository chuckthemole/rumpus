// package com.rumpus.rumpus.config;

// import javax.sql.DataSource;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.session.SessionRepository;
// import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

// import com.rumpus.common.ApiDBJdbc;
// import com.rumpus.common.IApiDB;
// import com.rumpus.common.Session.CommonSession;
// import com.rumpus.rumpus.data.RumpusSessionDao;

// @Configuration(proxyBeanMethods = false)
// public class HttpSessionConfig extends SpringHttpSessionConfiguration {

//     private static final String TABLE_NAME = "session";

//     // @Bean
//     // @DependsOn({"dataSource"})
//     // public PlatformTransactionManager transactionManager(DataSource dataSource) {
//     //     return new DataSourceTransactionManager(dataSource);
//     // }

//     // Not using rn
//     // @Bean
//     // @DependsOn({"transactionManager"})
//     // public TransactionTemplate transactionTemplate(PlatformTransactionManager manager) {
//     //     return new TransactionTemplate(manager);
//     // }

//     @Bean
//     public SessionRepository<CommonSession> rumpusSessionRepository(DataSource dataSource) {
//         RumpusSessionDao sessionDao = new RumpusSessionDao();
//         IApiDB<CommonSession> authSessionApi = new ApiDBJdbc<>(dataSource, sessionDao.getTable(), sessionDao.getMapper());
//         sessionDao.setApiDB(authSessionApi);
//         return sessionDao;
//     }
// }
