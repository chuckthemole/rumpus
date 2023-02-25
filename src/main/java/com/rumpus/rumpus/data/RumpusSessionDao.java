// package com.rumpus.rumpus.data;

// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.HashMap;

// import org.springframework.context.annotation.Profile;
// import org.springframework.stereotype.Repository;

// import com.rumpus.common.Mapper;
// import com.rumpus.common.Session.CommonSession;
// import com.rumpus.common.Session.CommonSessionRepository;
// import com.rumpus.common.util.Pair;

// @Repository
// @Profile("database")
// public class RumpusSessionDao extends CommonSessionRepository {

//     private static final String NAME = "RumpusSessionDao";
//     private static final String TABLE = "session";

//     public RumpusSessionDao() {
//         super(TABLE, NAME);
//     }

//     public static RumpusSessionDao create() {
//         return new RumpusSessionDao();
//     }

//     @Override
//     public Mapper<CommonSession> getMapper() {
//         LOG.info("RumpusSessionDao::getMapper()");
//         return mapper();
//     }

//     private final static Mapper<CommonSession> mapper() {
//         Mapper<CommonSession> mapper = new Mapper<>();
//         mapper.setMapFunc((Pair<ResultSet, Integer> resultSetAndRow) -> {
//             ResultSet rs = resultSetAndRow.getFirst();
//             // int row = resultSetAndRow.getSecond();
//             HashMap<String, String> map = new HashMap<>();
//             try {
//                 map.put("id", Integer.toString(rs.getInt("id")));
//                 map.put("last_accessed_time", (rs.getDate("last_accessed_time")).toString());
//                 map.put("max_inactive_interval", Integer.toString(rs.getInt("max_inactive_interval")));
//                 map.put("expired", Boolean.toString(rs.getBoolean("expired")));
//             } catch (SQLException e) {
//                 e.printStackTrace();
//             }
//             return CommonSession.createFromMap(map);
//         });
//         return mapper;
//     }
// }
