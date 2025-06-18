package com.rumpus.rumpus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rumpus.common.Forum.ForumThreadManager;

public class RumpusClassLoadingTest {

    private static final String FORUM_THREAD_MANAGER = "com.rumpus.common.Forum.ForumThreadManager";
    private static final String FORUM_THREAD_THREAD = "com.rumpus.common.Forum.ForumThread";

    @Test
    public void testLibraryClassesAreLoadable() {
        assertDoesNotThrow(() -> {
            Class.forName(RumpusClassLoadingTest.FORUM_THREAD_MANAGER);
            Class.forName(RumpusClassLoadingTest.FORUM_THREAD_THREAD);
        }, "Common library classes should be loadable");
    }

    @Test
    public void testLibraryClassesCanBeInstantiated() {
        assertDoesNotThrow(() -> {
            ForumThreadManager manager = ForumThreadManager.create();
            assertNotNull(manager);
        }, "ForumThreadManager should be instantiable");
    }
}