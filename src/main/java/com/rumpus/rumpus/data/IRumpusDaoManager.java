package com.rumpus.rumpus.data;

import java.util.List;
import java.util.Map;

import com.rumpus.common.util.Pair;

public interface IRumpusDaoManager {
    public void init(List<IRumpusDao<?>> list);
    public IRumpusDao<?> get(String name);
    public Map<String, Pair<Class<?>, IRumpusDao<?>>> getMap();
}
