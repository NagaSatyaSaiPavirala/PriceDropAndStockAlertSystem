package com.pricedrop.dao;

import com.pricedrop.model.PageInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends MongoRepository<PageInfo, String> {

    //@Query("{urlId : ?0}")
    @Query("{productUrlId : ?0}")
    //PageInfo findByUrlId(String id);
    //Optional<PageInfo> findFirstByProductUrlIdOrderByCreatedDateDesc(String id);
    List<PageInfo> findByProductUrlId(String id, Sort sort);


}
