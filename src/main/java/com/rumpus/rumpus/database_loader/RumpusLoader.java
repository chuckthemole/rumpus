// package com.rumpus.rumpus.database_loader;

// import java.util.HashMap;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.DependsOn;
// import org.springframework.stereotype.Component;

// import com.rumpus.common.Dao;
// import com.rumpus.common.Rumpus;
// import com.rumpus.common.util.Pair;
// import com.rumpus.rumpus.data.IAuthDao;
// import com.rumpus.rumpus.data.IRumpusDao;
// import com.rumpus.rumpus.data.IRumpusDaoManager;
// import com.rumpus.rumpus.data.IUserDao;
// import com.rumpus.rumpus.data.RumpusDao;
// import com.rumpus.rumpus.data.RumpusDaoManager;
// import com.rumpus.rumpus.models.Auth;
// import com.rumpus.rumpus.models.User;

// public class RumpusLoader implements CommandLineRunner {  

//     // private IRumpusDaoManager<? extends RumpusDao<?>> dao;
// 	private final IUserDao userDao;
//     private final IAuthDao authDao;

// 	public RumpusLoader(IUserDao userDao, IAuthDao authDao) {
//         // this.dao = dao;
//         this.userDao = userDao;
//         this.authDao = authDao;
// 	}

// 	@Override
// 	public void run(String... strings) throws Exception {
//         // IAuthDao authDao = (IAuthDao) dao.get("authDao");
//         Auth auth = Auth.createAdminAuth();
//         authDao.add(auth);
//         // IUserDao userDao = (IUserDao) dao.get("userDao");
//         userDao.add(User.createWithName("Gandalf"));

//         // System.out.println("* * Printing Keys * * ");
//         // dao.getMap().forEach((k, v) -> {
//         //     System.out.println("Key: " + k);
//         // });
// 	}
// }
