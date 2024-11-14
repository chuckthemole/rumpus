package com.rumpus.rumpus.service;

import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.rumpus.models.RumpusPost;

public class RumpusServiceManager extends AbstractServiceManager<IRumpusService<?>> {

        private RumpusServiceManager() {
            this.registerServices();
        }

        public static RumpusServiceManager create() {
            return new RumpusServiceManager();
        }

        private void registerServices() {
            IRumpusService<RumpusPost> postService = new RumpusPostService();
            this.put("RumpusPostService", postService);
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'toString'");
        }
}
