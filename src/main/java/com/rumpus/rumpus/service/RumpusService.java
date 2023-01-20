package com.rumpus.rumpus.service;

import com.rumpus.common.Service;
import com.rumpus.rumpus.data.IRumpusDao;
import com.rumpus.rumpus.models.RumpusModel;

public class RumpusService<T extends RumpusModel<T>> extends Service<T> implements IRumpusService<T> {
    public RumpusService(String name, IRumpusDao<T> dao) {
        super(name, dao);
    }
}
