package com.rumpus.rumpus.gson;

import com.rumpus.common.CommonGson;
import com.rumpus.rumpus.gson.Adapters.RumpusUserAdapter;
import com.rumpus.rumpus.models.RumpusUser;

public class RumpusGson extends CommonGson {

    public RumpusGson() {
        init();
    }

    private void init() {
        CommonGson.gsonBuilder.registerTypeAdapter(RumpusUser.class, new RumpusUserAdapter());
    }
}
