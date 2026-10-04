package com.rumpus.rumpus.data;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import com.rumpus.rumpus.models.RumpusPost;

public class RumpusPostDao extends RumpusDao<RumpusPost, UUID> implements IRumpusPostDao {

    public RumpusPostDao(DataSource ds, String table, RowMapper<RumpusPost> mapper) {
        super(ds, table, mapper, UUID.class);
    }

    @Override
    protected RumpusPost doAdd(RumpusPost model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doAdd'");
    }

    @Override
    protected RumpusPost doUpdate(UUID id, RumpusPost model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doUpdate'");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
