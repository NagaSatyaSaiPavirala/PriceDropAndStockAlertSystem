//package com.pricedrop.dao;
//
//import com.datastax.oss.driver.api.core.cql.Row;
//import com.datastax.oss.driver.api.core.cql.SimpleStatement;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.cassandra.core.CassandraOperations;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class SequenceGeneratorRepository {
//
//    @Autowired
//    private CassandraOperations cassandraTemplate;
//
//    private int getNextSequenceValue(String tableName) {
//        // Atomically increment the sequence value
//        String updateQuery = "UPDATE " + tableName + " SET id_counter = id_counter + 1";
//        cassandraTemplate.execute(SimpleStatement.newInstance(updateQuery));
//
//        // Retrieve the updated sequence value
//        String selectQuery = "SELECT id_counter FROM " + tableName + " LIMIT 1";
//        Row row = cassandraTemplate.getCqlOperations().queryForObject(selectQuery, Row.class);
//
//        return (row != null) ? row.getInt("id_counter") : 1; // Default to 1 if null
//    }
//
//    public int getNextProductApiId() {
//        return getNextSequenceValue("productapi_seq");
//    }
//
//    public int getNextProductId() {
//        return getNextSequenceValue("product_seq");
//    }
//
//    public int getNextUserId() {
//        return getNextSequenceValue("user_seq");
//    }
//
//    public int getNextUrlId() {
//        return getNextSequenceValue("url_seq");
//    }
//}





/*
package com.pricedrop.dao;


import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceGeneratorRepository {

    @Autowired
    private CassandraOperations cassandraTemplate;

    private int getNextSequenceValue(String seqName) {
        // Increment sequence value atomically
        String updateQuery = "UPDATE sequence_table SET id_counter = id_counter + 1 WHERE id_name = ?";
        cassandraTemplate.execute(SimpleStatement.newInstance(updateQuery, seqName));

        // Retrieve updated sequence value
        String selectQuery = "SELECT id_counter FROM sequence_table WHERE id_name = ? LIMIT 1";
        Row row = cassandraTemplate.getCqlOperations().queryForObject(selectQuery, Row.class, seqName);

        return (row != null) ? row.getInt("id_counter") : 1; // Default to 1 if null
    }

    public int getNextProductApiId() {
        return getNextSequenceValue("productapi");
    }

    public int getNextProductId() {
        return getNextSequenceValue("product");
    }

    public int getNextUserId() {
        return getNextSequenceValue("user");
    }

    public int getNextUrlId() {
        return getNextSequenceValue("url");
    }
}
*/


//package com.pricedrop.dao;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import com.datastax.oss.driver.api.core.cql.ResultSet;
//import com.datastax.oss.driver.api.core.cql.Row;
//import com.datastax.oss.driver.api.core.cql.SimpleStatement;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.cassandra.core.CassandraTemplate;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class SequenceGeneratorRepository {
//
//    @Autowired
//    private CassandraTemplate cassandraTemplate;
//
//    @Autowired
//    private CqlSession session;
//
//    private int getNextSequenceValue(String seqName) {
//        // Step 1: Increment sequence value atomically
//        String updateQuery = "UPDATE sequence_table SET id_counter = id_counter + 1 WHERE id_name = ?";
//        cassandraTemplate.getCqlOperations().execute(updateQuery, seqName);
//
//        // Step 2: Retrieve updated sequence value
//        String selectQuery = "SELECT id_counter FROM sequence_table WHERE id_name = ? ALLOW FILTERING";
//        Row row = cassandraTemplate.getCqlOperations().queryForObject(selectQuery, Row.class, seqName);
//
//        return (row != null) ? row.getInt("id_counter") : 1; // Default to 1 if null
//    }
//
//    public int getNextProductApiId() {
//        return getNextSequenceValue("productapi");
//    }
//
////    public int getNextProductId() {
////        return getNextSequenceValue("product");
////    }
//
////    public int getNextProductId() {
////        // Increment and retrieve the updated counter
////        String query = "UPDATE sequence_table SET id_counter = id_counter + 1 WHERE id_name = 'product' IF EXISTS";
////        cassandraTemplate.execute(query);
////
////        // Fetch the latest counter value
////        String selectQuery = "SELECT id_counter FROM sequence_table WHERE id_name = 'product'";
////        Integer newId = cassandraTemplate.selectOne(selectQuery, Integer.class);
////
////        if (newId == null) {
////            throw new RuntimeException("Failed to generate product ID");
////        }
////        return newId;
////    }
//
//    public int getNextProductId() {
//        // Increment the counter in sequence_table
//        SimpleStatement updateStmt = SimpleStatement.builder(
//                "UPDATE sequence_table SET id_counter = id_counter + 1 WHERE id_name = 'product' IF EXISTS"
//        ).build();
//        session.execute(updateStmt); // Execute the update statement
//
//        // Fetch the updated counter
//        SimpleStatement selectStmt = SimpleStatement.builder(
//                "SELECT id_counter FROM sequence_table WHERE id_name = 'product'"
//        ).build();
//        ResultSet resultSet = session.execute(selectStmt);
//
//        Row row = resultSet.one();
//        if (row == null) {
//            throw new RuntimeException("Failed to generate product ID: No row found.");
//        }
//
//        return row.getInt("id_counter");
//    }
//
//    public int getNextUserId() {
//        return getNextSequenceValue("user");
//    }
//
//    public int getNextUrlId() {
//        return getNextSequenceValue("url");
//    }
//}



package com.pricedrop.dao;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceGeneratorRepository {

    @Autowired
    private CqlSession session;

    public synchronized int getNextSequenceValue(String seqName) {
        // Step 1: Retrieve current value
        SimpleStatement selectStmt = SimpleStatement.builder(
                "SELECT id_counter FROM sequence_table WHERE id_name = ?"
        ).addPositionalValues(seqName).build();

        ResultSet resultSet = session.execute(selectStmt);
        Row row = resultSet.one();

        int newId;
        if (row == null) {
            // Row does not exist; create it with default value 1
            newId = 1;
            session.execute(SimpleStatement.builder(
                    "INSERT INTO sequence_table (id_name, id_counter) VALUES (?, ?)"
            ).addPositionalValues(seqName, newId).build());
        } else {
            // Step 2: Increment ID
            int currentId = row.getInt("id_counter");
            newId = currentId + 1;

            // Step 3: Update the counter
            session.execute(SimpleStatement.builder(
                    "UPDATE sequence_table SET id_counter = ? WHERE id_name = ?"
            ).addPositionalValues(newId, seqName).build());
        }

        return newId;
    }

//    public int getNextProductApiId() {
//        return getNextSequenceValue("productapi");
//    }

    public int getNextProductId() {
        return getNextSequenceValue("product");
    }

    public int getNextUserId() {
        return getNextSequenceValue("user");
    }

//    public int getNextUrlId() {
//        return getNextSequenceValue("url");
//    }
}
