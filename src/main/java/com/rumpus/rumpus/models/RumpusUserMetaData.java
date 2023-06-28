package com.rumpus.rumpus.models;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public class RumpusUserMetaData extends AbstractCommonUserMetaData<RumpusUserMetaData> {

    private static final long serialVersionUID = RUMPUS_USER_META_DATA_UID;
    private static final String NAME = "RumpusUserMetaData";

    // TODO: add more member variables for specific meta data here

    public RumpusUserMetaData() {
        super(NAME);
        this.setTypeAdapter(createTypeAdapter());
    }
    public RumpusUserMetaData(List<Map<String, String>> metaList) {
        super(NAME);
        this.setTypeAdapter(createTypeAdapter());
        this.init(metaList);
    }
    public RumpusUserMetaData(Map<String, String> metaList) {
        super(NAME);
        this.setTypeAdapter(createTypeAdapter());
        this.init(List.of(metaList));
    }

    private void init(List<Map<String, String>> metaList) {
        LOG.info("RumpusUserMetaData::init()");
        if(metaList == null || metaList.isEmpty()) {
            LOG.info("Error: empty list given for init of RumpusUserMetaData.");
        } else {
            for(Map<String, String> map : metaList) {
                LOG.info("New Map: \n");
                map.forEach((key, value) -> {
                    LogBuilder.logBuilderFromStringArgs("  ", key, value, "\n").info();;
                });
            }
        }
        
    }

    // overriding these serializer methods here. right now just using defaults but can customize as commented out below. 2023/6/28
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        LOG.info("RumpusUserMetaData::writeObject()");
        out.defaultWriteObject();
        // out.writeObject(this.getCreationTime());
        // out.writeChars(this.photoLink);
        // out.writeChars(this.aboutMe);
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        LOG.info("RumpusUserMetaData::readObject()");
        in.defaultReadObject();
        // try {
        //     this.creationTime = (Instant) stream.readObject();
        //     this.photoLink = (String) stream.readObject();
        //     this.aboutMe = (String) stream.readObject();
        // } catch (ClassNotFoundException e) {
        //     LogBuilder.logBuilderFromStringArgs(e.getMessage()).error();
        //     LogBuilder.logBuilderFromStackTraceElementArray(e.getStackTrace()).error();
        // } catch (IOException e) {
        //     LogBuilder.logBuilderFromStringArgs(e.getMessage()).error();
        //     LogBuilder.logBuilderFromStackTraceElementArray(e.getStackTrace()).error();
        // }
    }

    @Override
    public TypeAdapter<RumpusUserMetaData> createTypeAdapter() {
        return new TypeAdapter<RumpusUserMetaData>() {

            @Override
            public void write(JsonWriter out, RumpusUserMetaData userMetaData) throws IOException {
                out.beginObject(); 
                out.name(USER_CREATION_DATE_TIME);
                out.value(userMetaData.getStandardFormattedCreationTime());
                out.name(USER_PHOTO_LINK);
                out.value(userMetaData.getPhotoLink());
                out.name(USER_ABOUT_ME);
                out.value(userMetaData.getAboutMe());
                out.endObject();
            }

            @Override
            public RumpusUserMetaData read(JsonReader in) throws IOException {
                RumpusUserMetaData userMetaData = new RumpusUserMetaData();
                in.beginObject();
                String fieldname = null;

                while (in.hasNext()) {
                    JsonToken token = in.peek();
                    
                    if (token.equals(JsonToken.NAME)) {
                        //get the current token 
                        fieldname = in.nextName(); 
                    }
                    
                    if (USER_CREATION_DATE_TIME.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        userMetaData.setCreationTime(in.nextString());
                    }
                    
                    if(USER_PHOTO_LINK.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        userMetaData.setPhotoLink(in.nextString());
                    }

                    if(USER_ABOUT_ME.equals(fieldname)) {
                        //move to next token
                        token = in.peek();
                        userMetaData.setAboutMe(in.nextString());
                    }
                }
                in.endObject();
                return userMetaData;
            }
            
        };
    }

    @Override
    public void serialize(RumpusUserMetaData object, OutputStream outputStream) throws IOException {
        LOG.info("RumpusUserMetaData::serialize()");
        this.getTypeAdapter().write(new JsonWriter(new OutputStreamWriter(outputStream)), object);
    }

    @Override
    public Map<String, Object> getMetaAttributesMap() {
        LOG.info("RumpusUserMetaData::getModelAttributesMap()");
        Map<String, Object> modelAttributesMap = Map.of(
            USER_CREATION_DATE_TIME, (String) this.getStandardFormattedCreationTime(),
            USER_PHOTO_LINK, (String) this.getPhotoLink(),
            USER_ABOUT_ME, (String) this.getAboutMe());
        return modelAttributesMap;
    }
}
