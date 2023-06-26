package com.rumpus.rumpus.models.Adapters;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.time.Instant;

import org.springframework.core.serializer.Serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.MetaData;
import com.rumpus.common.Rumpus;
import com.rumpus.common.User.CommonUserMetaData;
import com.rumpus.rumpus.models.RumpusUser;

public class RumpusUserTypeAdapter extends TypeAdapter<RumpusUser> implements Serializer<RumpusUser> {

    @Override
    public void write(JsonWriter out, RumpusUser user) throws IOException {
        out.beginObject(); 
        out.name(Rumpus.USERNAME);
        out.value(user.getUsername());
        out.name(Rumpus.EMAIL);
        out.value(user.getEmail());
        out.name(Rumpus.PASSWORD);
        out.value(user.getPassword());

        // meta data
        out.name(MetaData.USER_CREATION_DATE_TIME);
        out.value(user.getMetaData().getStandardFormattedCreationTime());
        out.name(CommonUserMetaData.USER_PHOTO_LINK);
        out.value(user.getMetaData().getPhotoLink());
        out.name(CommonUserMetaData.USER_ABOUT_ME);
        out.value(user.getMetaData().getAboutMe());
        out.endObject();
    }

    @Override
    public RumpusUser read(JsonReader in) throws IOException {
        RumpusUser user = RumpusUser.createEmptyUser();
        RumpusUser.RumpusUserMetaData metaData = user.new RumpusUserMetaData();
        in.beginObject();
        String fieldname = null;

        while (in.hasNext()) {
            JsonToken token = in.peek();
            
            if (token.equals(JsonToken.NAME)) {
                //get the current token 
                fieldname = in.nextName(); 
            }
            if (Rumpus.USERNAME.equals(fieldname)) {
                //move to next token
                token = in.peek();
                user.setUsername(in.nextString());
            }
            if(Rumpus.EMAIL.equals(fieldname)) {
                //move to next token
                token = in.peek();
                user.setEmail(in.nextString());
            }

            // meta data
            if(MetaData.USER_CREATION_DATE_TIME.equals(fieldname)) {
                //move to next token
                token = in.peek();
                metaData.setCreationTime(Instant.parse(in.nextString()));
            }
            if(CommonUserMetaData.USER_PHOTO_LINK.equals(fieldname)) {
                token = in.peek();
                metaData.setPhotoLink(in.nextString());
            }
            if(CommonUserMetaData.USER_ABOUT_ME.equals(fieldname)) {
                token = in.peek();
                metaData.setAboutMe(in.nextString());
            }
        }
        user.setMetaData(metaData);
        in.endObject();
        return user;
    }

    @Override
    public void serialize(RumpusUser object, OutputStream outputStream) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(RumpusUser.class, new RumpusUserTypeAdapter()).create();
        gson.toJsonTree(object, RumpusUser.class);
    }
    
}
