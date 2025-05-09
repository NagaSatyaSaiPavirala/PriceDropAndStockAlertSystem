//package com.pricedrop.entities;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@NoArgsConstructor
//@Data
//@Entity
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int u_id;
//    @NotBlank(message = "Name field is required!!")
//    @Size(min = 2, max = 30, message = "Min 2 and Max 30 Character are allowed")
//    private String username;
//
//    @Column(unique = true)
//    // @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Invalid email")
//    @Pattern(regexp = "^[\\w.+\\-]+@gmail\\.com$", message = "Invalid email")
//    private String email;
//
//    @Size(min = 4, message = "Password must be at least 4 characters long")
//    private String password;
//    private String role;
//
//    @Pattern(regexp = "^\\d{8,12}$", message = "Invalid mobile number. Please enter a 10-digit number.")
//    private String mno;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
//    private List<Product> product = new ArrayList<>();
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "u_id=" + u_id +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", role='" + role + '\'' +
//                ", mno=" + mno +
//                '}';
//    }
//}




package com.pricedrop.entities;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table("user")  // Define the Cassandra table name
public class User {

    @PrimaryKey
    private int u_id;

    @NotBlank(message = "Name field is required!!")
    @Size(min = 2, max = 30, message = "Min 2 and Max 30 characters are allowed")
    private String username;

    @Pattern(regexp = "^[\\w.+\\-]+@gmail\\.com$", message = "Invalid email")
    private String email;

    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    private String role;

    @Pattern(regexp = "^\\d{8,12}$", message = "Invalid mobile number. Please enter a 10-digit number.")
    private String mno;

    // Removed @OneToMany relationship (Cassandra does not support joins)
}
