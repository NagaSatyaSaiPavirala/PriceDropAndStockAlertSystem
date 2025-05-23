//package com.pricedrop.config;
//
//import com.pricedrop.model.URL;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
////import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
//import org.springframework.kafka.listener.DefaultErrorHandler;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConfig {
//
//    @Value("${spring.kafka.consumer.group-id:group_id}")
//    private String groupId;
//
//    //@Value("${spring.kafka.consumer.bootstrap-servers:172.17.0.7:9092}")
//    @Value("${spring.kafka.consumer.bootstrap-servers:127.0.0.1:9092}")
//    private String brokers;
//
//    @Value("${spring.kafka.consumer.auto-offset-reset:latest}")
//    private String autoOffsetResetConfig;
//
//    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
//    private String trustedPackage;
//
//    @Bean
//    public ConsumerFactory<String, URL> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);
//
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, URL.class);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//
//        JsonDeserializer<URL> jsonDeserializer = new JsonDeserializer<>(URL.class, false);
//        jsonDeserializer.addTrustedPackages(trustedPackage);
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
//    }
//
//    @Bean
//    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, URL>> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, URL> factory = new ConcurrentKafkaListenerContainerFactory<>();
//       // factory.setErrorHandler(new SeekToCurrentErrorHandler());
//        factory.setErrorHandler(new DefaultErrorHandler());
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//}




package com.pricedrop.config;

import com.pricedrop.model.URL;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.consumer.group-id:group_id_new}") // Change group ID
    private String groupId;

    //@Value("${spring.kafka.consumer.bootstrap-servers:127.0.0.1:9092}") // Ensure Kafka is running
    @Value("${spring.kafka.consumer.bootstrap-servers:172.17.0.6:9092}") // Ensure Kafka is running
    private String brokers;

    @Value("${spring.kafka.consumer.auto-offset-reset:earliest}") // Set to earliest to get old messages
    private String autoOffsetResetConfig;

    @Bean
    public ConsumerFactory<String, URL> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        JsonDeserializer<URL> jsonDeserializer = new JsonDeserializer<>(URL.class, false);
        jsonDeserializer.addTrustedPackages("*"); // Allow all packages
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, URL>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, URL> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        // Set error handler with retry (3 retries with 1-second delay)
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000L, 3)));

        return factory;
    }
}
