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
import com.rumpus.common.User.CommonAuthority;
import com.rumpus.common.Service.User.IAuthorityService;

/**
 * AuthorityLoader seeds the database with initial authority/role data.
 * <p>
 * This runner is only active in the "dev" profile to avoid accidentally
 * populating production data.
 */
@Component
@Profile("dev")
public class AuthorityLoader implements CommandLineRunner {

    // File I/O utilities for reading JSON data
    private final IFileIO fileReader = JsonIO.create();
    private final FileProcessor fileProcessor;

    // Service for persisting CommonAuthority objects
    private final IAuthorityService authorityService;

    // Path to the JSON file containing initial authorities
    private static final String JSON_AUTHORITIES_FILE = "src/main/java/com/rumpus/rumpus/database_loader/authorities.json";

    /**
     * Constructor
     *
     * @param authorityService
     *            Service for CommonAuthority persistence
     */
    public AuthorityLoader(IAuthorityService authorityService) {
        this.authorityService = authorityService;
        this.fileProcessor = new FileProcessor(fileReader);
    }

    /**
     * CommandLineRunner entry point. Executes automatically on application startup
     * if the component is active.
     *
     * @param args
     *            Command-line arguments
     */
    @Override
    public void run(String... args) throws Exception {
        ICommon.LOG(
                AuthorityLoader.class,
                "AuthorityLoader::run() - running in dev profile");

        Optional<CommonAuthority[]> authoritiesOpt = this.fileProcessor
                .<CommonAuthority>processFile(
                        JSON_AUTHORITIES_FILE,
                        CommonAuthority[].class);

        if (authoritiesOpt.isPresent()) {
            CommonAuthority[] authorities = authoritiesOpt.get();

            LogBuilder log = LogBuilder.logBuilderFromStringArgs(
                    "\nPopulating Rumpus authorities...");

            for (CommonAuthority authority : authorities) {

                if (authorityService.existsByName(authority.getAuthority())) {
                    log.append(
                            "\n  Authority already exists: ",
                            authority.getAuthority());
                } else {
                    if (authorityService.createAuthority(authority) != null) {
                        log.append(
                                "\n  Success adding authority: ",
                                authority.getAuthority());
                    } else {
                        log.append(
                                "\n  ERROR adding authority: ",
                                authority.toString());
                    }
                }
            }

            ICommon.LOG(AuthorityLoader.class, log.toString());

        } else {
            ICommon.LOG(
                    AuthorityLoader.class,
                    "AuthorityLoader::run() - no authorities found, skipping population");
        }
    }
}
