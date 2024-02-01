package com.rumpus.rumpus;

import java.util.List;

import com.rumpus.common.ICommon;
import com.rumpus.common.Forum.ForumThread;

public interface IRumpus extends ICommon {

    public static final String ADMIN_FORUM_THREAD_ID = "ADMIN_FORUM_THREAD_ID";

    public static final List<ForumThread> rumpusForumThreads = List.of(
        ForumThread.createWithPageId(ADMIN_FORUM_THREAD_ID)
    );

    /**
     * Log a message in Rumpus using info level
     * 
     * @param clazz the class to log from
     * @param args the message to log
     */
    public static void LOG(Class<?> clazz, String... args) {
        ICommon.LOG(clazz, args);
    }

    /**
     * Log a message in Rumpus using the specified level
     * 
     * @param clazz the class to log from
     * @param level the level to log the message at
     * @param args the message to log
     */
    public static void LOG(Class<?> clazz, com.rumpus.common.Logger.AbstractCommonLogger.LogLevel level, String... args) {
        ICommon.LOG(clazz, level, args);
    }
}
