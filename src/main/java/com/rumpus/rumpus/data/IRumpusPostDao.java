package com.rumpus.rumpus.data;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rumpus.rumpus.models.RumpusPost;

@Repository
@Profile("database")
public interface IRumpusPostDao extends IRumpusDao<RumpusPost, UUID> {

}
