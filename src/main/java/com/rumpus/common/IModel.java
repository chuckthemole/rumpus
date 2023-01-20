package com.rumpus.common;

import java.sql.ResultSet;
import java.util.Map;

public interface IModel<T extends IRumpusObject> extends IRumpusObject {
    int init();
    Long getId();
    void setId(Long id);
    void map(ResultSet rs);
    void setInitMap(Map<String, String> initMap);
}
