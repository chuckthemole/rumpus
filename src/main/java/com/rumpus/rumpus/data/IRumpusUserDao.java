package com.rumpus.rumpus.data;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rumpus.common.Dao.IUserDao;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserMetaData;

@Repository
@Profile("database")
public interface IRumpusUserDao extends IUserDao<RumpusUser, RumpusUserMetaData> {
}
