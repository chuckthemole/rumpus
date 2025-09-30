package com.rumpus.buildshift.database_loader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.FileIO.FileProcessor;
import com.rumpus.common.FileIO.IFileIO;
import com.rumpus.common.FileIO.JsonIO;
import com.rumpus.buildshift.data.User.IUserDao;
import com.rumpus.buildshift.models.BuildShiftUser.User;

import java.util.Optional;

/**
 * BuildShiftLoader seeds the database with initial BuildShiftUser data.
 *
 * <p>
 * This loader is only active in the "DEV" profile to prevent accidental
 * data insertion in production environments.
 */
@Component
@Profile("DEV") // Only active when the 'DEV' profile is enabled
public class BuildShiftLoader implements CommandLineRunner {

    // JSON file reader utility
    private final IFileIO fileReader = JsonIO.create();
    private final FileProcessor fileProcessor;

    // DAO for persisting User objects
    private final IUserDao userDao;

    // Path to JSON file containing initial users
    private static final String JSON_USERS_FILE = "src/main/java/com/rumpus/buildshift/database_loader/buildshift_users.json";

    /**
     * Constructor
     *
     * @param userDao DAO for User persistence
     */
    public BuildShiftLoader(IUserDao userDao) {
        this.userDao = userDao;
        this.fileProcessor = new FileProcessor(fileReader);
    }

    /**
     * CommandLineRunner entry point. Executes automatically at application startup
     * if this bean is active.
     *
     * @param args Command-line arguments
     */
    @Override
    public void run(String... args) throws Exception {
        // Log that the loader is executing and which profile is active
        ICommon.LOG(BuildShiftLoader.class, "BuildShiftLoader::run() - executing in DEV profile");

        // Attempt to read users from the JSON file safely
        Optional<User[]> usersOpt = this.fileProcessor.<User>processFile(JSON_USERS_FILE, User[].class);

        if (usersOpt.isPresent()) {
            User[] users = usersOpt.get(); // Safe to get because Optional is present

            // Initialize a log builder to track successes and errors
            LogBuilder log = LogBuilder.logBuilderFromStringArgs("\nPopulating BuildShift users...");

            for (User user : users) {
                try {
                    // Persist user via DAO
                    if (userDao.add(user) != null) {
                        log.append("\n  Success adding user: ", user.getUsername());
                    } else {
                        log.append("\n  ERROR adding user: ", user.toString());
                    }
                } catch (Exception e) {
                    // Catch individual failures so one bad record doesn't stop the whole process
                    log.append("\n  EXCEPTION adding user: ", user.toString(), " - ", e.getMessage());
                }
            }

            // Log the final results
            ICommon.LOG(BuildShiftLoader.class, log.toString());
        } else {
            // No users found; skip population safely
            ICommon.LOG(BuildShiftLoader.class,
                    "BuildShiftLoader::run() - no users found in JSON file, skipping population");
        }
    }
}