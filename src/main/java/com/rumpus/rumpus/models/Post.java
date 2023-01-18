package com.rumpus.rumpus.models;

public class Post extends RumpusModel<Post> {
    private final int userId;
    private StringBuilder commentBuilder;
    private static final String MODEL_NAME = "postModel";
    
    Post(int userId) {
        super(MODEL_NAME);
        this.userId = userId;
    }

    public static Comment createComment(int userId) {
        return new Comment(userId);
    }

    public void setComment(String newComment) {
        commentBuilder = new StringBuilder();
        commentBuilder.append(newComment);
    }

    public String getComment() {
        return commentBuilder.toString();
    }
}
