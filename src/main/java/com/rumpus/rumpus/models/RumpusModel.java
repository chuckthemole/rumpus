package com.rumpus.rumpus.models;

import java.util.Map;

import com.rumpus.common.Model;

public abstract class RumpusModel<T extends Model<T>> extends Model<T> {

    public RumpusModel(String name) {
        super(name);
    }
    public RumpusModel(String name, Map<String, String> attributeMap) {
        super(name, attributeMap);
    }
}
