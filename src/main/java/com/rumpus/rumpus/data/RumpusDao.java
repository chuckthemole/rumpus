package com.rumpus.rumpus.data;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rumpus.common.Dao;
import com.rumpus.common.Mapper;
import com.rumpus.common.ApiDB.Api;
import com.rumpus.common.ApiDB.ApiDB;
import com.rumpus.common.ApiDB.IApi;
import com.rumpus.common.ApiDB.IApiDB;
import com.rumpus.rumpus.models.RumpusModel;

public class RumpusDao<T extends RumpusModel<T>> extends Dao<T> implements IRumpusDao<T> {

    public RumpusDao(IApi<T> api, String table, String idName) {
        super(api, table, idName);
    }
}
