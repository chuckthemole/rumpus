package com.rumpus.rumpus.models;

import java.util.Map;
import java.util.function.Supplier;

public class Post extends RumpusModel<Post> {
    private final String userId;
    private StringBuilder commentBuilder;
    private static final String MODEL_NAME = "postModel";
    
    Post(Map<String, String> attributeMap) {
        super(MODEL_NAME);
        if(attributeMap.containsKey("user_id")) {
            this.userId = attributeMap.get("user_id");
        } else {
            this.userId = NO_ID;
        }
    }

    public static Post create(Map<String, String> attributeMap) {
        return new Post(attributeMap);
    }

    public static Supplier<Post> createFunction(Map<String, String> attributeMap) {
        return () -> {
            Post post = create(attributeMap);
            return post;
        };
    }

    public void setComment(String newComment) {
        commentBuilder = new StringBuilder();
        commentBuilder.append(newComment);
    }

    public String getComment() {
        return commentBuilder.toString();
    }

    public String getUserId() {
        return userId;
    }
}
