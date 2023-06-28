package com.rumpus.rumpus.models;

import java.util.Map;

import com.rumpus.common.AbstractModel;

public abstract class RumpusModel<T extends AbstractModel<T>> extends AbstractModel<T> {

    public RumpusModel(String name) {
        super(name);
    }
}
