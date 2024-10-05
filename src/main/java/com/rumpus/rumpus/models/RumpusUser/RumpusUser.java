package com.rumpus.rumpus.models.RumpusUser;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.rumpus.IRumpus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rumpus_user")
public class RumpusUser extends AbstractCommonUser<RumpusUser, RumpusUserMetaData> {

    private static final String NAME = "RumpusUser";
    @JsonIgnore private static SqlIdManager idManager;

    static {
        RumpusUser.idManager = new SqlIdManager();
    }

    private RumpusUser() {
        super(NAME);
        this.setMetaData(RumpusUserMetaData.createEmpty());
    }

    /////////////////////////////
    // public static factory ////
    /////////////////////////////
    public static RumpusUser createEmptyUser() {
        return new RumpusUser();
    }

    public static RumpusUser create(String username, String password, String email) {
        RumpusUser user = new RumpusUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }

    public static RumpusUser createFromMap(Map<String, Object> userMap) {
        IRumpus.LOG(RumpusUser.class, "RumpusUser::createFromMap()");
        RumpusUser user = new RumpusUser();
        user.setUsername(userMap.containsKey(USERNAME) ? (String) userMap.get(USERNAME) : EMPTY_FIELD);
        user.setUserPassword(userMap.containsKey(PASSWORD) ? (String) userMap.get(PASSWORD) : EMPTY_FIELD);
        user.setEmail(userMap.containsKey(EMAIL) ? (String) userMap.get(EMAIL) : EMPTY_FIELD);
        user.setId(userMap.containsKey(ID) ? java.util.UUID.fromString((String) userMap.get(ID)) : EMPTY_UUID);

        // user meta data
        RumpusUserMetaData meta = null;
        if(userMap.containsKey(USER_META_DATA)) {
            // meta = RumpusUserMetaData.createFromListOfMaps((List<Map<String, String>>) userMap.get(USER_META_DATA));
            meta = (RumpusUserMetaData) userMap.get(USER_META_DATA);
        }

        if(meta == null) {
            LogBuilder.logBuilderFromStringArgs(RumpusUser.class, "Failed building RumpusUserMetaData. Setting empty meta data.").info();
            meta = RumpusUserMetaData.createEmpty();
        }

        LogBuilder.logBuilderFromStringArgs(RumpusUser.class, "Success building RumpusUserMetaData:\n", meta.toString()).info();
        user.setMetaData(meta);
        return user;
    }
    /////////////////////////////////
    // end public static factory ////
    /////////////////////////////////

    @Override
    public IModelIdManager<java.util.UUID> getIdManager() {
        return RumpusUser.idManager;
    }
}
