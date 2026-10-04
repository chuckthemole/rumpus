package com.rumpus.buildshift.service;

import com.rumpus.common.Service.AbstractService;
import com.rumpus.buildshift.data.IBuildShiftDao;
import com.rumpus.buildshift.models.BuildShiftModel;

public class BuildShiftService<MODEL extends BuildShiftModel<MODEL, ID>,
        ID> extends AbstractService<MODEL, ID>
        implements
            IBuildShiftService<MODEL, ID> {
    public BuildShiftService(IBuildShiftDao<MODEL, ID> dao) {
        super(dao);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
