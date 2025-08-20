package com.rumpus.buildshift.models.BuildShiftUser;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;
import com.rumpus.common.User.AbstractCommonUser;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "buildshift_user")
public class User extends AbstractCommonUser<User, UserMetaData> {

    @JsonIgnore private static SqlIdManager idManager;

    static {
        User.idManager = new SqlIdManager();
    }

    private User() {
        this.setMetaData(UserMetaData.createEmpty());
    }

    public static User createEmptyUser() {
        return new User();
    }

    public static User create(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }

    @Override
    public IModelIdManager<UUID> getIdManager() {
        return User.idManager;
    }
}
