package com.rumpus.common;

import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Model<T extends RumpusObject> extends RumpusObject implements IModel<T> {

    protected static final String NAME = "rawModel";
    protected Long id;
    // protected Function<Map<String, String>, T> createFunction;
    protected Map<String, String> initMap; // TODO: Map<String, String> : String should be abstracted

    // Ctors
    // public Model(Supplier<T> createFunction) {
    //     super(NAME);
    //     this.createFunction = createFunction;
    //     this.id = NO_ID;
    // }
    // public Model(String name) {
    //     super(name.isEmpty() ? NAME : name);
    //     this.id = NO_ID;
    // }
    // Ctors with id params for testing
    // public Model(Long id, Supplier<T> createFunction) {
    //     super(NAME);
    //     this.id = id;
    // }
    // public Model(String name, Long id, Supplier<T> createFunction) {
    //     super(name);
    //     this.id = id;
    // }
    public Model(String name) {
        super(name);
        // this.createFunction = createFunction;
    }
    public Model(String name, Map<String, String> initMap) {
        super(name);
        // this.createFunction = createFunction;
        this.initMap = initMap;
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
    public void map(ResultSet rs) {
    }

    // @Override
    // public T create(Map<String, String> initMap) {
    //     return createFunction.apply(initMap);
    // }

    @Override
    public int init() {
        return NOT_INITIALIZED;
    }

    // @Override
    // public Supplier<T> createFunction() {
    //     return null;
    // }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(NAME).append("  id: ").append(id);
        return sb.toString();
    }
    @Override
    public void setInitMap(Map<String, String> initMap) {
        this.initMap = initMap;
    }
}
