package com.rumpus.rumpus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.rumpus.common.Config.AbstractCommonModelConfig;
import com.rumpus.common.Serializer.ISerializerRegistry;
import com.rumpus.common.Serializer.SerializerRegistry;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserSerializer;
import com.rumpus.rumpus.service.RumpusServiceManager;

@Configuration
@ComponentScan("com.rumpus.rumpus")
public class RumpusModelConfig extends AbstractCommonModelConfig<RumpusServiceManager, ISerializerRegistry> {
    
    @Autowired
    public RumpusModelConfig(Environment environment) {
        super(environment);
    }

    @Override
    public RumpusServiceManager childServices() {
        return RumpusServiceManager.create();
    }

    @Override
    public String sqlDialect() {
        return "MYSQL";
    }

    @Override
    public ISerializerRegistry childSerializerRegistry() {
        ISerializerRegistry serializerRegistry = SerializerRegistry.create();
        serializerRegistry.registerSerializer(RumpusUser.class, RumpusUserSerializer.jsonSerializer());
        return serializerRegistry;
    }

    @Override
    public ISerializerRegistry childSerializerService() {
        ISerializerRegistry serializerRegistry = SerializerRegistry.create();
        serializerRegistry.registerSerializer(RumpusUser.class, RumpusUserSerializer.jsonSerializer());
        return serializerRegistry;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
    
}
