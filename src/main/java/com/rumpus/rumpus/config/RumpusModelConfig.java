package com.rumpus.rumpus.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Config.Model.AbstractCommonModelConfig;
import com.rumpus.common.Serializer.ISerializerRegistry;
import com.rumpus.common.Serializer.SerializerRegistry;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserSerializer;
import com.rumpus.rumpus.service.RumpusServiceManager;

@Configuration
@ComponentScan("com.rumpus.rumpus")
public class RumpusModelConfig
        extends
            AbstractCommonModelConfig<RumpusServiceManager, ISerializerRegistry> {

    @Override
    public RumpusServiceManager createModelServices() {
        return RumpusServiceManager.create();
    }

    @Override
    public ISerializerRegistry createSerializerRegistry() {
        ISerializerRegistry serializerRegistry = SerializerRegistry.create();
        serializerRegistry.registerSerializer(RumpusUser.class,
                RumpusUserSerializer.jsonSerializer());
        return serializerRegistry;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
