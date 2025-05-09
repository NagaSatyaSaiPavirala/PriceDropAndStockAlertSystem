//package com.pricedrop.dao;
//
//import com.pricedrop.entities.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Component;
//
//@Component
//public interface UserRepository extends JpaRepository<User,Integer> {
//    @Query("select u from User u  where u.email = :email ")
//    public User getUserByUserName(@Param("email") String email);
//}




package com.pricedrop.dao;

import com.pricedrop.entities.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<User, Integer> {

    @Query("SELECT * FROM user WHERE email = ?0 ALLOW FILTERING")
    User getUserByUserName(String email);


}
