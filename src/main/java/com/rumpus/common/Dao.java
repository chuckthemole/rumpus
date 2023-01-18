package com.rumpus.common;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class Dao<T extends Model<T>> extends RumpusObject implements IDao<T> {

    private final static String NAME = "rawDao";
    protected static JdbcTemplate jdbcTemplate; // maybe abstract this to allow for other implementations
    protected final String table;
    protected final String idName;
    protected final Mapper<T> mapper;
    protected final Function<T, T> add;

    static {
        jdbcTemplate = new JdbcTemplate();
    }

    public Dao(String table, String idName, Mapper<T> mapper, Function<T, T> add) {
        super(NAME);
        this.mapper = mapper;
        this.add = add;
        this.table = table;
        this.idName = idName;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        Dao.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean remove(int id) {
        // TODO: Check dependencies to delete
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ")
            .append(table)
            .append(" WHERE ")
            .append(id)
            .append(" = ?;");
        final String sql = sb.toString();
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public T get(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
            .append(table)
            .append(" WHERE ")
            .append(id)
            .append(" = ?;");
        final String sql = sb.toString();
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public List<T> getAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(table).append(";");
        final String sql = sb.toString();
        List<T> objects = jdbcTemplate.query(sql, mapper);
        return objects;
    }

    @Override
    public T add(T model) {
        return add.apply(model);
    }

    @Override
    public String getTable() {
        return this.table;
    }

    @Override
    public Mapper<T> getMapper() {
        return this.mapper;
    }

    @Override
    public Function<T, T> getAddFunction() {
        return this.add;
    }
}
