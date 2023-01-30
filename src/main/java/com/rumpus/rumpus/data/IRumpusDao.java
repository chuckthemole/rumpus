package com.rumpus.rumpus.data;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.rumpus.common.IDao;
import com.rumpus.common.Mapper;
import com.rumpus.rumpus.models.RumpusModel;

public interface IRumpusDao<T extends RumpusModel<T>> extends IDao<T> {
    // T get(int id);
    // List<T> getAll();
    // T add(T rumpusModel);
    // boolean remove(int id);
}
