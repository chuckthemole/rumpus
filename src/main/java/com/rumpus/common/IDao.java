package com.rumpus.common;

import java.util.List;
import java.util.function.Function;

public interface IDao<T extends Model<T>> extends IRumpusObject {
    T get(int id);
    List<T> getAll();
    T add(T model);
    boolean remove(int id);
    boolean removeAll();
    String getTable();
    Mapper<T> getMapper();
    Function<T, T> getAddFunction();
}
