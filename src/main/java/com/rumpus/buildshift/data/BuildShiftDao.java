package com.rumpus.buildshift.data;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import com.rumpus.common.Dao.jdbc.AbstractApiDBJdbc;
import com.rumpus.buildshift.models.BuildShiftModel;

public abstract class BuildShiftDao<MODEL extends BuildShiftModel<MODEL, ID>,
        ID> extends AbstractApiDBJdbc<MODEL, ID>
        implements
            IBuildShiftDao<MODEL, ID> {

    public BuildShiftDao(
            DataSource ds,
            String table,
            RowMapper<MODEL> mapper,
            Class<ID> idClass) {
        super(ds, table, mapper, idClass);
    }
}
