package com.rumpus.rumpus.data;

import java.util.List;

import com.rumpus.rumpus.models.RumpusModel;

public interface IRumpusDao<T extends RumpusModel> {
    T get(int id);
    List<T> getAll();
    T add(T rumpusModel);
    boolean remove(int id);
    String name();
}
