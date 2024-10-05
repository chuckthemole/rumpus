package com.rumpus.rumpus.models.RumpusUser;

import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.ICommon;
import com.rumpus.common.Model.AbstractMetaData;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.AbstractCommonUserSerializer;

public class RumpusUserSerializer extends AbstractCommonUserSerializer<RumpusUser, RumpusUserMetaData> {

    private RumpusUserSerializer(SerializationType serializationType) {
        super("RumpusUserSerializer", serializationType);
    }

    public static RumpusUserSerializer jsonSerializer() {
        return new RumpusUserSerializer(SerializationType.JSON);
    }

    @Override
    public void writeJson(JsonWriter out, RumpusUser object) throws IOException {
        out.beginObject(); 
        out.name(ICommon.USERNAME);
        out.value(object.getUsername());
        out.name(ICommon.EMAIL);
        out.value(object.getEmail());
        out.name(ICommon.PASSWORD);
        out.value(object.getPassword());

        // meta data
        out.name(AbstractMetaData.USER_CREATION_DATE_TIME);
        out.value(object.getMetaData().getStandardFormattedCreationTime());
        out.name(AbstractCommonUserMetaData.USER_PHOTO_LINK);
        out.value(object.getMetaData().getPhotoLink());
        out.name(AbstractCommonUserMetaData.USER_ABOUT_ME);
        out.value(object.getMetaData().getAboutMe());
        out.endObject();
    }

    @Override
    public RumpusUser readJson(JsonReader in) throws IOException {
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
}
