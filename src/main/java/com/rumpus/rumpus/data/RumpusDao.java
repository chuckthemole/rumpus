package com.rumpus.rumpus.data;

import java.util.function.Function;

import com.rumpus.common.Dao;
import com.rumpus.common.Mapper;
import com.rumpus.rumpus.models.RumpusModel;

public class RumpusDao<T extends RumpusModel<T>> extends Dao<T> implements IRumpusDao<T> {

    public RumpusDao(String table, String idName, Mapper<T> mapper, Function<T, T> add) {
        super(table, idName, mapper, add);
    }
}
