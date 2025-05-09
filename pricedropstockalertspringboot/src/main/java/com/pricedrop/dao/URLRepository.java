//package com.pricedrop.dao;
//
//import com.pricedrop.model.URL;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
////below for cassandra
////import com.satya.app.urldataextractor.model.URL;
////import org.springframework.data.cassandra.repository.AllowFiltering;
////import org.springframework.data.cassandra.repository.CassandraRepository;
////import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//public interface URLRepository extends JpaRepository<URL,String>{
////    @Query("SELECT u FROM URL u WHERE u.url=?1")
//    //URL findByUrl(String url);
//    Optional<URL> findByUrl(String url);
//}
////@Repository
////public interface URLRepository extends CassandraRepository<URL,String>
////{
////    @AllowFiltering
////    Optional<URL> findByUrl(String url);//use camelCase
////    @AllowFiltering
////    Optional<URL> findById(String id);
////}




package com.pricedrop.dao;

import com.pricedrop.model.URL;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends CassandraRepository<URL, String> {

    @AllowFiltering // Required for filtering since Cassandra does not support WHERE clauses on non-primary keys
    Optional<URL> findByUrl(String url);

    @AllowFiltering
    Optional<URL> findById(String id);
}
