package com.rumpus.rumpus.models;

import java.util.Map;
import java.util.function.Supplier;

public class Post extends RumpusModel<Post> {
    private final Long userId;
    private StringBuilder commentBuilder;
    private static final String MODEL_NAME = "postModel";
    
    Post(Map<String, String> initMap) {
        super(MODEL_NAME);
        if(initMap.containsKey("user_id")) {
            this.userId = Long.valueOf(initMap.get("user_id"));
        } else {
            this.userId = NO_ID;
        }
    }

    public static Post create(Map<String, String> initMap) {
        return new Post(initMap);
    }

    public static Supplier<Post> createFunction(Map<String, String> initMap) {
        return () -> {
            Post post = create(initMap);
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

    public Long getUserId() {
        return userId;
    }
}
