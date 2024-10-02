package com.rumpus.rumpus.data;

import com.rumpus.common.Dao.IUserDaoJpa;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

public interface IRumpusUserDaoJpa extends IUserDaoJpa<RumpusUser, RumpusUserMetaData> {
}
