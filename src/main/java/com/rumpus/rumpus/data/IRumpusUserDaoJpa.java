package com.rumpus.rumpus.data;

import com.rumpus.common.Dao.IUserDaoJpa;
import com.rumpus.rumpus.models.RumpusUser;
import com.rumpus.rumpus.models.RumpusUserMetaData;

public interface IRumpusUserDaoJpa extends IUserDaoJpa<RumpusUser, RumpusUserMetaData> {
}
