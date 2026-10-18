package com.rumpus.buildshift.models.BuildShiftUser;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.CommonUserDetails;

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

    protected User(
            UUID userId,
            String email,
            CommonUserDetails userDetails,
            UserMetaData metaData) {
        super(userId, email, userDetails, metaData);
    }

    @Override
    public IModelIdManager<UUID> getIdManager() {
        return User.idManager;
    }
}
