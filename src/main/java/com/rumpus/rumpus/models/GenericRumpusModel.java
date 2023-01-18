package com.rumpus.rumpus.models;

import com.rumpus.common.Model;
import com.rumpus.common.Rumpus;

public class GenericRumpusModel extends Model<GenericRumpusModel> {
    private static final String MODEL_NAME = "genericRumpusModel";
    private RumpusModel<?> object;

    public GenericRumpusModel() {
        super(MODEL_NAME);
    }
    public GenericRumpusModel(String modelName) {
        super(modelName);
    }
    public GenericRumpusModel(String modelName, Long id) {
        super(modelName, id);
    }
    public GenericRumpusModel(RumpusModel<?> object) {
        super(object.name(), object.getId());
        this.object = object;
    }

    public static GenericRumpusModel create(RumpusModel<?> object) {
        return new GenericRumpusModel(object);
    }
    
    public RumpusModel<?> get() {
        return object;
    }
}
