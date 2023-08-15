package com.rumpus.rumpus.managers;

import com.rumpus.common.AbstractCommon;
import com.rumpus.common.Forum.ForumThread;

public class ForumThreadManager extends AbstractCommon {

    private ForumThread adminForumThread;
    private static final String ADMIN_FORUM_THREAD_ID = "ADMIN_FORUM_THREAD_ID";

    private ForumThreadManager() {
        this.init();
    }
    
    public static ForumThreadManager initManager() {
        return new ForumThreadManager();
    }

    private void init() {
        this.adminForumThread = ForumThread.create(null, ADMIN_FORUM_THREAD_ID);
    }

    public ForumThread getAdminForumThread() {
        return this.adminForumThread;
    }
}
