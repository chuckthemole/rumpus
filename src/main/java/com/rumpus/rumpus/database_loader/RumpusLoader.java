package com.rumpus.rumpus.database_loader;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.FileIO.FileProcessor;
import com.rumpus.common.FileIO.IFileIO;
import com.rumpus.common.FileIO.JsonIO;
import com.rumpus.rumpus.data.IRumpusUserDao;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;

/**
 * RumpusLoader seeds the database with initial RumpusUser data.
 * <p>
 * This runner is only active in the "dev" profile to avoid
 * accidentally populating production data.
 */
@Component
@Profile("dev")
public class RumpusLoader implements CommandLineRunner {

    // File I/O utilities for reading JSON data
    private final IFileIO fileReader = JsonIO.create();
    private final FileProcessor fileProcessor;

    // DAO for persisting RumpusUser objects
    private final IRumpusUserDao userDao;

    // Path to the JSON file containing initial users
    private static final String JSON_USERS_FILE = "src/main/java/com/rumpus/rumpus/database_loader/rumpus_users.json";

    /**
     * Constructor
     *
     * @param userDao DAO for RumpusUser persistence
     */
    public RumpusLoader(IRumpusUserDao userDao) {
        this.userDao = userDao;
        this.fileProcessor = new FileProcessor(fileReader);
    }

    /**
     * CommandLineRunner entry point.
     * Executes automatically on application startup if the component is active.
     *
     * @param args Command-line arguments
     */
    @Override
    public void run(String... args) throws Exception {
        ICommon.LOG(RumpusLoader.class, "RumpusLoader::run() - running in dev profile");

        // Try to read users from JSON file
        Optional<RumpusUser[]> usersOpt = this.fileProcessor.<RumpusUser>processFile(
                JSON_USERS_FILE,
                RumpusUser[].class);

        if (usersOpt.isPresent()) {
            RumpusUser[] users = usersOpt.get();

            LogBuilder log = LogBuilder.logBuilderFromStringArgs("\nPopulating Rumpus users...");
            for (RumpusUser user : users) {
                if (userDao.add(user) != null) {
                    log.append("\n  Success adding user: ", user.getUsername());
                } else {
                    log.append("\n  ERROR adding user: ", user.toString());
                }
            }

            ICommon.LOG(RumpusLoader.class, log.toString());
        } else {
            ICommon.LOG(RumpusLoader.class, "RumpusLoader::run() - no users found, skipping population");
        }
    }

}
