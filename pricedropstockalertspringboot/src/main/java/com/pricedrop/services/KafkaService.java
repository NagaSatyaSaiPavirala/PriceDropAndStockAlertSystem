package com.pricedrop.services;

import com.pricedrop.model.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, URL> kafkaTemplate;

    public void send(String topic, URL message) {
        kafkaTemplate.send(topic, message);
    }
}
