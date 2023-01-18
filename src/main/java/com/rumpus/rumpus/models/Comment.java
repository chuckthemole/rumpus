package com.rumpus.rumpus.models;

public class Comment extends RumpusModel<Comment> {
    private final int userId;
    private StringBuilder commentBuilder;
    private static final String MODEL_NAME = "commentModel";
    
    Comment(int userId) {
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
