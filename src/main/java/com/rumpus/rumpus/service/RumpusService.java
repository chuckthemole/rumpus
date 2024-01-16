package com.rumpus.rumpus.service;

import com.rumpus.common.Service.AbstractService;
import com.rumpus.rumpus.data.IRumpusDao;
import com.rumpus.rumpus.models.RumpusModel;

public class RumpusService<MODEL extends RumpusModel<MODEL>> extends AbstractService<MODEL> implements IRumpusService<MODEL> {
    public RumpusService(String name, IRumpusDao<MODEL> dao) {
        super(name, dao);
    }
}
