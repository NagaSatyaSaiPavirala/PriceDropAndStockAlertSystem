//package com.pricedrop.entities;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.DecimalMax;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.transaction.annotation.Transactional;
//
//@Data
//@NoArgsConstructor
//@Entity
//public class Product {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int p_id;
//    //@NotBlank(message = "Product name is required")
//    @Size(min = 2, max = 1000, message = "Product name must be between 2 and 100 characters")
//    private String p_name;
//
//    @NotBlank(message = "Product URL is required")
//    @Size(min = 10, max = 200, message = "Product URL must be between 10 and 200 characters")
//    private String p_url;
//
//    @DecimalMin(value = "100", message = "Price must be greater than 100")
//    @DecimalMax(value = "999999.99", message = "Total price must not exceed 999999.99")
//    private double t_price;
//
//    @ManyToOne
//    @JsonIgnore
//    private User user;
//
//    public User getUser(User user) {
//        return user;
//    }
//    public void setUser(User user) {
//        this.user = user;
//    }
//}

//int id
//package com.pricedrop.entities;
//
//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;
//import jakarta.validation.constraints.DecimalMax;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@Table("product")  // Cassandra table name
//public class Product {
//
//    @PrimaryKey
//    private int p_id;
//
////    @PrimaryKey
////    private ProductKey key;
////
////    private int p_id;
//
//    @Size(min = 2, max = 1000, message = "Product name must be between 2 and 100 characters")
//    private String p_name;
//
//    @NotBlank(message = "Product URL is required")
//    @Size(min = 10, max = 200, message = "Product URL must be between 10 and 200 characters")
//    private String p_url;
//
//    @DecimalMin(value = "100", message = "Price must be greater than 100")
//    @DecimalMax(value = "999999.99", message = "Total price must not exceed 999999.99")
//    private double t_price;
//
//    private int user_id;  // Store user_id directly instead of a User object
//    // Getters and Setters
//    public int getUserId() {
//        return user_id;
//    }
//    public void setUserId(int user_id) {
//        this.user_id = user_id;
//    }
//
//}



package com.pricedrop.entities;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Table("product")  // Cassandra table name
public class Product {

    @PrimaryKeyColumn(name="p_id",ordinal=0,type= PrimaryKeyType.PARTITIONED)
    private int p_id;



    @Size(min = 2, max = 1000, message = "Product name must be between 2 and 100 characters")
    private String p_name;

    @NotBlank(message = "Product URL is required")
    @Size(min = 10, max = 200, message = "Product URL must be between 10 and 200 characters")
    private String p_url;

    @PrimaryKeyColumn(name = "t_price", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @DecimalMin(value = "100", message = "Price must be greater than 100")
    @DecimalMax(value = "999999.99", message = "Total price must not exceed 999999.99")
    private BigDecimal t_price;
   // private double t_price;

    // @PrimaryKeyColumn(name="t_price",ordinal=1,type= PrimaryKeyType.PARTITIONED)
    @Indexed
    private int user_id;  // Store user_id directly instead of a User object
    // Getters and Setters
    public int getUserId() {
        return user_id;
    }
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
    public Product(int p_id, String p_name, BigDecimal t_price, String p_url, int user_id) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.t_price = t_price;
        this.p_url = p_url;
        this.user_id = user_id;
    }

}



//package com.pricedrop.entities;
//import java.util.UUID;
//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;
//import jakarta.validation.constraints.DecimalMax;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@Table("product")  // Cassandra table name
//public class Product {
//
//    @PrimaryKey
//    private UUID p_id;  // Change from int to UUID
//
//    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
//    private String p_name;
//
//    @NotBlank(message = "Product URL is required")
//    @Size(min = 10, max = 200, message = "Product URL must be between 10 and 200 characters")
//    private String p_url;
//
//    @DecimalMin(value = "100", message = "Price must be greater than 100")
//    @DecimalMax(value = "999999.99", message = "Total price must not exceed 999999.99")
//    private double t_price;
//
//    private UUID user_id;  // Change user_id to UUID
//
//    // Constructor to generate UUID automatically
//    public Product(String p_name, String p_url, double t_price, UUID user_id) {
//        this.p_id = UUID.randomUUID(); // Generates a new UUID
//        this.p_name = p_name;
//        this.p_url = p_url;
//        this.t_price = t_price;
//        this.user_id = user_id;
//    }
//}
