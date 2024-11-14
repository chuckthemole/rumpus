package com.rumpus.rumpus.models;

import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Model.AbstractModel.AbstractModelSerializer;

public class RumpusPostSerializer extends AbstractModelSerializer<RumpusPost> {

    public RumpusPostSerializer() {
        super(SerializationType.JSON);
    }

    @Override
    protected void writeJson(JsonWriter out, RumpusPost object) throws IOException {
        out.beginObject(); 
        out.name("title");
        out.value(object.getTitle());
        out.name("content");
        out.value(object.getContent());
        out.name("author");
        out.value(object.getAuthorID());
        out.name("threadID");
        out.value(object.getThreadID());
        out.name("created");
        out.value(object.getCreated());
        out.name("updated");
        out.value(object.getUpdated());
        out.name("parentPostID");
        out.value(object.getParentPostID());
        out.endObject();
    }

    @Override
    protected RumpusPost readJson(JsonReader in) throws IOException {
        RumpusPost post = RumpusPost.createEmpty();
        in.beginObject();

        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "title":
                    post.setTitle(in.nextString());
                    break;
                case "content":
                    post.setContent(in.nextString());
                    break;
                case "author":
                    post.setAuthorID(in.nextString());
                    break;
                case "threadID":
                    post.setThreadID(in.nextString());
                    break;
                case "created":
                    post.setCreated(in.nextString());
                    break;
                case "updated":
                    post.setUpdated(in.nextString());
                    break;
                case "parentPostID":
                    post.setParentPostID(in.nextString());
                    break;
            }
        }

        in.endObject();
        return post;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
    
}
