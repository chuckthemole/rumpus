package com.rumpus.rumpus.data;

import com.rumpus.common.Dao.IDao;
import com.rumpus.rumpus.models.RumpusModel;

public interface IRumpusDao<MODEL extends RumpusModel<MODEL, ID>, ID> extends IDao<MODEL, ID> {
}
