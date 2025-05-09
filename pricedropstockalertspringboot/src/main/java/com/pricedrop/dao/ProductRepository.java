//package com.pricedrop.dao;
//
//import com.pricedrop.entities.Product;
//import com.pricedrop.entities.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Component;
//
//@Component
//public interface ProductRepository extends JpaRepository<Product,Integer> {
//    @Query("delete from Product p where p.user.u_id = :pid")
//    void deleteProductOfUser(@Param("pid") int pid);
//
//    @Query("SELECT p FROM Product p JOIN FETCH p.user WHERE p.p_id = :pId")
//    Product findProductWithUser(int pId);
//
//}



//package com.pricedrop.dao;
//
//import com.pricedrop.entities.Product;
//import org.springframework.data.cassandra.repository.CassandraRepository;
//import org.springframework.data.cassandra.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ProductRepository extends CassandraRepository<Product, Integer> {
//
//    @Query("DELETE FROM product WHERE user_id = ?0")
//    void deleteProductOfUser(int userId);
//
//    @Query("SELECT * FROM product WHERE p_id = ?0 ALLOW FILTERING")
//    Product findProductById(int pId);
//
//    // Add this method to fetch all products of a specific user
//    @Query("SELECT * FROM product WHERE user_id = ?0 ALLOW FILTERING")
//    List<Product> findByUserId(int userId);
//
//    @Query("SELECT * FROM product WHERE user_id = ?0 ALLOW FILTERING")
//    List<Product> findProductsByUserId(int userId);
//
//    @Query("DELETE FROM product WHERE user_id = ?0 AND p_id = ?1")
//    void deleteProductByUserIdAndProductId(int userId, int productId);
//
//   // boolean existsByKey(ProductKey key);  // Custom method to check if the product exists by composite key
//
//}




package com.pricedrop.dao;

import com.pricedrop.entities.Product;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CassandraRepository<Product, Integer> {

//    @Query("DELETE FROM product WHERE user_id = ?0")
//    void deleteProductOfUser(int userId);

//    @Query("SELECT * FROM product WHERE p_id = ?0")
//    Product findProductById(int pId);


    @Query("SELECT * FROM product WHERE p_id = ?0")
    List<Product> findProductById(int pId);


    @Query("SELECT * FROM product WHERE p_id = ?0 AND t_price = ?1")
    Product findProductByIdAndPrice(int pId, BigDecimal tPrice);


    // Add this method to fetch all products of a specific user
    @Query("SELECT * FROM product WHERE user_id = ?0")
    List<Product> findByUserId(int userId);

    @Query("SELECT * FROM product WHERE user_id = ?0")
    List<Product> findProductsByUserId(int userId);

    @Query("DELETE FROM product WHERE p_id = ?0 AND t_price = ?1")
    void deleteProductByIdAndPrice(int p_id, BigDecimal t_price);

    //    @Query("DELETE FROM product WHERE user_id = ?0 AND p_id = ?1")
//    void deleteProductByUserIdAndProductId(int userId, int productId);

//    @Query("DELETE FROM product WHERE p_id = ?1 AND t_price = ?2")
//    void deleteProductByIdAndPrice(int pId, double tPrice);

//    @Modifying
//    @Query("UPDATE product SET p_name = :pName, t_price = :price WHERE p_id = :pId")
//    void updateProduct(@Param("pId") int productId, @Param("pName") String productName, @Param("price") BigDecimal price);

//    @Modifying
//    @Query("UPDATE product SET p_name = ?1, t_price = ?2 WHERE p_id = ?0")
//    void updateProduct(int pId, String pName, BigDecimal price);

//    @Modifying
//    @Query("INSERT INTO product (p_id, p_name, t_price, p_url, user_id) VALUES (?0, ?1, ?2, ?3, ?4)")
//    void insertProduct(int pId, String pName, BigDecimal tPrice, String pUrl, int userId);




//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Product p WHERE p.p_id = :p_id AND p.t_price = :t_price")
//    void deleteProductByIdAndPrice(@Param("p_id") int p_id, @Param("t_price") BigDecimal t_price);



    // boolean existsByKey(ProductKey key);  // Custom method to check if the product exists by composite key

}