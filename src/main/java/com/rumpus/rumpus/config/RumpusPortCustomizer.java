package com.rumpus.rumpus.config;

@org.springframework.stereotype.Component
public class RumpusPortCustomizer extends com.rumpus.common.Config.AbstractServerPortCustomizer {

    @org.springframework.beans.factory.annotation.Autowired
    public RumpusPortCustomizer(com.rumpus.common.Server.Port.IPort port) {
        super(port);
    }
}
