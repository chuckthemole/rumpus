package com.rumpus.rumpus.data;

import com.rumpus.common.Dao.Dao;
import com.rumpus.common.Dao.IApiDB;
import com.rumpus.rumpus.models.RumpusModel;

public class RumpusDao<T extends RumpusModel<T>> extends Dao<T> implements IRumpusDao<T> {

    public RumpusDao(String table, String name) {
        super(table, NO_META_TABLE, name);
    }
    public RumpusDao(IApiDB<T> api, String table, String name) {
        super(api, table, NO_META_TABLE, name);
    }
}
