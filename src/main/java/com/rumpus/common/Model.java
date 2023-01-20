package com.rumpus.common;

import java.sql.ResultSet;
import java.util.Map;

public class Model<T extends RumpusObject> extends RumpusObject implements IModel<T> {

    protected static final String NAME = "rawModel";
    protected Long id;
    protected Map<String, String> initMap; // TODO: Map<String, String> : String should be abstracted

    // Ctors
    public Model(String name) {
        super(name);
    }
    public Model(String name, Map<String, String> initMap) {
        super(name);
        this.initMap = initMap;
    }

    @Override
    public int init() {
        return NOT_INITIALIZED;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setInitMap(Map<String, String> initMap) {
        this.initMap = initMap;
    }

    @Override
    public void map(ResultSet rs) {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(NAME).append("  id: ").append(id);
        return sb.toString();
    }
}
