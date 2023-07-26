package com.rumpus.rumpus.database_loader;

import org.springframework.boot.CommandLineRunner;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.util.ReadJson;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser;

public class RumpusLoader implements CommandLineRunner {  

    private RumpusUser[] users;
    private static final String JSON_USERS_FILE = "src/main/java/com/rumpus/rumpus/database_loader/rumpus_users.json";
	private final IRumpusUserDao userDao;

	public RumpusLoader(IRumpusUserDao userDao) {
        this.userDao = userDao;
	}

	@Override
	public void run(String... strings) throws Exception {
        LogBuilder.logBuilderFromStringArgs("RumpusLoader::run()").info();
        ReadJson<RumpusUser> json = new ReadJson<>(JSON_USERS_FILE, new com.google.gson.reflect.TypeToken<RumpusUser[]>(){}.getType());
        users = json.readModelsFromFile();
        LogBuilder log = LogBuilder.logBuilderFromStringArgs("\nPopulating rumpus users...");
        for(RumpusUser user : users) {
            if(userDao.add(user) != null) {
                log.append("\n  Success adding user: ", user.getUsername());
            } else {
                log.append("\n  ERROR adding user: ", user.toString());
            }
        }
        log.info();

	}
}
