package com.rumpus.rumpus.config;

import org.springframework.core.env.Environment;

import com.rumpus.common.Config.AbstractCommonSerializerConfig;
import com.rumpus.common.Serializer.ISerializerRegistry;
import com.rumpus.common.Serializer.SerializerRegistry;
import com.rumpus.rumpus.models.RumpusUser.RumpusUser;
import com.rumpus.rumpus.models.RumpusUser.RumpusUserSerializer;

public class RumpusSerializerConfig extends AbstractCommonSerializerConfig {

    public RumpusSerializerConfig(Environment environment) {
        super(environment);
    }

    @Override
    public ISerializerRegistry childSerializerRegistry() {
        ISerializerRegistry registry = SerializerRegistry.create();
        registry.registerSerializer(RumpusUser.class, RumpusUserSerializer.jsonSerializer());
        return registry;
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
