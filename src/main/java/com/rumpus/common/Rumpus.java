package com.rumpus.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rumpus.common.IO.IRumpusIO;
import com.rumpus.common.IO.RumpusIO;

@Component
public class Rumpus {
    protected IRumpusIO io;
    protected static final Logger LOG = LoggerFactory.getLogger(Rumpus.class);
    public final static Long NO_ID = Long.valueOf(-1);

    @Autowired
    public Rumpus() {
        io = new RumpusIO();
    }
}
