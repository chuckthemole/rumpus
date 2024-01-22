package com.rumpus.rumpus.models;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.AbstractMetaData;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public class RumpusUser extends AbstractCommonUser<RumpusUser, RumpusUserMetaData> {

    private static final String NAME = "RumpusUser";
    @JsonIgnore transient private Gson rumpusUserGson;

    private RumpusUser() {
        super(NAME);
        this.setMetaData(RumpusUserMetaData.createEmpty());
        this.setTypeAdapter(this.createTypeAdapter()); // TODO can I make this static?
        this.rumpusUserGson = new GsonBuilder()
            .registerTypeAdapter(this.getClass(), this.getTypeAdapter()).create();
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

    @SuppressWarnings(UNCHECKED)
    public static RumpusUser createFromMap(Map<String, Object> userMap) {
        LOG.info("RumpusUser::createFromMap()");
        RumpusUser user = new RumpusUser();
        user.setUsername(userMap.containsKey(USERNAME) ? (String) userMap.get(USERNAME) : EMPTY_FIELD);
        user.setUserPassword(userMap.containsKey(PASSWORD) ? (String) userMap.get(PASSWORD) : EMPTY_FIELD);
        user.setEmail(userMap.containsKey(EMAIL) ? (String) userMap.get(EMAIL) : EMPTY_FIELD);
        user.setId(userMap.containsKey(ID) ? (String) userMap.get(ID) : EMPTY_FIELD);

        // user meta data
        AbstractCommonUserMetaData<RumpusUserMetaData> meta = null;
        if(userMap.containsKey(USER_META_DATA)) {
            // meta = RumpusUserMetaData.createFromListOfMaps((List<Map<String, String>>) userMap.get(USER_META_DATA));
            meta = (AbstractCommonUserMetaData<RumpusUserMetaData>) userMap.get(USER_META_DATA);
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
    public void serialize(RumpusUser object, OutputStream outputStream) throws IOException {
        LOG.info("RumpusUser::serialize()");
        this.getTypeAdapter().write(new JsonWriter(new OutputStreamWriter(outputStream)), object);
    }

    @Override
    public Map<String, Object> getModelAttributesMap() {
        LOG.info("RumpusUser::getModelAttributesMap()");
        Map<String, Object> modelAttributesMap = Map.of(ID, this.id, EMAIL, this.getEmail());
        return modelAttributesMap;
    }

    @Override
    public TypeAdapter<RumpusUser> createTypeAdapter() {
        return new TypeAdapter<RumpusUser>() {
            @Override
            public void write(JsonWriter out, RumpusUser user) throws IOException {
                out.beginObject(); 
                out.name(ICommon.USERNAME);
                out.value(user.getUsername());
                out.name(ICommon.EMAIL);
                out.value(user.getEmail());
                out.name(ICommon.PASSWORD);
                out.value(user.getPassword());

                // meta data
                out.name(AbstractMetaData.USER_CREATION_DATE_TIME);
                out.value(user.getMetaData().getStandardFormattedCreationTime());
                out.name(AbstractCommonUserMetaData.USER_PHOTO_LINK);
                out.value(user.getMetaData().getPhotoLink());
                out.name(AbstractCommonUserMetaData.USER_ABOUT_ME);
                out.value(user.getMetaData().getAboutMe());
                out.endObject();
            }

            @Override
            public RumpusUser read(JsonReader in) throws IOException {
                RumpusUser user = RumpusUser.createEmptyUser();
                RumpusUserMetaData metaData = RumpusUserMetaData.createEmpty();
                in.beginObject();
                String fieldname = null;

                while (in.hasNext()) {
                    JsonToken token = in.peek();
                    
                    if (token.equals(JsonToken.NAME)) {
                        //get the current token 
                        fieldname = in.nextName(); 
                    }
                    if (ICommon.USERNAME.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        user.setUsername(in.nextString());
                    }
                    if(ICommon.EMAIL.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        user.setEmail(in.nextString());
                    }

                    // meta data
                    if(AbstractMetaData.USER_CREATION_DATE_TIME.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        metaData.setCreationTime(in.nextString());
                    }
                    if(AbstractCommonUserMetaData.USER_PHOTO_LINK.equals(fieldname)) {
                        token = in.peek();
                        metaData.setPhotoLink(in.nextString());
                    }
                    if(AbstractCommonUserMetaData.USER_ABOUT_ME.equals(fieldname)) {
                        token = in.peek();
                        metaData.setAboutMe(in.nextString());
                    }
                }
                user.setMetaData(metaData);
                in.endObject();
                return user;
            }
        };
    }  
}
