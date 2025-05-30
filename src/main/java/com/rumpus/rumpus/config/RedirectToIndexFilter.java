package com.rumpus.rumpus.config;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rumpus.common.Config.AbstractRedirectToIndexFilter;

// @Component
/**
 * Not using right now, commenting out Component. Check out this bug for more info: https://gitlab.com/rumpushub-group/rumpushub/-/issues/1
 * - chuck 2025/5/30
 */
public class RedirectToIndexFilter extends AbstractRedirectToIndexFilter {

    final static List<String> startOfURIsNotFiltered = List.of(
            "/api",
            "/view",
            "/static",
            "/css",
            "/js");

    public RedirectToIndexFilter() {
        super(startOfURIsNotFiltered);
    }

}
