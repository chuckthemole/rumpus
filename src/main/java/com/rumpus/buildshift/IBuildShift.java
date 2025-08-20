package com.rumpus.buildshift;

import java.util.List;

import com.rumpus.common.ICommon;
import com.rumpus.common.Forum.ForumThread;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

public interface IBuildShift extends ICommon {

    /**
     * Log a message using info level
     * 
     * @param clazz the class to log from
     * @param args the message to log
     */
    public static void LOG(Class<?> clazz, String... args) {
        ICommon.LOG(clazz, args);
    }

    /**
     * Log a message using the specified level
     * 
     * @param clazz the class to log from
     * @param level the level to log the message at
     * @param args the message to log
     */
    public static void LOG(Class<?> clazz, LogLevel level, String... args) {
        ICommon.LOG(clazz, level, args);
    }
}
