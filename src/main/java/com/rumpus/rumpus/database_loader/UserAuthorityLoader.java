package com.rumpus.rumpus.database_loader;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Config.User.UserRolesListProperties;
import com.rumpus.common.ICommon;
import com.rumpus.common.Service.User.IAuthorityService;
import com.rumpus.common.User.CommonAuthority;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.service.IRumpusUserService;

@Component
@Profile("dev")
@Order(3)
public class UserAuthorityLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthorityLoader.class);

    private final IAuthorityService authorityService;
    private final IRumpusUserService userService;
    private final UserRolesListProperties userRolesProperties;

    private static final UUID CREATOR_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public UserAuthorityLoader(
            IAuthorityService authorityService,
            IRumpusUserService userService,
            UserRolesListProperties userRolesProperties) {
        this.authorityService = authorityService;
        this.userService = userService;
        this.userRolesProperties = userRolesProperties;
    }

    @Override
    public void run(String... args) {
        ICommon.LOG(
                UserAuthorityLoader.class,
                "UserAuthorityLoader::run() - loading user-authority assignments");

        logger.info("[UAL-START] Starting user-authority loader");

        if (userRolesProperties.getUserRoles() == null
                || userRolesProperties.getUserRoles().isEmpty()) {
            logger.info("[UAL-CONFIG] No assignments configured");
            return;
        }

        Set<String> assignments = new LinkedHashSet<>();

        for (String entry : userRolesProperties.getUserRoles()) {
            if (entry != null && !entry.isBlank()) {
                assignments.add(entry.trim());
            }
        }

        if (assignments.isEmpty()) {
            logger.info("[UAL-CONFIG] No valid assignments configured");
            return;
        }

        logger.info("[UAL-CONFIG] Processing {} unique assignments",
                assignments.size());

        LogBuilder log = LogBuilder.logBuilderFromStringArgs(
                "\nLoading user-authority assignments...");

        for (String assignment : assignments) {
            String currentStep = "parse assignment";

            try {
                logger.info("[UAL-ASSIGNMENT] Starting: {}", assignment);

                String[] parts = assignment.split(":", 2);

                if (parts.length != 2
                        || parts[0].isBlank()
                        || parts[1].isBlank()) {
                    logger.warn("[UAL-VALIDATION] Invalid assignment: {}",
                            assignment);
                    log.append(
                            "\n  Invalid assignment (expected username:AUTHORITY): ",
                            assignment);
                    continue;
                }

                String username = parts[0].trim();
                String authorityName = parts[1].trim();

                // Step 1: Resolve user.
                currentStep = "userService.getByUsername";
                logger.debug("[UAL-1] Resolving user '{}'", username);

                final RumpusUser user = userService.getByUsername(username);

                logger.debug(
                        "[UAL-1] getByUsername returned {} for '{}'",
                        user == null ? "null" : user.getClass().getName(),
                        username);

                if (user == null) {
                    logger.warn("[UAL-1] User '{}' does not exist", username);
                    log.append(
                            "\n  ERROR: user does not exist: ",
                            username,
                            " (authority ",
                            authorityName,
                            ")");
                    continue;
                }

                currentStep = "user.getId";
                final UUID userId = user.getId();

                logger.debug("[UAL-1] User '{}' has ID: {}",
                        username, userId);

                if (userId == null) {
                    logger.error("[UAL-1] User '{}' has no ID", username);
                    log.append("\n  ERROR: user has no ID: ", username);
                    continue;
                }

                // Step 2: Resolve authority definition.
                currentStep = "authorityService.getByName";
                logger.debug(
                        "[UAL-2] Resolving authority '{}' for user '{}'",
                        authorityName, username);

                final CommonAuthority authority = authorityService.getByName(authorityName);

                logger.debug(
                        "[UAL-2] getByName returned {} for authority '{}'",
                        authority == null
                                ? "null"
                                : authority.getClass().getName(),
                        authorityName);

                if (authority == null) {
                    logger.error(
                            "[UAL-2] Authority '{}' does not exist",
                            authorityName);
                    log.append(
                            "\n  ERROR: authority does not exist: ",
                            authorityName,
                            " (user ",
                            username,
                            ")");
                    continue;
                }

                logger.debug(
                        "[UAL-2] Resolved authority name: '{}'",
                        authority.getAuthority());

                // Step 3: Check for existing assignment.
                currentStep = "userService.existsByUserIdAndAuthority";
                logger.debug(
                        "[UAL-3] Checking existing assignment: userId={}, role={}",
                        userId, authorityName);

                final boolean alreadyAssigned = userService.existsByUserIdAndAuthority(
                        userId, authorityName);

                logger.debug(
                        "[UAL-3] Existing assignment result: {}",
                        alreadyAssigned);

                if (alreadyAssigned) {
                    log.append(
                            "\n  Already assigned: ",
                            username,
                            " -> ",
                            authorityName);
                    continue;
                }

                // Step 4: Create assignment.
                currentStep = "userService.addUserRole";
                logger.info(
                        "[UAL-4] Creating assignment: username={}, userId={}, role={}, grantedBy={}, expiresAt=null",
                        username, userId, authorityName, CREATOR_ID);

                final boolean created = userService.addUserRole(
                        userId,
                        authorityName,
                        CREATOR_ID,
                        null);

                logger.info(
                        "[UAL-4] addUserRole returned {} for {}:{}",
                        created, username, authorityName);

                if (created) {
                    log.append(
                            "\n  Created assignment: ",
                            username,
                            " -> ",
                            authorityName);
                } else {
                    logger.error(
                            "[UAL-4] addUserRole returned false for {}:{}",
                            username, authorityName);
                    log.append(
                            "\n  ERROR creating assignment: ",
                            username,
                            " -> ",
                            authorityName);
                }

            } catch (RuntimeException exception) {
                logger.error(
                        "[UAL-ERROR] Assignment '{}' failed at step '{}': {}",
                        assignment,
                        currentStep,
                        exception.getMessage(),
                        exception);

                log.append(
                        "\n  ERROR processing assignment ",
                        assignment,
                        " at step ",
                        currentStep,
                        ": ",
                        exception.toString());
            }
        }

        ICommon.LOG(UserAuthorityLoader.class, log.toString());

        logger.info("[UAL-END] Finished processing user-authority assignments");
    }
}
