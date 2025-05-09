package com.pricedrop.listener;

import com.pricedrop.model.URL;
import com.pricedrop.services.URLProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import com.pricedrop.dao.URLRepository;



@Component
public class HTMLWorker {
    private static final Logger LOG=LoggerFactory.getLogger(HTMLWorker.class);
    @Autowired
    private URLProcessor urlProcessor;

    @Autowired
    private URLRepository urlRepository;
    /*
    @KafkaListener(topics="html_file", groupId = "group_id")
    public void consume(String message)
    {
        //System.out.println("Received message:"+message);
        LOG.info("Received message:{}",message);
        urlProcessor.process(message);

    }

     */
//    @KafkaListener(topics = "price_topic", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")    public void consume(@Payload URL message, @Headers MessageHeaders headers) {
//        LOG.info("Received message: {}, headers: {}", message, headers);
//        urlProcessor.process(message.getUrl(), message.getId());
//    }

//    @KafkaListener(topics = {"price_topic", "stock_topic"},
//            groupId = "group_id",
//            containerFactory = "kafkaListenerContainerFactory")
//    public void consume(@Payload URL message, @Headers MessageHeaders headers) {
//        LOG.info("Received message from topic: {}, message: {}, headers: {}",
//                headers.get("kafka_receivedTopic"), message, headers);
//        urlProcessor.process(message.getUrl(), message.getId());
//    }

    @KafkaListener(topics = {"price_topic", "stock_topic"},
            groupId = "group_id",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Payload URL message, @Headers MessageHeaders headers) {
        LOG.info("Received message from topic: {}, message: {}, headers: {}",
                headers.get("kafka_receivedTopic"), message, headers);
//        // Process URL and get required data from Kafka
//        String urlString = message.getUrl();
//        String productName = productScraperService.scrapeProductTitle(urlString);
//        String productImageUrl = productScraperService.scrapeProductImage(urlString);
//
//        // Update message object with additional details
//        message.setProductName(productName);
//        message.setProductImage(productImageUrl);
//        message.setLastProcessed(Instant.now());
//        message.setTimesProcessed(message.getTimesProcessed() == null ? 1 : message.getTimesProcessed() + 1);
        // Save to repository only when Kafka message is received
        LOG.info("Saving URL to database from Kafka: {}", message.getUrl());
        urlRepository.save(message);
        urlProcessor.process(message.getUrl(), message.getId());
        // Store in cache
        //cacheService.cacheProductDetails(urlString, productName, productImageUrl);
    }


}
