package com.rumpus.rumpus.data;

import java.util.List;

import com.rumpus.common.IDao;
import com.rumpus.rumpus.models.RumpusModel;

public interface IRumpusDao<T extends RumpusModel<T>> extends IDao<T> {
    // T get(int id);
    // List<T> getAll();
    // T add(T rumpusModel);
    // boolean remove(int id);
}
