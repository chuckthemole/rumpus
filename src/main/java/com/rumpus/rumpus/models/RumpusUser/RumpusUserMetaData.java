package com.rumpus.rumpus.models.RumpusUser;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public class RumpusUserMetaData extends AbstractCommonUserMetaData<RumpusUserMetaData> {

    private static final long serialVersionUID = RUMPUS_USER_META_DATA_UID;
    transient private static final String NAME = "RumpusUserMetaData";

    /**
     * TODO: think about this placement. Can we put this in the AbstractMetaData class?
     */
    transient private static final RumpusUserMetaDataSerializer SERIALIZER = new RumpusUserMetaDataSerializer();

    // TODO: add more member variables for specific meta data here

    // ctors
    private RumpusUserMetaData(List<Map<String, String>> metaList) {
        super(NAME);
        this.init(metaList);
    }

    private RumpusUserMetaData(Map<String, String> metaMap) {
        super(NAME);
        this.init(List.of(metaMap));
    }

    private RumpusUserMetaData(ObjectInputStream stream) {
        super(NAME);
        try {
            this.readObject(stream);
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            LOG("TODO: catch error in RumpusUserMetaData");
            LOG(e.getMessage());
        }
    }

    // factory static ctors
    public static RumpusUserMetaData createEmpty() {
        return new RumpusUserMetaData(List.of());
    }
    public static RumpusUserMetaData createFromListOfMaps(List<Map<String, String>> metaList) {
        return new RumpusUserMetaData(metaList);
    }
    public static RumpusUserMetaData createFromMap(Map<String, String> metaMap) {
        return new RumpusUserMetaData(metaMap);
    }
    public static RumpusUserMetaData createFromStream(ObjectInputStream stream) {
        return new RumpusUserMetaData(stream);
    }

    // TODO: this isn't really doing anything right now. 2024/1/22 - chuck
    // it's just printing out the metaList that is passed in
    private void init(List<Map<String, String>> metaList) {
        LogBuilder.logBuilderFromStringArgs(RumpusUserMetaData.class, "RumpusUserMetaData::init()").info();
        if(metaList == null) {
            LogBuilder.logBuilderFromStringArgsNoSpaces(RumpusUserMetaData.class, "Provided metaList is null").info();
        } else if(metaList.isEmpty()) {
            LogBuilder.logBuilderFromStringArgsNoSpaces(RumpusUserMetaData.class, "Provided metaList is empty").info();
        } else {
            for(Map<String, String> map : metaList) {
                LogBuilder.logBuilderFromStringArgs(RumpusUserMetaData.class, "New Map:").info();
                map.forEach((key, value) -> {
                    LogBuilder.logBuilderFromStringArgs(RumpusUserMetaData.class, "  ", key, value, "\n").info();;
                });
            }
        }
    }

    // overriding these serializer methods here. right now just using defaults but can customize as commented out below. 2023/6/28
    protected void writeObject(java.io.ObjectOutputStream out) throws IOException {
        LOG("RumpusUserMetaData::writeObject()");
        out.defaultWriteObject();
        // out.writeObject(this.getCreationTime());
        // out.writeChars(this.photoLink);
        // out.writeChars(this.aboutMe);
    }
    protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        LOG("RumpusUserMetaData::readObject()");
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
    public Map<String, Object> getMetaAttributesMap() {
        LOG("RumpusUserMetaData::getModelAttributesMap()");
        Map<String, Object> modelAttributesMap = Map.of(
            USER_CREATION_DATE_TIME, (String) this.getStandardFormattedCreationTime(),
            USER_PHOTO_LINK, (String) this.getPhotoLink(),
            USER_ABOUT_ME, (String) this.getAboutMe());
        return modelAttributesMap;
    }
}
