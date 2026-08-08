package com.rumpus.rumpus.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumpus.common.Controller.AbstractCommonRestController;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.LogItem.LogItemCollectionManager;
import com.rumpus.common.Server.ServerManager;
import com.rumpus.common.User.ActiveUserStore;

abstract public class AbstractRumpusRestController
        extends
            AbstractCommonRestController {

    @Autowired
    protected ActiveUserStore activeUserStore;

    @Autowired
    protected ForumThreadManager forumThreadManager;

    @Autowired
    protected LogItemCollectionManager logManager;

    @Autowired
    protected ServerManager serverManager;

    protected static String RUMPUS_DEFAULT_BASE_PATH = "/api";

    public AbstractRumpusRestController() {
        super(RUMPUS_DEFAULT_BASE_PATH);
    }
}
