package com.rumpus.rumpus.models;

import java.util.UUID;

import com.rumpus.common.Model.IModelIdManager;
import com.rumpus.common.Model.SqlIdManager;

import com.rumpus.common.Forum.ForumPost;

/**
 * RumpusPost
 * 
 * Represents a post in a Rumpus thread.
 * TODO: This class can be abstracted. We can create a CommonPost class that can be used by both Rumpus and RumpusCommon.
 * Actually look in Forum in common and see if we can use that.
 * 
 * UPDATE: I've already created a package Forum and elements like {@link ForumPost}
 */
public class RumpusPost extends RumpusModel<RumpusPost> {
    private static SqlIdManager idManager;
    private String authorID;
    private String threadID;
    private String created;
    private String updated;
    private String title;
    private String content;
    private String parentPostID;

    static {
        RumpusPost.idManager = new SqlIdManager();
    }

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

    public static RumpusPost createEmpty() {
        return new RumpusPost();
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
    public int compareTo(RumpusPost o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
    @Override
    public IModelIdManager<UUID> getIdManager() {
        return RumpusPost.idManager;
    }
}
