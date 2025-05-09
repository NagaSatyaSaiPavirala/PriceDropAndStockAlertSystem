//package com.pricedrop.admin.dao;
//
//import com.pricedrop.admin.entities.Productapi;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public interface ApiRepository extends JpaRepository<Productapi,Integer> {
//    @Query("SELECT p FROM Productapi p WHERE p.product_name = :productName")
//    Optional<Productapi> findByProductName(String productName);
////    @Query("SELECT p FROM Productapi p WHERE p.product_url = :productUrl")
////    Productapi findByProductUrl(String productUrl);
//
//    @Query("SELECT p FROM Productapi p WHERE p.product_url LIKE %:productUrl%")
//    Productapi findByProductUrl(String productUrl);
//
//    @Query("SELECT p FROM Productapi p WHERE p.product_name LIKE %:productName%")
//    List<Productapi> findByProductNameContains(@Param("productName") String productName);
//
////    public List<Productapi> findByProduct_nameContaining(String product);
//
//}




//package com.pricedrop.admin.dao;
//
//import com.pricedrop.admin.entities.Productapi;
//import org.springframework.data.cassandra.repository.CassandraRepository;
//import org.springframework.data.cassandra.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ApiRepository extends CassandraRepository<Productapi, Integer> {
//
//    Optional<Productapi> findByProductName(String product_name);
//
//    @Query("SELECT * FROM productapi WHERE product_url CONTAINS ?0 ALLOW FILTERING")
//    Productapi findByProductUrl(String productUrl);
//
//    @Query("SELECT * FROM productapi WHERE product_name CONTAINS ?0 ALLOW FILTERING")
//    List<Productapi> findByProductNameContains(String productName);
//}


package com.pricedrop.admin.dao;
import com.pricedrop.admin.entities.Productapi;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApiRepository extends CassandraRepository<Productapi, Integer> {  // Fix primary key type

    Optional<Productapi> findByProductName(String product_name);  // Fix field name

    @Query("SELECT * FROM productapi WHERE product_url = ?0 ALLOW FILTERING")  // Remove CONTAINS
    Productapi findByProductUrl(String productUrl);

//    @Query("SELECT p FROM Productapi p WHERE p.product_url = :productUrl")
//    List<Productapi> findByProductUrl(String productUrl);


    @Query("SELECT * FROM productapi WHERE product_name LIKE ?0 ALLOW FILTERING")  // Replace CONTAINS with LIKE
    List<Productapi> findByProductNameContains(String productName);
}
