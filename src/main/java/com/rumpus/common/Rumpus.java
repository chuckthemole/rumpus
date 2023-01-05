package com.rumpus.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rumpus.common.IO.IRumpusIO;

@Component
public class Rumpus {
    protected IRumpusIO io;

    @Autowired
    public Rumpus(IRumpusIO io) {
        this.io = io;
    }
}
