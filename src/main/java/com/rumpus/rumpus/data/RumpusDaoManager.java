package com.rumpus.rumpus.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rumpus.common.util.Pair;
import com.rumpus.common.Rumpus;

public class RumpusDaoManager implements IRumpusDaoManager {
    private Map<String, Pair<Class<?>, IRumpusDao<?>>> dao; // Holds each dao. Key: dao class name. Value: pair<class, dao>

    public RumpusDaoManager(List<IRumpusDao<?>> list) {
        init(list);
    }

    @Override
    public void init(List<IRumpusDao<?>> list) {
        dao = new HashMap<>();
        for(IRumpusDao<?> daoFromList : list) {
            Pair<Class<?>, IRumpusDao<?>> daoPair = new Pair<>(daoFromList.getClass(), daoFromList);
            this.dao.put(daoFromList.name(), daoPair);
        }
    }

    @Override
    public IRumpusDao<?> get(String name) {
        return dao.get(name).getSecond();
    }

    @Override
    public Map<String, Pair<Class<?>, IRumpusDao<?>>> getMap() {
        return dao;
    }
}
