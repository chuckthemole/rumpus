package com.rumpus.rumpus.database_loader;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Config.User.AuthRolesListProperties;
import com.rumpus.common.User.CommonAuthority;
import com.rumpus.common.Service.User.IAuthorityService;

/**
 * Loads configured authority definitions into the database.
 *
 * Authorities are configured under properties.roles.auth-roles in
 * application.properties.
 *
 * Only runs in the dev profile.
 */
@Component
@Profile("dev")
@Order(2)
public class AuthorityLoader implements CommandLineRunner {

    private final IAuthorityService authorityService;
    private final AuthRolesListProperties rolesProperties;

    public AuthorityLoader(
            IAuthorityService authorityService,
            AuthRolesListProperties rolesProperties) {
        this.authorityService = authorityService;
        this.rolesProperties = rolesProperties;
    }

    @Override
    public void run(String... args) {
        ICommon.LOG(
                AuthorityLoader.class,
                "AuthorityLoader::run() - loading authority definitions");

        Set<String> authorities = new LinkedHashSet<>(
                rolesProperties.getRoles().stream()
                        .filter(name -> name != null)
                        .map(String::trim)
                        .filter(name -> !name.isEmpty())
                        .toList());

        if (authorities.isEmpty()) {
            ICommon.LOG(
                    AuthorityLoader.class,
                    "No authorities configured; skipping population");
            return;
        }

        LogBuilder log = LogBuilder.logBuilderFromStringArgs(
                "\nLoading authority definitions...");

        for (String name : authorities) {
            try {
                if (authorityService.existsByName(name)) {
                    log.append("\n  Already exists: ", name);
                    continue;
                }

                CommonAuthority authority = new CommonAuthority(name);

                if (authorityService.createAuthority(authority) != null) {
                    log.append("\n  Created: ", name);
                } else {
                    log.append("\n  ERROR creating: ", name);
                }
            } catch (RuntimeException exception) {
                log.append(
                        "\n  ERROR creating ",
                        name,
                        ": ",
                        exception.getMessage());
            }
        }

        ICommon.LOG(AuthorityLoader.class, log.toString());
    }
}
