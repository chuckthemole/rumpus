package com.rumpus.rumpus.models;

import com.rumpus.common.Model.AbstractModel;

public abstract class RumpusModel<RUMPUS_MODEL extends AbstractModel<?>> extends AbstractModel<RUMPUS_MODEL> {

    public RumpusModel(String name) {
        super(name);
    }
}
