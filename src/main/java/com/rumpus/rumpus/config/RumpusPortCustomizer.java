package com.rumpus.rumpus.config;

@org.springframework.stereotype.Component
public class RumpusPortCustomizer extends com.rumpus.common.Config.AbstractServerPortCustomizer {

    public static final String NAME = "RumpusPortCustomizer";

    @org.springframework.beans.factory.annotation.Autowired
    public RumpusPortCustomizer(org.springframework.core.env.Environment environment, com.rumpus.common.Server.Port.IPort port) {
        super(NAME, environment, port);
    }
}
