package com.rumpus.rumpus.data;

import com.rumpus.rumpus.models.Auth;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("database")
public interface IAuthDao extends IRumpusDao<Auth> {
}
