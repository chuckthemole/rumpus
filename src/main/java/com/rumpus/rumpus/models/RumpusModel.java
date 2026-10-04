package com.rumpus.rumpus.models;

import com.rumpus.common.Model.AbstractModel;

public abstract class RumpusModel<RUMPUS_MODEL extends AbstractModel<?, ID>, ID>
        extends
            AbstractModel<RUMPUS_MODEL, ID> {

    public RumpusModel() {
    }
}
