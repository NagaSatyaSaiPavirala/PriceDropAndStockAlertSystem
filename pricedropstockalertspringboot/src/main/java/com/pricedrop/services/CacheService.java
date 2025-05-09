//package com.pricedrop.services;
//
//import com.pricedrop.codec.URLSerializationCodec;
//import com.pricedrop.model.URL;
//import io.lettuce.core.RedisClient;
//import io.lettuce.core.api.StatefulRedisConnection;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class CacheService {
//    private static final Logger LOG= LoggerFactory.getLogger(CacheService.class);
//    @Value("${com.pricedrop.service.cache.ttl}")
//    private Integer ttl;
//    @Value("${com.pricedrop.cache.url}")
//    private String url;
//    private RedisClient redisClient=null;
//    private StatefulRedisConnection<String, URL> statefulRedisConnection=null;
//    public URL get(String key)
//    {
//        URL url=statefulRedisConnection.sync().get(key);
//        if(url!=null)
//        {
//            LOG.info("Serving from cache,for key:{}!",key);
//        }
//        else
//        {
//            LOG.info("Cache miss.for the key:{}!",key);
//        }
//        return url;
//    }
//    public void set(URL url)
//    {
//        //Long ttlSeconds=30L;
//        //Long ttlSeconds= TimeUnit.DAYS.toSeconds(this.ttl);//for sql
//        long ttlSeconds= TimeUnit.DAYS.toSeconds(this.ttl);//for cassandra
//        statefulRedisConnection.sync().setex(url.getUrl(),ttlSeconds,url);
//    }
//    public void set(String str)
//    {
//        //Long ttlSeconds=30L;
//        //Long ttlSeconds= TimeUnit.DAYS.toSeconds(this.ttl);//for sql
//        long ttlSeconds= TimeUnit.DAYS.toSeconds(this.ttl);//for cassandra
//        statefulRedisConnection.sync().setex(str,ttlSeconds,str);
//    }
////    @PostConstruct
////    private void init()
////    {
////        LOG.info("Post init called");
////        redisClient= RedisClient.create(url);
////        statefulRedisConnection=redisClient.connect(new URLSerializationCodec());
////
////    }
//@PostConstruct
//private void init() {
//    LOG.info("Post init called, attempting to connect to Redis at {}", url);
//    try {
//        redisClient = RedisClient.create(url);
//        statefulRedisConnection = redisClient.connect(new URLSerializationCodec());
//        LOG.info("Successfully connected to Redis!");
//    } catch (Exception e) {
//        LOG.error("Failed to connect to Redis at {}: {}", url, e.getMessage(), e);
//    }
//}
//
//    @PreDestroy
//    private void destroy()
//    {
//if(statefulRedisConnection!=null)
//{
//    statefulRedisConnection.close();
//}
//if(redisClient!=null)
//{
//    redisClient.shutdown();
//}
//    }
//}

package com.pricedrop.services;

import com.pricedrop.codec.URLSerializationCodec;
import com.pricedrop.model.URL;
import com.pricedrop.model.ProductDetails;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.Map;

@Service
public class CacheService {
    private static final Logger LOG = LoggerFactory.getLogger(CacheService.class);

    @Value("${com.pricedrop.service.cache.ttl}")
    private Integer ttl;

    @Value("${com.pricedrop.cache.url}")
    private String redisUrl;

    private RedisClient redisClient = null;
    private StatefulRedisConnection<String, String> statefulRedisConnection = null;

    @PostConstruct
    private void init() {
        LOG.info("Post init called, attempting to connect to Redis at {}", redisUrl);
        try {
            redisClient = RedisClient.create(redisUrl);
            statefulRedisConnection = redisClient.connect();
            LOG.info("Successfully connected to Redis!");
        } catch (Exception e) {
            LOG.error("Failed to connect to Redis at {}: {}", redisUrl, e.getMessage(), e);
        }
    }

    @PreDestroy
    private void destroy() {
        if (statefulRedisConnection != null) {
            statefulRedisConnection.close();
        }
        if (redisClient != null) {
            redisClient.shutdown();
        }
    }

    // Store product details using HSET
    public void cacheProductDetails(String url, String productName, String productImageUrl) {
        try {
            RedisCommands<String, String> commands = statefulRedisConnection.sync();
            long ttlSeconds = TimeUnit.DAYS.toSeconds(this.ttl); // TTL for expiration

            // Storing as a hash in Redis
            String cacheKey = "product:" + url;
            commands.hset(cacheKey, "name", productName);
            commands.hset(cacheKey, "image", productImageUrl);

            // Set expiration time
            commands.expire(cacheKey, ttlSeconds);

            LOG.info("Cached product details for URL: {}", url);
            printProductDetails(url);

        } catch (Exception e) {
            LOG.error("Error caching product details for URL {}: {}", url, e.getMessage(), e);
        }
    }

    // Retrieve product details
    public ProductDetails getProductDetails(String url) {
        try {
            RedisCommands<String, String> commands = statefulRedisConnection.sync();
            String cacheKey = "product:" + url;

            String productName = commands.hget(cacheKey, "name");
            String productImageUrl = commands.hget(cacheKey, "image");

            if (productName != null && productImageUrl != null) {
                LOG.info("Serving product details from cache for URL: {}", url);
                return new ProductDetails(productName, productImageUrl);
            } else {
                LOG.info("Cache miss for product details of URL: {}", url);
                return null;
            }
        } catch (Exception e) {
            LOG.error("Error retrieving product details for URL {}: {}", url, e.getMessage(), e);
            return null;
        }
    }
    public void printProductDetails(String url) {
        try {
            RedisCommands<String, String> commands = statefulRedisConnection.sync();
            String cacheKey = "product:" + url;

            // Retrieve cached product details
            Map<String, String> productData = commands.hgetall(cacheKey);

            if (productData.isEmpty()) {
                LOG.info("Cache miss for URL: {}", url);
                System.out.println("Product details not found in cache.");
            } else {
                String productName = productData.get("name");
                String productImageUrl = productData.get("image");

                LOG.info("Cached Product Details for URL {}: Name: {}, Image: {}", url, productName, productImageUrl);
                System.out.println("Cached Product Name: " + productName);
                System.out.println("Cached Product Image URL: " + productImageUrl);
            }
        } catch (Exception e) {
            LOG.error("Error retrieving cached product details for URL {}: {}", url, e.getMessage(), e);
        }
    }

}