package com.rumpus.rumpus.data;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rumpus.common.IDao;
import com.rumpus.rumpus.models.RumpusUser;

@Repository
@Profile("database")
public interface IRumpusUserDao extends IDao<RumpusUser> {
}
