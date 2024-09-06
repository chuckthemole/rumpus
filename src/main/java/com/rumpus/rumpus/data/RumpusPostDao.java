package com.rumpus.rumpus.data;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import com.rumpus.rumpus.models.RumpusPost;

public class RumpusPostDao extends RumpusDao<RumpusPost> implements IRumpusPostDao {

    public RumpusPostDao(String name, DataSource ds, String table, RowMapper<RumpusPost> mapper) {
        super(name, ds, table, mapper);
        //TODO Auto-generated constructor stub
    }

    @Override
    public RumpusPost add(RumpusPost model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public RumpusPost update(String id, RumpusPost updatedModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
