package com.rumpus.rumpus.data;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import com.rumpus.common.Dao.jdbc.AbstractApiDBJdbc;
import com.rumpus.rumpus.models.RumpusModel;

/**
 * Assigning AbstractApiDBJdbc for RumpusDao and implementing IRumpusDao
 */
public abstract class RumpusDao<MODEL extends RumpusModel<MODEL, ID>,
        ID> extends AbstractApiDBJdbc<MODEL, ID>
        implements
            IRumpusDao<MODEL, ID> {

    public RumpusDao(
            DataSource ds,
            String table,
            RowMapper<MODEL> mapper,
            Class<ID> idClass) {
        super(ds, table, mapper, idClass);
    }
}
