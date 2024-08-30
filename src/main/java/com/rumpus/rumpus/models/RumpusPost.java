package com.rumpus.rumpus.models;

import java.io.IOException;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class RumpusPost extends RumpusModel<RumpusPost> {
    private String authorID;
    private String threadID;
    private String created;
    private String updated;
    private String title;
    private String content;
    private String parentPostID;

    private RumpusPost() {
        super("RumpusPost");
    }
    private RumpusPost(
        String title,
        String content,
        String authorID,
        String threadID,
        String created,
        String updated,
        String parentPostID) {
            super("RumpusPost");
            this.title = title;
            this.content = content;
            this.authorID = authorID;
            this.threadID = threadID;
            this.created = created;
            this.updated = updated;
            this.parentPostID = parentPostID;
    }

    public static RumpusPost create(
        String title,
        String content,
        String authorID,
        String threadID,
        String created,
        String updated,
        String parentPostID) {
            return new RumpusPost(title, content, authorID, threadID, created, updated, parentPostID);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getParentPostID() {
        return parentPostID;
    }

    public void setParentPostID(String parentPostID) {
        this.parentPostID = parentPostID;
    }

    @Override
    public TypeAdapter<RumpusPost> createTypeAdapter() {
        return new TypeAdapter<RumpusPost>() {
            @Override
            public void write(JsonWriter out, RumpusPost post) throws IOException {
                out.beginObject(); 
                out.name("title");
                out.value(post.getTitle());
                out.name("content");
                out.value(post.getContent());
                out.name("author");
                out.value(post.getAuthorID());
                out.name("threadID");
                out.value(post.getThreadID());
                out.name("created");
                out.value(post.getCreated());
                out.name("updated");
                out.value(post.getUpdated());
                out.name("parentPostID");
                out.value(post.getParentPostID());
                out.endObject();
            }

            @Override
            public RumpusPost read(JsonReader in) throws IOException {
                RumpusPost post = new RumpusPost();
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
        };
    }

    @Override
    public Map<String, Object> getModelAttributesMap() {
        LOG("Post::getModelAttributesMap()");
        return Map.of(
            "title", this.title,
            "content", this.content,
            "author", this.authorID,
            "threadID", this.threadID,
            "created", this.created,
            "updated", this.updated,
            "parentPostID", this.parentPostID
        );
    }
}
