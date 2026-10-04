package com.rumpus.rumpus.service;

import com.rumpus.rumpus.models.RumpusPost;

import java.util.UUID;

public class RumpusPostService extends RumpusService<RumpusPost, UUID> {
    public RumpusPostService() {
        super(null);
    }
}
