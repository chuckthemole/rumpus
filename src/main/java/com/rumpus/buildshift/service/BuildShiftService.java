package com.rumpus.buildshift.service;

import com.rumpus.common.Service.AbstractService;
import com.rumpus.buildshift.data.IBuildShiftDao;
import com.rumpus.buildshift.models.BuildShiftModel;

public class BuildShiftService<MODEL extends BuildShiftModel<MODEL>> extends AbstractService<MODEL> implements IBuildShiftService<MODEL> {
    public BuildShiftService(IBuildShiftDao<MODEL> dao) {
        super(dao);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
