//package com.pricedrop.model;
//
//import lombok.*;
////import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
////import org.springframework.data.cassandra.core.mapping.Column;
////import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
////import org.springframework.data.cassandra.core.mapping.Table;
//
//
//
//
////for sql below 5 lines
//import org.hibernate.annotations.DynamicUpdate;
//import org.springframework.data.annotation.CreatedDate;
//
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//
//
//@DynamicUpdate
//@Builder
//@Entity//for sql
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name="url")//for sql
////@Table("url")//for cassandra
//public class URL implements Serializable {
//    @Id//for sql
//    //@PrimaryKeyColumn(name="id",ordinal=0,type= PrimaryKeyType.PARTITIONED)
//    String id;
//    //@PrimaryKeyColumn(name="url",ordinal=1,type= PrimaryKeyType.PARTITIONED)
//    String url;
//    @Column(name="product_image")//for sql
//    String productImage;
//    @Column(name="product_name")//for sql
//    String productName;
//    @Column(name="times_processed")
//    //@Column("times_processed")//for cassandra
//    Integer timesProcessed;
//   @Column(name="content_type")//for sql
//   //@Column("content_type")//for cassandra
//    String contentType;
//
//    @Column(name="last_processed")
//   // @Column("last_processed")//for cassandra
//    Timestamp lastProcessed;
//   // @CreatedDate
//    @Column(name="created_date")
//   //@Column("created_date")//for cassandra
//    Timestamp createdDate;
//
//}



package com.pricedrop.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("url") // Cassandra table name
public class URL implements Serializable {

    @PrimaryKey // Primary key in Cassandra
    private String id;

    @Column("url")
    private String url;

    @Column("product_image")
    private String productImage;

    @Column("product_name")
    private String productName;

    @Column("times_processed")
    private Integer timesProcessed;

    @Column("content_type")
    private String contentType;

    @Column("last_processed")
    private Instant lastProcessed;

    @Column("created_date")
    private Instant createdDate;
}
