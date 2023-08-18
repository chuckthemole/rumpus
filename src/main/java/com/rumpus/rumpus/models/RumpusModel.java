package com.rumpus.rumpus.models;

import com.rumpus.common.Model.AbstractModel;

public abstract class RumpusModel<MODEL extends AbstractModel<MODEL>> extends AbstractModel<MODEL> {

    public RumpusModel(String name) {
        super(name);
    }
}
