package com.pricedrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.TimeZone;

@EnableAsync
//@SpringBootApplication
@EnableKafka
@EnableScheduling
@ComponentScan
//@EnableAutoConfiguration
@EnableCassandraRepositories(basePackages = "com.pricedrop") // Replace with your actual package name
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableMongoRepositories(basePackages="com.pricedrop.dao")
//@EnableEurekaClient
//automatically detects
//@EnableDiscoveryClient
//@SpringBootApplication(exclude = { CassandraAutoConfiguration.class })
@SpringBootApplication(exclude = { CassandraDataAutoConfiguration.class })
public class PriceDropAlertApplication {

	public static void main(String[] args) {
		//TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		SpringApplication.run(PriceDropAlertApplication.class, args);

	}

}
