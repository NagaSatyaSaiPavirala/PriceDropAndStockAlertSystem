//package com.pricedrop.admin.entities;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Setter
//@Getter
//public class Productapi {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int product_id;
//    private String product_url;
//    private String product_name;
//    private String product_image;
//    private String product_price;
//   // private String last_processed;
//}




package com.pricedrop.admin.entities;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@Table("productapi")  // Cassandra Table Name
public class Productapi {

    @PrimaryKey
    private int product_id;  // UUID is recommended for unique IDs

    private String product_url;
    @Column("product_name")
    private String productName;
    private String product_image;
    private String product_price;
}
