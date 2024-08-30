package com.rumpus.rumpus.data;

import javax.sql.DataSource;

import com.rumpus.common.Dao.jdbc.AbstractApiDBJdbc;
import com.rumpus.common.Dao.jdbc.Mapper;
import com.rumpus.rumpus.models.RumpusPost;

public class RumpusPostDao extends AbstractApiDBJdbc<RumpusPost> implements IRumpusPostDao {

    public RumpusPostDao(String name, DataSource dataSource, String table, Mapper<RumpusPost> mapper) {
        super(name, dataSource, table, mapper);
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
