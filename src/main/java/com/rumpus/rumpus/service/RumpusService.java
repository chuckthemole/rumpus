package com.rumpus.rumpus.service;

import com.rumpus.common.Service.AbstractService;
import com.rumpus.rumpus.data.IRumpusDao;
import com.rumpus.rumpus.models.RumpusModel;

public class RumpusService<MODEL extends RumpusModel<MODEL, ID>,
        ID> extends AbstractService<MODEL, ID>
        implements
            IRumpusService<MODEL, ID> {
    public RumpusService(IRumpusDao<MODEL, ID> dao) {
        super(dao);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
