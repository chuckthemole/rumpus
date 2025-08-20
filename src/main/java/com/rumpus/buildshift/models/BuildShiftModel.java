package com.rumpus.buildshift.models;

import com.rumpus.common.Model.AbstractModel;

import java.util.UUID;

public abstract class BuildShiftModel<BS_MODEL extends AbstractModel<?, UUID>> extends AbstractModel<BS_MODEL, UUID> {

    public BuildShiftModel() {
    }
}
