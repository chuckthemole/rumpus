package com.rumpus.rumpus.config;

@org.springframework.stereotype.Component
public class RumpusPortCustomizer extends com.rumpus.common.Config.AbstractServerPortCustomizer {

    @org.springframework.beans.factory.annotation.Autowired
    public RumpusPortCustomizer(org.springframework.core.env.Environment environment, com.rumpus.common.Server.Port.IPort port) {
        super(environment, port);
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
