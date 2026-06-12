package com.rumpus.rumpus.models.RumpusUser;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;
import com.rumpus.common.User.AbstractCommonUser;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rumpus_user")
public class RumpusUser extends AbstractCommonUser<RumpusUser, RumpusUserMetaData> {

    @JsonIgnore
    private static SqlIdManager idManager;

    static {
        RumpusUser.idManager = new SqlIdManager();
    }

    protected RumpusUser() {
        RumpusUserFactory factory = new RumpusUserFactory();
        this.setMetaData(factory.createMetaData());
    }

    @Override
    public IModelIdManager<UUID> getIdManager() {
        return RumpusUser.idManager;
    }
}
