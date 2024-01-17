package com.rumpus.rumpus;

import java.util.List;

import com.rumpus.common.ICommon;
import com.rumpus.common.Forum.ForumThread;

public class Rumpus implements ICommon {

    // ForumThread list : if you want to add a new forum thread, add it to the list.
    public static final String ADMIN_FORUM_THREAD_ID = "ADMIN_FORUM_THREAD_ID";
    public static final List<ForumThread> rumpusForumThreads = List.of(
        ForumThread.createWithPageId(ADMIN_FORUM_THREAD_ID)
    );
}
