package com.rumpus.buildshift.database_loader;

import org.springframework.boot.CommandLineRunner;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.FileIO.FileProcessor;
import com.rumpus.common.FileIO.IFileIO;
import com.rumpus.common.FileIO.JsonIO;
import com.rumpus.buildshift.data.User.IUserDao;
import com.rumpus.buildshift.models.BuildShiftUser.User;

public class BuildShiftLoader implements CommandLineRunner {

    private final IFileIO fileReader = JsonIO.create();
    private FileProcessor fileProcessor;

    private User[] users;
    private static final String JSON_USERS_FILE = "src/main/java/com/rumpus/buildshift/database_loader/buildshift_users.json";
	private final IUserDao userDao; // TODO: maybe should go through service layer...

	public BuildShiftLoader(IUserDao userDao) {
        this.userDao = userDao;
        this.fileProcessor = new FileProcessor(fileReader);
	}

	@Override
	public void run(String... strings) throws Exception {
        LogBuilder.logBuilderFromStringArgs("RumpusLoader::run()").toString();

        // TODO: make this a service layer call
        this.users = this.fileProcessor.<User>processFile(
            JSON_USERS_FILE,
            User[].class
        ).get();

        LogBuilder log = LogBuilder.logBuilderFromStringArgs("\nPopulating buildshift users...");
        for(User user : users) {
            if(userDao.add(user) != null) {
                log.append("\n  Success adding user: ", user.getUsername());
            } else {
                log.append("\n  ERROR adding user: ", user.toString());
            }
        }
        ICommon.LOG(BuildShiftLoader.class, log.toString());
	}
}
