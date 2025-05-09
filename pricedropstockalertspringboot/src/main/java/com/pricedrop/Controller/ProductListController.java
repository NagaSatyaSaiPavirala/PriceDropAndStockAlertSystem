//package com.pricedrop.Controller;
//
//import com.pricedrop.entities.Product;
//import com.pricedrop.entities.User;
//import com.pricedrop.dao.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//
//import java.security.Principal;
//import java.util.Collections;
//import java.util.List;
//
//@RestController // Use @RestController to directly return JSON
//@RequestMapping("/user/api1")
//public class ProductListController {
//    @Autowired
//    private UserRepository userRepository;
//
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @GetMapping("/products-list")
//    public ResponseEntity<List<Product>> getProductList(@AuthenticationPrincipal Principal principal) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//            List<Product> products = user.getProduct();
//
//            // Return the product list as JSON with HTTP 200 status
//            return ResponseEntity.ok(products);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Return an error message in case of exception
//            return ResponseEntity.status(500).body(Collections.emptyList());
//        }
//    }
//}
//





//package com.pricedrop.Controller;
//
//import com.pricedrop.entities.Product;
//import com.pricedrop.entities.User;
//import com.pricedrop.dao.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//
//import java.security.Principal;
//import java.util.Collections;
//import java.util.List;
//
//@RestController
//@RequestMapping("/user/api1")
//public class ProductListController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @GetMapping("/products-list")
//    public ResponseEntity<List<Product>> getProductList(@AuthenticationPrincipal Principal principal) {
//        try {
//            // Get the user's email from the authenticated principal
//            String email = principal.getName();
//            System.out.println("Fetching products for user: " + email);
//
//            // Fetch the user from the repository
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Check if user exists
//            if (user == null) {
//                System.out.println("User not found for email: " + email);
//                return ResponseEntity.status(404).body(Collections.emptyList()); // Return 404 if user is not found
//            }
//
//            // Get the product list associated with the user
//            List<Product> products = user.getProduct();
//
//            // Check if the product list is null, return an empty list instead of null
//            if (products == null || products.isEmpty()) {
//                System.out.println("No products found for user: " + email);
//                return ResponseEntity.ok(Collections.emptyList());
//            }
//
//            // Return the product list with HTTP 200 status
//            return ResponseEntity.ok(products);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Return an error message in case of exception
//            return ResponseEntity.status(500).body(Collections.emptyList());
//        }
//    }
//}





//package com.pricedrop.Controller;
//
//import com.pricedrop.entities.Product;
//import com.pricedrop.entities.User;
//import com.pricedrop.dao.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//
//import java.security.Principal;
//import java.util.Collections;
//import java.util.List;
//
//@RestController
//@RequestMapping("/user/api1")
//public class ProductListController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @GetMapping("/products-list")
//    public ResponseEntity<List<Product>> getProductList(@AuthenticationPrincipal Principal principal) {
//        try {
//            // Check if the principal is null (i.e., user is not authenticated)
//            if (principal == null) {
//                System.out.println("Principal is null, user not authenticated.");
//                return ResponseEntity.status(401).body(Collections.emptyList()); // Return 401 Unauthorized if principal is null
//            }
//
//            // Get the user's email from the authenticated principal
//            String email = principal.getName();
//            System.out.println("Fetching products for user: " + email);
//
//            // Fetch the user from the repository
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Check if user exists
//            if (user == null) {
//                System.out.println("User not found for email: " + email);
//                return ResponseEntity.status(404).body(Collections.emptyList()); // Return 404 if user is not found
//            }
//
//            // Get the product list associated with the user
//            List<Product> products = user.getProduct();
//
//            // Check if the product list is null, return an empty list instead of null
//            if (products == null || products.isEmpty()) {
//                System.out.println("No products found for user: " + email);
//                return ResponseEntity.ok(Collections.emptyList());
//            }
//
//            // Return the product list with HTTP 200 status
//            return ResponseEntity.ok(products);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Return an error message in case of exception
//            return ResponseEntity.status(500).body(Collections.emptyList());
//        }
//    }
//}





package com.pricedrop.Controller;

import com.pricedrop.entities.Product;
import com.pricedrop.entities.User;
import com.pricedrop.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import com.pricedrop.dao.ProductRepository;

@RestController
@RequestMapping("/user/api1")
public class ProductListController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/products-list")
    public ResponseEntity<List<Product>> getProductList(@AuthenticationPrincipal Principal principal) {
        try {
            // Check if the principal is null (i.e., user is not authenticated)
            if (principal == null) {
                System.out.println("Principal is null, user not authenticated.");
                return ResponseEntity.status(401).body(Collections.emptyList()); // Return 401 Unauthorized if principal is null
            }

            // Get the user's email from the authenticated principal
            String email = principal.getName();
            System.out.println("Fetching products for user: " + email);

            // Fetch the user from the repository
            User user = this.userRepository.getUserByUserName(email);

            // Check if user exists
            if (user == null) {
                System.out.println("User not found for email: " + email);
                return ResponseEntity.status(404).body(Collections.emptyList()); // Return 404 if user is not found
            }

            // Get the product list associated with the user
            //List<Product> products = user.getProduct();
            // Fetch products by user_id
            List<Product> products = productRepository.findByUserId(user.getU_id());

            // Check if the product list is null, return an empty list instead of null
            if (products == null || products.isEmpty()) {
                System.out.println("No products found for user: " + email);
                return ResponseEntity.ok(Collections.emptyList());
            }

            // Return the product list with HTTP 200 status
            return ResponseEntity.ok(products);

        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message in case of exception
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }
}