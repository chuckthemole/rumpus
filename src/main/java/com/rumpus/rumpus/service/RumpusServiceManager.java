package com.rumpus.rumpus.service;

import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.rumpus.models.RumpusPost;

public class RumpusServiceManager extends AbstractServiceManager<IRumpusService<?>> {

        public static final String NAME = "RumpusServiceManager";

        private RumpusServiceManager() {
            super(NAME);
            this.registerServices();
        }

        public static RumpusServiceManager create() {
            return new RumpusServiceManager();
        }

        private void registerServices() {
            IRumpusService<RumpusPost> postService = new RumpusPostService();
            this.put("RumpusPostService", postService);
        }
}
