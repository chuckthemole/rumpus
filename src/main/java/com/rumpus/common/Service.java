package com.rumpus.common;

public class Service<T extends RumpusObject> extends RumpusObject implements IService<T> {

    protected static final String NAME = "rawService";

    public Service() {super(NAME);}
    public Service(String name) {
        super(name);
    }
}
