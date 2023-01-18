package com.rumpus.common;

import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Supplier;

public interface IModel<T extends IRumpusObject> extends IRumpusObject {
    T create();
    boolean init(Map<String, String> initList);
    Supplier<T> createFunction();
    int getId();
    void setId(int id);
    void map(ResultSet rs);
}
