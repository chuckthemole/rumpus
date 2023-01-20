package com.rumpus.common;

import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Supplier;

public interface IModel<T extends IRumpusObject> extends IRumpusObject {
    // T create(Map<String, String> initMap);
    int init();
    // Supplier<T> createFunction();
    Long getId();
    void setId(Long id);
    void map(ResultSet rs);
    void setInitMap(Map<String, String> initMap);
}
