package com.rumpus.rumpus.data;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import com.rumpus.common.Dao.jdbc.AbstractApiDBJdbc;
import com.rumpus.rumpus.models.RumpusModel;

/**
 * Assigning AbstractApiDBJdbc for RumpusDao and implementing IRumpusDao
 */
public abstract class RumpusDao<MODEL extends RumpusModel<MODEL>> extends AbstractApiDBJdbc<MODEL> implements IRumpusDao<MODEL> {
    
    public RumpusDao(String name, DataSource ds, String table, RowMapper<MODEL> mapper) {
        super(name, ds, table, mapper);
    }
}
