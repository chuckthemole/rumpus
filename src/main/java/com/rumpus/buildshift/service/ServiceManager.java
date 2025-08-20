package com.rumpus.buildshift.service;

import com.rumpus.common.Manager.AbstractServiceManager;

public class ServiceManager extends AbstractServiceManager<IBuildShiftService<?>> {

    private ServiceManager() {
        this.registerServices();
    }

    public static ServiceManager create() {
        return new ServiceManager();
    }

    private void registerServices() {
        // IRumpusService<RumpusPost> postService = new RumpusPostService();
        // this.put("RumpusPostService", postService);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}