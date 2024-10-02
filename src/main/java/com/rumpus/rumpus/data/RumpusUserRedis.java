package com.rumpus.rumpus.data;

import com.rumpus.common.Dao.redis.IRedisRepository;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;

public class RumpusUserRedis {
    private IRedisRepository<RumpusUser> rumpusUserRepository;

    public RumpusUserRedis(IRedisRepository<RumpusUser> rumpusUserRepository) {
        this.rumpusUserRepository = rumpusUserRepository;
    }
}
