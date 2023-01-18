package com.rumpus.common;

import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Supplier;

public class Model<T extends RumpusObject> extends RumpusObject implements IModel<T> {

    protected static final String NAME = "rawModel";
    protected int id;
    protected Supplier<T> createFunction;
    protected Map<String, String> rawInitList; // TODO: Map<String, String> : String should be abstracted

    // Ctors
    public Model() {super(NAME);}
    public Model(String name) {
        super(name.isEmpty() ? NAME : name);
    }
    // Ctors with id params for testing
    public Model(int id) {
        super(NAME);
        this.id = id;
    }
    public Model(String name, int id) {
        super(name);
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
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
}
