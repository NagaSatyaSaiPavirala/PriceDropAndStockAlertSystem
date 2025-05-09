///*package com.satya.app.urldataextractor.config;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CassandraConfig { public @Bean CqlSession session() { return CqlSession.builder().withKeyspace("urlfeeder").build(); } }
//
//
// */
//package com.pricedrop.config;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
//import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
//import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
//
//import java.net.InetSocketAddress;
//
//@Configuration
//
//
//public class CassandraConfig {
//
//    @Value("${spring.data.cassandra.contact-points}")
//    private String contactPoints;
//
//    @Value("${spring.data.cassandra.port}")
//    private int port;
//
//    @Value("${spring.data.cassandra.local-datacenter}")
//    private String localDatacenter;
//
//    @Value("${spring.data.cassandra.keyspace-name}")
//    private String keyspace;
//
//    @Bean
//    public CqlSession session() {
//        return CqlSession.builder()
//                .addContactPoint(new InetSocketAddress(contactPoints, port))
//                .withLocalDatacenter(localDatacenter)
//                .withKeyspace(keyspace)
//                .build();
//    }
//    @Bean
//    public CassandraMappingContext cassandraMapping() throws Exception {
//        return new BasicCassandraMappingContext();
//    }
//}




//package com.pricedrop.config;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
//import org.springframework.data.cassandra.core.CassandraTemplate;
//import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
//import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
//
//import java.net.InetSocketAddress;
//
//@Configuration
////@EnableCassandraRepositories(basePackages = "com.pricedrop.admin.dao")
//public class CassandraConfig extends AbstractCassandraConfiguration {
//
//    @Value("${spring.data.cassandra.contact-points}")
//    private String contactPoints;
//
//    @Value("${spring.data.cassandra.port}")
//    private int port;
//
//    @Value("${spring.data.cassandra.local-datacenter}")
//    private String localDatacenter;
//
//    @Value("${spring.data.cassandra.keyspace-name}")
//    private String keyspace;
//
//    @Override
//    protected String getKeyspaceName() {
//        return keyspace;
//    }
//
//    @Primary  //  Mark this as the primary session bean
//    @Bean
//    public CqlSession session() {
//        return CqlSession.builder()
//                .addContactPoint(new InetSocketAddress(contactPoints, port))
//                .withLocalDatacenter(localDatacenter)
//                .withKeyspace(keyspace)
//                .build();
//    }
//
//    @Bean
//    public CassandraTemplate cassandraTemplate(CqlSession session) {
//        return new CassandraTemplate(session);
//    }
//
//    @Bean
//    public CassandraMappingContext cassandraMapping() {
//        return new CassandraMappingContext();
//    }
//}





package com.pricedrop.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.net.InetSocketAddress;

@Configuration
//@EnableCassandraRepositories(basePackages = "com.pricedrop.admin.dao") // Enable Cassandra repositories
//extends AbstractCassandraConfiguration
public class CassandraConfig  {

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${spring.data.cassandra.local-datacenter}")
    private String localDatacenter;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    //@Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Primary
    @Bean
    public CqlSession session() {
        System.out.println("Attempting to connect to Cassandra at: "+contactPoints);
        System.out.println("Using local datacenter: " + localDatacenter);
        System.out.println("Using keyspace: " + keyspace);

        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoints, port))
               // .addContactPoint(new InetSocketAddress("172.17.0.4", 9042))
                .withLocalDatacenter(localDatacenter)
                .withKeyspace(keyspace)
                .build();
    }

//    @Primary
//    @Bean
//    public CqlSession session() {
//        return CqlSession.builder()
//                .addContactPoint(new InetSocketAddress("172.17.0.2", 9042)) // Explicitly set IP
////                .addContactPoint(new InetSocketAddress("172.17.0.8", 9042)) // Add dc2 contact point
////                .addContactPoint(new InetSocketAddress("172.17.0.7", 9042)) // Add another dc2 contact point
//                .withLocalDatacenter("dc1") // Set correct datacenter name
//                .withKeyspace("pricedrop")
//                .build();
//    }


    //  Fix: Define CassandraMappingContext properly
    @Bean
    public CassandraMappingContext cassandraMappingContext() {
        return new CassandraMappingContext();
    }

    //  Fix: Add CassandraConverter (Needed for CassandraTemplate)
    @Bean
    public CassandraConverter cassandraConverter(CassandraMappingContext cassandraMappingContext) {
        return new MappingCassandraConverter(cassandraMappingContext);
    }

    //  Fix: Use CassandraAdminTemplate instead of CassandraTemplate
    @Bean
    public CassandraAdminTemplate cassandraTemplate(CqlSession session, CassandraConverter converter) {
        return new CassandraAdminTemplate(session, converter);
    }
}




//package com.pricedrop.config;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
//import org.springframework.data.cassandra.core.CassandraAdminTemplate;
//import org.springframework.data.cassandra.core.convert.CassandraConverter;
//import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
//import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
//import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
//
//import java.net.InetSocketAddress;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Configuration
////@EnableCassandraRepositories(basePackages = "com.pricedrop.admin.dao") // Enable Cassandra repositories
//public class CassandraConfig extends AbstractCassandraConfiguration {
//
//    @Value("${spring.data.cassandra.contact-points}")
//    private String contactPoints;  // Multiple IPs from application.properties
//
//    @Value("${spring.data.cassandra.port}")
//    private int port;
//
//    @Value("${spring.data.cassandra.local-datacenter}")
//    private String localDatacenter;
//
//    @Value("${spring.data.cassandra.keyspace-name}")
//    private String keyspace;
//
//    @Override
//    protected String getKeyspaceName() {
//        return keyspace;
//    }
//
//    @Primary
//    @Bean
//    public CqlSession session() {
//        List<InetSocketAddress> contactPointList = Arrays.stream(contactPoints.split(","))
//                .map(String::trim)
//                .map(ip -> new InetSocketAddress(ip, port))
//                .collect(Collectors.toList());
//
//        return CqlSession.builder()
//                .addContactPoints(contactPointList)
//                .withLocalDatacenter(localDatacenter)
//                .withKeyspace(keyspace)
//                .build();
//    }
//
//    @Bean
//    public CassandraMappingContext cassandraMappingContext() {
//        return new CassandraMappingContext();
//    }
//
//    @Bean
//    public CassandraConverter cassandraConverter(CassandraMappingContext cassandraMappingContext) {
//        return new MappingCassandraConverter(cassandraMappingContext);
//    }
//
//    @Bean
//    public CassandraAdminTemplate cassandraTemplate(CqlSession session, CassandraConverter converter) {
//        return new CassandraAdminTemplate(session, converter);
//    }
//}
