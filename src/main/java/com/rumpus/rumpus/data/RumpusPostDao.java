package com.rumpus.rumpus.data;

import javax.sql.DataSource;

import com.rumpus.common.Dao.AbstractDao;
import com.rumpus.common.Dao.jdbc.AbstractApiDBJdbc;
import com.rumpus.common.Dao.jdbc.Mapper;
import com.rumpus.rumpus.models.RumpusPost;

public class RumpusPostDao extends RumpusDao<RumpusPost> implements IRumpusPostDao {

    private static final String NAME = "RumpusPostDao";
    private static final String TABLE = "rumpus_post";

    public RumpusPostDao(DataSource ds) {
        super(NAME, ds, TABLE, null);
        //TODO Auto-generated constructor stub
    }

    @Override
    public RumpusPost add(RumpusPost model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public RumpusPost update(String id, RumpusPost updatedModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    private final static Mapper<RumpusPost> rumpusPostMapper() {
        Mapper<RumpusPost> postMapper = new Mapper<>();
        postMapper.setMapFunc(
            (rs) -> {
                RumpusPost post = RumpusPost.create(CHANGE_PASSWORD, ADMIN, DELETE_GROUP_AUTHORITIES, ID, CREATE_AUTHORITY, UPDATE_USER, ID);
                // post.setId(rs.get("id"));
                // post.setUserId(rs.getInt("user_id"));
                // post.setPost(rs.getString("post"));
                // post.setCreatedAt(rs.getTimestamp("created_at"));
                // post.setUpdatedAt(rs.getTimestamp("updated_at"));
                return post;
            }
        );
        return null;
    }
}
