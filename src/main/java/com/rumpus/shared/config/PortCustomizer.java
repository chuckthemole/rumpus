package com.rumpus.shared.config;

import com.rumpus.common.Server.Port.IPort;
import com.rumpus.common.Config.AbstractServerPortCustomizer;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class PortCustomizer extends AbstractServerPortCustomizer {

    @Autowired
    public PortCustomizer(IPort port) {
        super(port);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
