package com.rumpus.rumpus.gson.Adapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Rumpus;
import com.rumpus.rumpus.models.RumpusUser;

public class RumpusUserAdapter extends Rumpus {
    public class CommonUserAdapter extends TypeAdapter<RumpusUser> {

        @Override
        public void write(JsonWriter out, RumpusUser user) throws IOException {
            out.beginObject(); 
            out.name(USERNAME);
            out.value(user.getUsername());
            out.name(EMAIL);
            out.value(user.getEmail());
            out.name(PASSWORD);
            out.value(user.getPassword());
            out.endObject();
        }

        @Override
        public RumpusUser read(JsonReader in) throws IOException {
            RumpusUser user = RumpusUser.createEmptyUser();
            in.beginObject();
            String fieldname = null;

            while (in.hasNext()) {
                JsonToken token = in.peek();
                
                if (token.equals(JsonToken.NAME)) {
                    //get the current token 
                    fieldname = in.nextName(); 
                }
                
                if (USERNAME.equals(fieldname)) {
                    //move to next token
                    token = in.peek();
                    user.getUserDetails().setUsername(in.nextString());
                }
                
                if(EMAIL.equals(fieldname)) {
                    //move to next token
                    token = in.peek();
                    user.setEmail(in.nextString());
                }
            }
            in.endObject();
            return user;
        }
    }
}
