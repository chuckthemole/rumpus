package com.rumpus.common;

import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Supplier;

public class Model<T extends RumpusObject> extends RumpusObject implements IModel<T> {

    protected static final String NAME = "rawModel";
    protected Long id;
    protected Supplier<T> createFunction;
    protected Map<String, String> rawInitList; // TODO: Map<String, String> : String should be abstracted

    // Ctors
    public Model() {
        super(NAME);
        this.id = NO_ID;
    }
    public Model(String name) {
        super(name.isEmpty() ? NAME : name);
        this.id = NO_ID;
    }
    // Ctors with id params for testing
    public Model(Long id) {
        super(NAME);
        this.id = id;
    }
    public Model(String name, Long id) {
        super(name);
        this.id = id;
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

    @Override
    public T create() {
        return createFunction.get();
    }

    @Override
    public boolean init(Map<String, String> initList) {
        this.rawInitList = initList;
        createFunction = createFunction();
        return true;
    }

    @Override
    public Supplier<T> createFunction() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(NAME).append("  id: ").append(id);
        return sb.toString();
    }
}
