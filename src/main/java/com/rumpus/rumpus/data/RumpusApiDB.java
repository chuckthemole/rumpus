// package com.rumpus.rumpus.data;

// import java.util.function.Function;

// import com.rumpus.common.Mapper;
// import com.rumpus.common.ApiDB.ApiJdbc;
// import com.rumpus.rumpus.models.RumpusModel;

// public class RumpusApiDB<T extends RumpusModel<T>> implements IRumpusApiDB<T> {

//     protected ApiJdbc<T> api; // implementing JdbcTemplate, can change here to implement another.

//     public RumpusApiDB(String table, Mapper<T> mapper) {
//         api = new ApiJdbc<>(table, mapper);
//     }
    
// }
