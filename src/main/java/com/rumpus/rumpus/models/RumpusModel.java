package com.rumpus.rumpus.models;

import com.rumpus.common.Model;

public class RumpusModel<T extends Model<T>> extends Model<T> {

    public RumpusModel(String name) {
        super(name);
    }
    public RumpusModel(String name, int id) {
        super(name, id);
    }
}
