package com.rumpus.buildshift.models;

import com.rumpus.common.Model.AbstractModel;

public abstract class BuildShiftModel<BS_MODEL extends AbstractModel<?, ID>,
        ID> extends AbstractModel<BS_MODEL, ID> {

    public BuildShiftModel() {
    }
}
