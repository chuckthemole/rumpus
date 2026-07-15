package com.rumpus.rumpus.data.User;

import com.rumpus.common.Dao.User.IUserDaoJpa;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

public interface IRumpusUserDaoJpa extends IUserDaoJpa<RumpusUser, RumpusUserMetaData> {
}
