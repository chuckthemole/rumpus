package com.rumpus.rumpus.service;

import java.util.List;

import com.rumpus.rumpus.models.RumpusModel;

public interface IRumpusService<T extends RumpusModel> {
    T get(int id);
    List<T> getAll();
    T add(T rumpusModel);
    boolean remove(int id);
}
