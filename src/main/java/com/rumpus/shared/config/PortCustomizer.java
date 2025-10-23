package com.rumpus.shared.config;

import com.rumpus.common.Server.Port.IPort;
import com.rumpus.common.Config.AbstractServerPortCustomizer;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Component
public class PortCustomizer extends AbstractServerPortCustomizer {

    @Autowired
    public PortCustomizer(Environment environment, IPort port) {
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
