package com.rumpus.common;

import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Supplier;

public interface IModel<T extends IRumpusObject> extends IRumpusObject {
    T create();
    boolean init(Map<String, String> initList);
    Supplier<T> createFunction();
    Long getId();
    void setId(Long id);
    void map(ResultSet rs);
}
