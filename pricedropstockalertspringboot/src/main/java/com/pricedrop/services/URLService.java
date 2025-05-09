//package com.pricedrop.services;
//
//import com.pricedrop.dao.PageRepository;
//import com.pricedrop.dao.URLRepository;
//import com.pricedrop.model.PageInfo;
//import com.pricedrop.model.URL;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.data.domain.Sort;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class URLService {
//    private static Logger LOG=LoggerFactory.getLogger(URLService.class);
//    @Value("#{${com.pricedrop.topics}}")
//    Map<String,String> kafkaTopics;
//    @Value("${com.pricedrop.service.cooldown}")
//    private Integer cooldown;
//
//    @Autowired
//    private KafkaService kafkaService;
//    @Autowired
//    private URLRepository urlRepository;
//
//    @Autowired
//    @Lazy
//    private CacheService cacheService;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private ProductScraperService productScraperService;
//
//    @Autowired
//    private PageRepository pageRepository;
//
//    public Optional<URL> getq(String id) {
//        return urlRepository.findById(id);
//    }
//
//    public Optional<PageInfo> getm(String id) {
////        PageInfo pageInfo = pageRepository.findByUrlId(id);
////        if (pageInfo == null)  {
////            return Optional.empty();
////        }
////        return Optional.of(pageInfo);
//        //return pageRepository.findFirstByProductUrlIdOrderByCreatedDateDesc(id);
//        List<PageInfo> results = pageRepository.findByProductUrlId(id, Sort.by(Sort.Direction.DESC, "createdTime"));
//
//        if (results.isEmpty()) {
//            return Optional.empty();
//        }
//        return Optional.of(results.get(0)); // Returns the latest entry
//
//    }
////    @Async("urlProcessor")
////    public void save(Set<URL> urls) {
////        for(URL url : urls) {
////            try {
////                LOG.info("--------- {}", Thread.currentThread().getName());
////                if (cacheService.get(url.getUrl()) != null) {
////                    return;
////                }
////                Optional<URL> existingURLOpt = urlRepository.findByUrl(url.getUrl());
////                Optional<String> optContentType = Optional.empty();
////                if (!existingURLOpt.isEmpty()) {
////                    URL existingURL = existingURLOpt.get();
////                    // we are going to allow processing if the URL has been processed more than 7 days ago
////                    if (existingURL.getLastProcessed().getTime() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis()) {
////                        LOG.info("URL {} already processed on {}", existingURL.getUrl(), existingURL.getLastProcessed());
////                        cacheService.set(existingURL);
////                        return;
////                    }
////                    url = existingURL;
////                    optContentType = Optional.of(existingURL.getContentType());
////                }
////                url.setLastProcessed(new Timestamp(System.currentTimeMillis()));
////                url.setTimesProcessed(url.getTimesProcessed() + 1);
////                if (optContentType.isEmpty()) {
////                    optContentType = getContentType(url.getUrl());
////                    LOG.info("URL {} content type is {}", url.getUrl(),optContentType);
////                }
////                if (optContentType.isEmpty()) {
////                    LOG.warn("Content type not found for URL: {}", url.getUrl());
////                    return;
////                }
////                Optional<String> optTopic = getTopicByContentType(optContentType.get());
////                if (optTopic.isEmpty()) {
////                    LOG.warn("Content type {} not mapped", optContentType.get());
////                    return;
////                }
////                String topic = optTopic.get();
////                if (url.getContentType() == null || url.getContentType().isEmpty()) {
////
////                    url.setContentType(optContentType.get());
////                }
////                LOG.info("URL: {}, sending to topic: {}", url.getUrl(), topic);
////              //kafkaService.send(topic, url.getUrl());
////              //  kafkaService.send(topic, url);
////
////                cacheService.set(url);
////                urlRepository.save(url);
////            } catch (IOException ex) {
////                LOG.error("Exception: ", ex);
////            }
////        }
////    }
//
////    @Async("urlProcessor")
////    public void save(Set<URL> urls) {
////        for (URL url : urls) {
////            try {
////                LOG.info("--------- {}", Thread.currentThread().getName());
////
////                Optional<URL> existingURLOpt = urlRepository.findByUrl(url.getUrl());
////                Optional<String> optContentType = Optional.empty();
////
////                if (existingURLOpt.isPresent()) {
////                    URL existingURL = existingURLOpt.get();
////
////                    // Allow re-processing only if last processed was more than 7 days ago
////                    if (existingURL.getLastProcessed() != null &&
////                            existingURL.getLastProcessed().getTime() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis()) {
////                        LOG.info("URL {} already processed on {}", existingURL.getUrl(), existingURL.getLastProcessed());
////                        cacheService.set(existingURL);
////                        return;
////                    }
////
////                    //  Update the existing entity directly (Do not reassign)
////                    existingURL.setLastProcessed(new Timestamp(System.currentTimeMillis()));
////                    existingURL.setTimesProcessed(existingURL.getTimesProcessed() == null ? 1 : existingURL.getTimesProcessed() + 1);
////                    url = existingURL;
////                    optContentType = Optional.ofNullable(existingURL.getContentType());
////                } else {
////                    // First-time processing
////                    url.setLastProcessed(new Timestamp(System.currentTimeMillis()));
////                    url.setTimesProcessed(1);
////                }
////
////                if (optContentType.isEmpty()) {
////                    optContentType = getContentType(url.getUrl());
////                    LOG.info("URL {} content type is {}", url.getUrl(), optContentType);
////                }
////
////                if (optContentType.isEmpty()) {
////                    LOG.warn("Content type not found for URL: {}", url.getUrl());
////                    return;
////                }
////
////                Optional<String> optTopic = getTopicByContentType(optContentType.get());
////                if (optTopic.isEmpty()) {
////                    LOG.warn("Content type {} not mapped", optContentType.get());
////                    return;
////                }
////
////                String topic = optTopic.get();
////                if (url.getContentType() == null || url.getContentType().isEmpty()) {
////                    url.setContentType(optContentType.get());
////                }
////
////                LOG.info("URL: {}, sending to topic: {}", url.getUrl(), topic);
////                cacheService.set(url);
////
////                //  Ensure the entity is explicitly saved
////
////
////               // urlRepository.save(url);
////                LOG.info("Saving URL: {}, Last Processed: {}, Times Processed: {}",
////                        url.getUrl(), url.getLastProcessed(), url.getTimesProcessed());
////                urlRepository.save(url);
////                LOG.info("Save executed for URL: {}", url.getUrl());
////
////            } catch (IOException ex) {
////                LOG.error("Exception: ", ex);
////            }
////        }
////    }
//
//
//    @Async("urlProcessor")
//    public void save(Set<URL> urls) {
//        for (URL url : urls) {
//            try {
//                LOG.info("Processing URL: {}", url.getUrl()); //  Start log
//
//                Optional<URL> existingURLOpt = urlRepository.findByUrl(url.getUrl());
//                Optional<String> optContentType = Optional.empty();
//
//                if (existingURLOpt.isPresent()) {
//                    URL existingURL = existingURLOpt.get();
//                    LOG.info("Found existing URL: {}, Last Processed: {}", existingURL.getUrl(), existingURL.getLastProcessed());
//
////                    // Check if it should be skipped due to cooldown
////                    if (existingURL.getLastProcessed().getTime() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis()) {
////                        LOG.warn("Skipping URL {} because it's still in cooldown.", existingURL.getUrl());
////                        cacheService.set(existingURL);
////                        continue; //  Exiting early
////                    }
//
//                    if (existingURL.getLastProcessed().getTime() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis()) {
//                        LOG.warn("Skipping URL {} because it's still in cooldown. But updating lastProcessed.");
//
//                        existingURL.setLastProcessed(new Timestamp(System.currentTimeMillis()));
//                        existingURL.setTimesProcessed(existingURL.getTimesProcessed() + 1);
//                        urlRepository.saveAndFlush(existingURL);
//
//                       // cacheService.set(existingURL);
//                        url = existingURL;
//                        optContentType = Optional.of(existingURL.getContentType());
//
//                        Optional<String> optTopic = getTopicByContentType(optContentType.get());
//                        String topic = optTopic.get();
//                        if (url.getContentType() == null || url.getContentType().isEmpty()) {
//                            url.setContentType(optContentType.get());
//                        }
//
//                        LOG.info("Sending URL {} to topic: {}", url.getUrl(), topic);
//
//
//                        String urlString = url.getUrl();
//                        String productName = productScraperService.scrapeProductTitle(urlString);
//                        String productImageUrl = productScraperService.scrapeProductImage(urlString);
//
//                        url.setProductName(productName);
//                        url.setProductImage(productImageUrl);
//                        kafkaService.send(topic, url);
//// Store all three details in Redis
//                        cacheService.cacheProductDetails(urlString, productName, productImageUrl);
//
//
//                        return;
//                    }
//
//                    url = existingURL;
//                    optContentType = Optional.of(existingURL.getContentType());
//                }
//
//                url.setLastProcessed(new Timestamp(System.currentTimeMillis()));
//                url.setTimesProcessed(url.getTimesProcessed() == null ? 1 : url.getTimesProcessed() + 1);
//
//                LOG.info("Updated URL: {}, Last Processed: {}, Times Processed: {}",
//                        url.getUrl(), url.getLastProcessed(), url.getTimesProcessed());
//
//                if (optContentType.isEmpty()) {
//                    optContentType = getContentType(url.getUrl());
//                    LOG.info("Determined Content Type: {}", optContentType.orElse("Unknown"));
//                }
//
//                if (optContentType.isEmpty()) {
//                    LOG.warn("Skipping URL {} because content type could not be determined.", url.getUrl());
//                    continue; // Exiting early
//                }
//
//                Optional<String> optTopic = getTopicByContentType(optContentType.get());
//                if (optTopic.isEmpty()) {
//                    LOG.warn("Skipping URL {} because its content type {} is not mapped.", url.getUrl(), optContentType.get());
//                    continue; //  Exiting early
//                }
//
//                String topic = optTopic.get();
//                if (url.getContentType() == null || url.getContentType().isEmpty()) {
//                    url.setContentType(optContentType.get());
//                }
//
//                LOG.info("Sending URL {} to topic: {}", url.getUrl(), topic);
//                // kafkaService.send(topic, url.getUrl());
////                kafkaService.send(topic, url);
////                cacheService.set(url);
////                cacheService.set(productScraperService.scrapeProductTitle(url.getUrl()));
////                cacheService.set(productScraperService.scrapeProductImage(url.getUrl()));
//
//                String urlString = url.getUrl();
//                String productName = productScraperService.scrapeProductTitle(urlString);
//                String productImageUrl = productScraperService.scrapeProductImage(urlString);
//                url.setProductName(productName);
//url.setProductImage(productImageUrl);
//                kafkaService.send(topic, url);
//// Store all three details in Redis
//                cacheService.cacheProductDetails(urlString, productName, productImageUrl);
//              //  cacheService.getProductDetails(urlString);
////url.setProductName(productName);
////url.setProductImage(productImageUrl);
//
//                LOG.info("Saving URL to database: {}", url.getUrl()); //  Add log before saving
//                urlRepository.saveAndFlush(url);
//                LOG.info("Successfully saved URL: {}", url.getUrl()); //  Log success
//
//            } catch (Exception ex) {
//                LOG.error("Exception processing URL {}: ", url.getUrl(), ex);
//            }
//        }
//    }
//
//
//    private Optional<String> getContentType(String path) throws IOException {
//        /*
//        java.net.URL url = new java.net.URL(path);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("HEAD");
//        connection.connect();
//        return Optional.of(connection.getContentType());
//
//         */
//        try {
////            java.net.URL url = new java.net.URL(path);
////            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////            connection.setRequestMethod("HEAD");
////            connection.connect();
//           // return Optional.of(connection.getContentType());
//            String pr = productService.getCurrentPrice(path);
//            if (pr.equals("Currently Unavailable"))
//                return Optional.of("stock");
//                        else
//            return Optional.of("price");
//        }
//        catch (InterruptedException e) {
//            Thread.currentThread().interrupt(); // Reset the interruption flag
//            LOG.error("The thread was interrupted: ", e);
//            return Optional.empty();
//        }
//        catch (IOException e) {
//            // Log the exception message
//            e.printStackTrace();
//            return Optional.empty();
//        }
//    }
//
//    private Optional<String> getTopicByContentType(String rawContentType) {
//        String contentType = rawContentType.split(";")[0];
//        LOG.info("Key: {}", contentType);
//        if (kafkaTopics.containsKey(contentType)) {
//            return Optional.of(kafkaTopics.get(contentType));
//        }
//        return Optional.empty();
//    }
//}




package com.pricedrop.services;

import com.pricedrop.dao.PageRepository;
import com.pricedrop.dao.URLRepository;
import com.pricedrop.model.PageInfo;
import com.pricedrop.model.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class URLService {
    private static Logger LOG=LoggerFactory.getLogger(URLService.class);
    @Value("#{${com.pricedrop.topics}}")
    Map<String,String> kafkaTopics;
    @Value("${com.pricedrop.service.cooldown}")
    private Integer cooldown;

    @Autowired
    private KafkaService kafkaService;
    @Autowired
    private URLRepository urlRepository;

    @Autowired
    @Lazy
    private CacheService cacheService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductScraperService productScraperService;

    @Autowired
    private PageRepository pageRepository;

    public Optional<URL> getq(String id) {
        return urlRepository.findById(id);
    }

    public Optional<PageInfo> getm(String id) {
//        PageInfo pageInfo = pageRepository.findByUrlId(id);
//        if (pageInfo == null)  {
//            return Optional.empty();
//        }
//        return Optional.of(pageInfo);
        //return pageRepository.findFirstByProductUrlIdOrderByCreatedDateDesc(id);
        List<PageInfo> results = pageRepository.findByProductUrlId(id, Sort.by(Sort.Direction.DESC, "createdTime"));

        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(results.get(0)); // Returns the latest entry

    }
//    @Async("urlProcessor")
//    public void save(Set<URL> urls) {
//        for(URL url : urls) {
//            try {
//                LOG.info("--------- {}", Thread.currentThread().getName());
//                if (cacheService.get(url.getUrl()) != null) {
//                    return;
//                }
//                Optional<URL> existingURLOpt = urlRepository.findByUrl(url.getUrl());
//                Optional<String> optContentType = Optional.empty();
//                if (!existingURLOpt.isEmpty()) {
//                    URL existingURL = existingURLOpt.get();
//                    // we are going to allow processing if the URL has been processed more than 7 days ago
//                    if (existingURL.getLastProcessed().getTime() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis()) {
//                        LOG.info("URL {} already processed on {}", existingURL.getUrl(), existingURL.getLastProcessed());
//                        cacheService.set(existingURL);
//                        return;
//                    }
//                    url = existingURL;
//                    optContentType = Optional.of(existingURL.getContentType());
//                }
//                url.setLastProcessed(new Timestamp(System.currentTimeMillis()));
//                url.setTimesProcessed(url.getTimesProcessed() + 1);
//                if (optContentType.isEmpty()) {
//                    optContentType = getContentType(url.getUrl());
//                    LOG.info("URL {} content type is {}", url.getUrl(),optContentType);
//                }
//                if (optContentType.isEmpty()) {
//                    LOG.warn("Content type not found for URL: {}", url.getUrl());
//                    return;
//                }
//                Optional<String> optTopic = getTopicByContentType(optContentType.get());
//                if (optTopic.isEmpty()) {
//                    LOG.warn("Content type {} not mapped", optContentType.get());
//                    return;
//                }
//                String topic = optTopic.get();
//                if (url.getContentType() == null || url.getContentType().isEmpty()) {
//
//                    url.setContentType(optContentType.get());
//                }
//                LOG.info("URL: {}, sending to topic: {}", url.getUrl(), topic);
//              //kafkaService.send(topic, url.getUrl());
//              //  kafkaService.send(topic, url);
//
//                cacheService.set(url);
//                urlRepository.save(url);
//            } catch (IOException ex) {
//                LOG.error("Exception: ", ex);
//            }
//        }
//    }

//    @Async("urlProcessor")
//    public void save(Set<URL> urls) {
//        for (URL url : urls) {
//            try {
//                LOG.info("--------- {}", Thread.currentThread().getName());
//
//                Optional<URL> existingURLOpt = urlRepository.findByUrl(url.getUrl());
//                Optional<String> optContentType = Optional.empty();
//
//                if (existingURLOpt.isPresent()) {
//                    URL existingURL = existingURLOpt.get();
//
//                    // Allow re-processing only if last processed was more than 7 days ago
//                    if (existingURL.getLastProcessed() != null &&
//                            existingURL.getLastProcessed().getTime() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis()) {
//                        LOG.info("URL {} already processed on {}", existingURL.getUrl(), existingURL.getLastProcessed());
//                        cacheService.set(existingURL);
//                        return;
//                    }
//
//                    //  Update the existing entity directly (Do not reassign)
//                    existingURL.setLastProcessed(new Timestamp(System.currentTimeMillis()));
//                    existingURL.setTimesProcessed(existingURL.getTimesProcessed() == null ? 1 : existingURL.getTimesProcessed() + 1);
//                    url = existingURL;
//                    optContentType = Optional.ofNullable(existingURL.getContentType());
//                } else {
//                    // First-time processing
//                    url.setLastProcessed(new Timestamp(System.currentTimeMillis()));
//                    url.setTimesProcessed(1);
//                }
//
//                if (optContentType.isEmpty()) {
//                    optContentType = getContentType(url.getUrl());
//                    LOG.info("URL {} content type is {}", url.getUrl(), optContentType);
//                }
//
//                if (optContentType.isEmpty()) {
//                    LOG.warn("Content type not found for URL: {}", url.getUrl());
//                    return;
//                }
//
//                Optional<String> optTopic = getTopicByContentType(optContentType.get());
//                if (optTopic.isEmpty()) {
//                    LOG.warn("Content type {} not mapped", optContentType.get());
//                    return;
//                }
//
//                String topic = optTopic.get();
//                if (url.getContentType() == null || url.getContentType().isEmpty()) {
//                    url.setContentType(optContentType.get());
//                }
//
//                LOG.info("URL: {}, sending to topic: {}", url.getUrl(), topic);
//                cacheService.set(url);
//
//                //  Ensure the entity is explicitly saved
//
//
//               // urlRepository.save(url);
//                LOG.info("Saving URL: {}, Last Processed: {}, Times Processed: {}",
//                        url.getUrl(), url.getLastProcessed(), url.getTimesProcessed());
//                urlRepository.save(url);
//                LOG.info("Save executed for URL: {}", url.getUrl());
//
//            } catch (IOException ex) {
//                LOG.error("Exception: ", ex);
//            }
//        }
//    }


   @Async("urlProcessor")
    public void save(Set<URL> urls) {
        for (URL url : urls) {
            try {
                LOG.info("Processing URL: {}", url.getUrl()); //  Start log

                Optional<URL> existingURLOpt = urlRepository.findByUrl(url.getUrl());
                Optional<String> optContentType = Optional.empty();

                if (existingURLOpt.isPresent()) {
                    URL existingURL = existingURLOpt.get();
                    LOG.info("Found existing URL: {}, Last Processed: {}", existingURL.getUrl(), existingURL.getLastProcessed());

//                    // Check if it should be skipped due to cooldown
//                    if (existingURL.getLastProcessed().getTime() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis()) {
//                        LOG.warn("Skipping URL {} because it's still in cooldown.", existingURL.getUrl());
//                        cacheService.set(existingURL);
//                        continue; //  Exiting early
//                    }

                   // if (existingURL.getLastProcessed().getTime() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis())
                    if (existingURL.getLastProcessed().toEpochMilli() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis())
                        {
                        LOG.warn("Skipping URL {} because it's still in cooldown. But updating lastProcessed.");

                        //existingURL.setLastProcessed(new Timestamp(System.currentTimeMillis()));
                      //  existingURL.setLastProcessed(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()); //  Correct
                            existingURL.setLastProcessed(Instant.now()); //  Correct

                        existingURL.setTimesProcessed(existingURL.getTimesProcessed() + 1);
                       // urlRepository.saveAndFlush(existingURL);
                        urlRepository.save(existingURL); //  Works with Cassandra


                        // cacheService.set(existingURL);
                        url = existingURL;
                        optContentType = Optional.of(existingURL.getContentType());

                        Optional<String> optTopic = getTopicByContentType(optContentType.get());
                        String topic = optTopic.get();
                        if (url.getContentType() == null || url.getContentType().isEmpty()) {
                            url.setContentType(optContentType.get());
                        }

                        LOG.info("Sending URL {} to topic: {}", url.getUrl(), topic);


                        String urlString = url.getUrl();
                        String productName = productScraperService.scrapeProductTitle(urlString);
                        String productImageUrl = productScraperService.scrapeProductImage(urlString);

                        url.setProductName(productName);
                        url.setProductImage(productImageUrl);
                        kafkaService.send(topic, url);
// Store all three details in Redis
                        cacheService.cacheProductDetails(urlString, productName, productImageUrl);


                        return;
                    }

                    url = existingURL;
                    optContentType = Optional.of(existingURL.getContentType());
                }

                //url.setLastProcessed(new Timestamp(System.currentTimeMillis()));
                //Instant.now()
                url.setLastProcessed(Instant.now()); //  Correct

                url.setTimesProcessed(url.getTimesProcessed() == null ? 1 : url.getTimesProcessed() + 1);

                LOG.info("Updated URL: {}, Last Processed: {}, Times Processed: {}",
                        url.getUrl(), url.getLastProcessed(), url.getTimesProcessed());

                if (optContentType.isEmpty()) {
                    optContentType = getContentType(url.getUrl());
                    LOG.info("Determined Content Type: {}", optContentType.orElse("Unknown"));
                }

                if (optContentType.isEmpty()) {
                    LOG.warn("Skipping URL {} because content type could not be determined.", url.getUrl());
                    continue; // Exiting early
                }

                Optional<String> optTopic = getTopicByContentType(optContentType.get());
                if (optTopic.isEmpty()) {
                    LOG.warn("Skipping URL {} because its content type {} is not mapped.", url.getUrl(), optContentType.get());
                    continue; //  Exiting early
                }

                String topic = optTopic.get();
                if (url.getContentType() == null || url.getContentType().isEmpty()) {
                    url.setContentType(optContentType.get());
                }

                LOG.info("Sending URL {} to topic: {}", url.getUrl(), topic);
                // kafkaService.send(topic, url.getUrl());
//                kafkaService.send(topic, url);
//                cacheService.set(url);
//                cacheService.set(productScraperService.scrapeProductTitle(url.getUrl()));
//                cacheService.set(productScraperService.scrapeProductImage(url.getUrl()));

                String urlString = url.getUrl();
                String productName = productScraperService.scrapeProductTitle(urlString);
                String productImageUrl = productScraperService.scrapeProductImage(urlString);
                url.setProductName(productName);
                url.setProductImage(productImageUrl);
                kafkaService.send(topic, url);
// Store all three details in Redis
                cacheService.cacheProductDetails(urlString, productName, productImageUrl);
                //  cacheService.getProductDetails(urlString);
//url.setProductName(productName);
//url.setProductImage(productImageUrl);

                LOG.info("Saving URL to database: {}", url.getUrl()); //  Add log before saving
               // urlRepository.saveAndFlush(url);
               // urlRepository.save(url); //  Works with Cassandra

                LOG.info("Successfully saved URL: {}", url.getUrl()); //  Log success

            } catch (Exception ex) {
                LOG.error("Exception processing URL {}: ", url.getUrl(), ex);
            }
        }
    }



//    @Async("urlProcessor")
//    public void save(Set<URL> urls) {
//        for (URL url : urls) {
//            try {
//                LOG.info("Processing URL: {}", url.getUrl());
//
//                Optional<URL> existingURLOpt = urlRepository.findByUrl(url.getUrl());
//                Optional<String> optContentType = Optional.empty();
//
//                if (existingURLOpt.isPresent()) {
//                    URL existingURL = existingURLOpt.get();
//                    LOG.info("Found existing URL: {}, Last Processed: {}", existingURL.getUrl(), existingURL.getLastProcessed());
//
//                    if (existingURL.getLastProcessed().toEpochMilli() + TimeUnit.DAYS.toMillis(cooldown) > System.currentTimeMillis()) {
//                        LOG.warn("Skipping URL {} because it's still in cooldown. Updating lastProcessed.", url.getUrl());
//
//                        existingURL.setLastProcessed(Instant.now());
//                        existingURL.setTimesProcessed(existingURL.getTimesProcessed() + 1);
//                        urlRepository.save(existingURL); // Save cooldown update
//
//                        url = existingURL;
//                        optContentType = Optional.ofNullable(existingURL.getContentType());
//
//                        kafkaService.send(optContentType,url);
//                        return;
//                    }
//
//                    url = existingURL;
//                    optContentType = Optional.ofNullable(existingURL.getContentType());
//                }
//
//                url.setLastProcessed(Instant.now());
//                url.setTimesProcessed(url.getTimesProcessed() == null ? 1 : url.getTimesProcessed() + 1);
//
//                LOG.info("Updated URL: {}, Last Processed: {}, Times Processed: {}",
//                        url.getUrl(), url.getLastProcessed(), url.getTimesProcessed());
//
//                if (optContentType.isEmpty()) {
//                    optContentType = getContentType(url.getUrl());
//                    LOG.info("Determined Content Type: {}", optContentType.orElse("Unknown"));
//                }
//
//                if (optContentType.isEmpty()) {
//                    LOG.warn("Skipping URL {} because content type could not be determined.", url.getUrl());
//                    continue;
//                }
//
//                kafkaService.send( optContentType,url);
//
//                LOG.info("Saving URL to database: {}", url.getUrl());
//                urlRepository.save(url);
//                LOG.info("Successfully saved URL: {}", url.getUrl());
//
//            } catch (Exception ex) {
//                LOG.error("Exception processing URL {}: ", url.getUrl(), ex);
//            }
//        }
//    }

    private Optional<String> getContentType(String path) throws IOException {
        /*
        java.net.URL url = new java.net.URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        connection.connect();
        return Optional.of(connection.getContentType());
         */
        try {
//            java.net.URL url = new java.net.URL(path);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("HEAD");
//            connection.connect();
            // return Optional.of(connection.getContentType());
            String pr = productService.getCurrentPrice(path);
            if (pr.equals("Currently Unavailable"))
                return Optional.of("stock");
            else
                return Optional.of("price");
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Reset the interruption flag
            LOG.error("The thread was interrupted: ", e);
            return Optional.empty();
        }
        catch (IOException e) {
            // Log the exception message
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<String> getTopicByContentType(String rawContentType) {
        String contentType = rawContentType.split(";")[0];
        LOG.info("Key: {}", contentType);
        if (kafkaTopics.containsKey(contentType)) {
            return Optional.of(kafkaTopics.get(contentType));
        }
        return Optional.empty();
    }
}
