package com.rumpus.buildshift.models.BuildShiftUser;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;
import com.rumpus.common.User.AbstractCommonUser;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "buildshift_user")
public class User extends AbstractCommonUser<User, UserMetaData> {

    @JsonIgnore
    private static SqlIdManager idManager;

    static {
        User.idManager = new SqlIdManager();
    }

    protected User() {
        UserFactory factory = new UserFactory();
        this.setMetaData(factory.createMetaData());
    }

    @Override
    public IModelIdManager<UUID> getIdManager() {
        return User.idManager;
    }
}
