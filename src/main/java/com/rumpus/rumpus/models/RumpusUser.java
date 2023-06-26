package com.rumpus.rumpus.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Blob;
import java.time.Instant;
import java.util.Map;

import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.MetaData;
import com.rumpus.common.User.CommonUser;
import com.rumpus.common.User.CommonUserMetaData;
import com.rumpus.rumpus.models.Adapters.RumpusUserTypeAdapter;

public class RumpusUser extends CommonUser<RumpusUser> {

    private static final String NAME = "RumpusUser";
    @JsonIgnore private Gson rumpusUserGson;

    private RumpusUser() {
        super(NAME);
        this.setMetaData(new RumpusUserMetaData());
        this.setTypeAdapter(new RumpusUserTypeAdapter()); // TODO can I make this static?
        this.rumpusUserGson = new GsonBuilder()
            .registerTypeAdapter(this.getClass(), new RumpusUserTypeAdapter()).create();
    }

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
        RumpusUser user = new RumpusUser();
        user.setUsername(userMap.containsKey(USERNAME) ? (String) userMap.get(USERNAME) : EMPTY_FIELD);
        user.setUserPassword(userMap.containsKey(PASSWORD) ? (String) userMap.get(PASSWORD) : EMPTY_FIELD);
        user.setEmail(userMap.containsKey(EMAIL) ? (String) userMap.get(EMAIL) : EMPTY_FIELD);
        user.setId(userMap.containsKey(ID) ? (String) userMap.get(ID) : EMPTY_FIELD);

        Blob blob = userMap.containsKey(USER_META_DATA) ? (Blob) userMap.get(USER_META_DATA) : null;
        RumpusUserMetaData metaData = user.new RumpusUserMetaData();

        // ByteArrayInputStream inputStrm = new ByteArrayInputStream(data);
        // LobHandler handler = new DefaultLobHandler();
        // SqlLobValue sqlLobValue = new SqlLobValue(inputStrm, data.length, handler);
        // sqlLobValue.
        // metaData.set
        return user;
    }

    public class RumpusUserMetaData extends CommonUserMetaData<RumpusUser> {
        public RumpusUserMetaData() {super(NAME);}
    }
}
