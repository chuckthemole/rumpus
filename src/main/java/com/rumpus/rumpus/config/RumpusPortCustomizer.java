package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import com.rumpus.common.Config.AbstractServerPortCustomizer;
import com.rumpus.common.Config.Server.ServerConfig;
import com.rumpus.common.Server.Port.IPort;

@Component
@Import({
        ServerConfig.class
})
public class RumpusPortCustomizer extends AbstractServerPortCustomizer {

    @Autowired
    public RumpusPortCustomizer(IPort port) {
        super(port);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
