package com.rumpus.rumpus.database_loader;

import org.springframework.boot.CommandLineRunner;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.FileIO.FileProcessor;
import com.rumpus.common.FileIO.IFileIO;
import com.rumpus.common.FileIO.JsonIO;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;

public class RumpusLoader implements CommandLineRunner {

    private final IFileIO fileReader = JsonIO.create();
    private FileProcessor fileProcessor;

    private RumpusUser[] users;
    private static final String JSON_USERS_FILE = "src/main/java/com/rumpus/rumpus/database_loader/rumpus_users.json";
	private final IRumpusUserDao userDao; // TODO: maybe should go through service layer...

	public RumpusLoader(IRumpusUserDao userDao) {
        this.userDao = userDao;
        this.fileProcessor = new FileProcessor(fileReader);
	}

	@Override
	public void run(String... strings) throws Exception {
        LogBuilder.logBuilderFromStringArgs("RumpusLoader::run()").toString();

        // TODO: make this a service layer call
        this.users = this.fileProcessor.<RumpusUser>processFile(
            JSON_USERS_FILE,
            RumpusUser[].class
        ).get();

        LogBuilder log = LogBuilder.logBuilderFromStringArgs("\nPopulating rumpus users...");
        for(RumpusUser user : users) {
            if(userDao.add(user) != null) {
                log.append("\n  Success adding user: ", user.getUsername());
            } else {
                log.append("\n  ERROR adding user: ", user.toString());
            }
        }
        ICommon.LOG(RumpusLoader.class, log.toString());
	}
}
