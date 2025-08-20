package com.rumpus.buildshift.models.BuildShiftUser;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public class UserMetaData extends AbstractCommonUserMetaData<UserMetaData> {

    // private static final long serialVersionUID = RUMPUS_USER_META_DATA_UID;

    /**
     * TODO: think about this placement. Can we put this in the AbstractMetaData
     * class?
     */
    transient private static final UserMetaDataSerializer SERIALIZER = new UserMetaDataSerializer();

    // TODO: add more member variables for specific meta data here

    private UserMetaData(List<Map<String, String>> metaList) {
        super(null, null, null);
    }

    private UserMetaData(Map<String, String> metaMap) {
        super(null, null, null);
    }

    private UserMetaData(ObjectInputStream stream) {
        super(null, null, null);
        try {
            this.readObject(stream);
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            LOG("TODO: catch error in UserMetaData");
            LOG(e.getMessage());
        }
    }

    // factory static ctors
    public static UserMetaData createEmpty() {
        return new UserMetaData(List.of());
    }

    public static UserMetaData createFromListOfMaps(List<Map<String, String>> metaList) {
        return new UserMetaData(metaList);
    }

    public static UserMetaData createFromMap(Map<String, String> metaMap) {
        return new UserMetaData(metaMap);
    }

    public static UserMetaData createFromStream(ObjectInputStream stream) {
        return new UserMetaData(stream);
    }

    // overriding these serializer methods here. right now just using defaults but
    // can customize as commented out below. 2023/6/28
    protected void writeObject(ObjectOutputStream out) throws IOException {
        LOG("UserMetaData::writeObject()");
        out.defaultWriteObject();
    }

    protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        LOG("UserMetaData::readObject()");
        in.defaultReadObject();
    }

    @Override
    public Map<String, Object> getMetaAttributesMap() {
        LOG("UserMetaData::getModelAttributesMap()");
        Map<String, Object> modelAttributesMap = Map.of(
                USER_CREATION_DATE_TIME, (String) this.getStandardFormattedCreationTime(),
                USER_PHOTO_LINK, (String) this.getPhotoLink(),
                USER_ABOUT_ME, (String) this.getAboutMe());
        return modelAttributesMap;
    }
}
