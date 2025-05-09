////for sql
//
//package com.pricedrop.Controller;
//
//import com.pricedrop.admin.dao.ApiRepository;
//import com.pricedrop.admin.entities.Productapi;
//import com.pricedrop.dao.ProductRepository;
//import com.pricedrop.dao.UserRepository;
//import com.pricedrop.entities.Product;
//import com.pricedrop.entities.User;
//import com.pricedrop.helper.Message;
//import com.pricedrop.services.ProductScraperService;
//import com.pricedrop.services.ProductService;
//import com.pricedrop.services.UrlCoding;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.naming.Binding;
//import javax.validation.Valid;
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//
//
//
//
//import com.pricedrop.model.URL;
//import com.pricedrop.common.Constants;
//import com.pricedrop.services.URLService;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;
//
//import org.slf4j.Logger;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//import java.util.UUID;
//import java.sql.Timestamp;
//
//
//
//
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//
//
//
//@Controller
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ApiRepository apiRepository;
//
//    @Autowired
//    private ProductService productService;
//
//
//    @Autowired
//    private ProductScraperService productScraperService;
//
//    @Autowired
//    private URLService urlService;
//
//    private static Logger LOG=LoggerFactory.getLogger(URLService.class);
//
//    @GetMapping("/api/user") // New endpoint for fetching user data in JSON format
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<User> getUser(Principal principal) {
//        User user = this.userRepository.getUserByUserName(principal.getName());
//        return ResponseEntity.ok(user);
//    }
//
//    @RequestMapping("/dashboard")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String Dashboard(Principal principal,Model model){
//        User user = this.userRepository.getUserByUserName(principal.getName());
//        model.addAttribute("user",user);
//
//        return "dashboard1";
//    }
//    @RequestMapping("/add-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String product(Principal principal,Model model){
//        User user = this.userRepository.getUserByUserName(principal.getName());
//        model.addAttribute("user",user);
//        model.addAttribute("product",new Product());
//        return "add_product1";
//    }
//
//
//    @RequestMapping("/added-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String addedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Log the incoming product details
//           // System.out.println("Received Product URL: " + product.getP_url());
//         //   System.out.println("Received Threshold Price: " + product.getT_price());
//
//            // Check if the product exists in the ProductAPI repository
//            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
//
//            // Scrape product details from the Amazon URL
//            String productUrl = product.getP_url();
//
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            // Check for scraped title and price
//            //if (productTitle == null || currentPrice == null)
//            if (productTitle == null)
//            {
//             //   System.out.println("Scraped Product Title: " + productTitle);
//             //   System.out.println("Scraped Product Price: " + currentPrice);
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            // Set the product name from scraped title
//            product.setP_name(productTitle);
//         //   System.out.println("Scraped Product Title: " + productTitle);
//          //  System.out.println("Scraped Product Price: " + currentPrice);
//
//            // Set the current price in the product object
//            product.setT_price(product.getT_price()); // Assuming product_price is a String
//
//          //  System.out.println("Current Price: " + currentPrice);
//           // System.out.println("Threshold Price: " + product.getT_price());
//
//            // Check threshold price logic
//            if (currentPrice!=null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            String productImageUrl;
//            if (existingProductApi != null) {
//                // Product exists in productapi table, use its image URL
//                productImageUrl = existingProductApi.getProduct_image();
//                System.out.println("Product already exists in ProductAPI. Using existing image URL: " + productImageUrl);
//                // Update the product price in productapi if currentPrice is available
//                if (currentPrice != null) {
//                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
//                    apiRepository.save(existingProductApi);
//                    System.out.println("Product price updated in ProductAPI with currentPrice: " + currentPrice);
//                }
//            } else {
//                // Product does not exist in productapi, scrape the image URL
//                productImageUrl = productScraperService.scrapeProductImage(productUrl);
//             //   System.out.println("Product not found in ProductAPI. Scraped image URL: " + productImageUrl);
//
//                if (productImageUrl != null) {
//                    // Save the scraped image URL to productapi for future use
//                    Productapi newProductApi = new Productapi();
//                    newProductApi.setProduct_name(product.getP_name());
//                   // newProductApi.setProduct_url(product.getP_url());
//                    newProductApi.setProduct_url(UrlCoding.extractProductName(product.getP_url()));
//                    newProductApi.setProduct_image(productImageUrl);
//                    //  newProductApi.setProduct_price(String.valueOf(currentPrice));
//                    if(currentPrice!=null)
//                    newProductApi.setProduct_price(String.valueOf(currentPrice));
//                    else
//                        newProductApi.setProduct_price("Currently Unavailable");
//
//
//                    apiRepository.save(newProductApi);
//                    System.out.println("Scraped product saved to ProductAPI table.");
//                } else {
//                    session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                    return "redirect:/user/add-product";
//                }
//            }
//
//            // Set the product image URL in the session
//            session.setAttribute("productImage", productImageUrl);
//
//            // Check for validation errors after scraping and setting the product fields
//            if (result.hasErrors()) {
//                result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
//                model.addAttribute("product", product);
//                model.addAttribute("user", user);
//                return "add_product1";
//            }
//
//            // Save the product entity
//            product.setUser(user); // Associate product with the user
//            productRepository.save(product); // Save the product to the repository
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception for debugging purposes
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//        }
//        return "redirect:/user/add-product";
//    }
//
//
//
//
//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                               BindingResult result,
//                                               Principal principal,
//                                               HttpSession session,Model model) {
//
//
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Log the incoming product details
//            // System.out.println("Received Product URL: " + product.getP_url());
//            // System.out.println("Received Threshold Price: " + product.getT_price());
//
//            // Check if the product exists in the ProductAPI repository
//            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
//
//            // Scrape product details from the Amazon URL
//            String productUrl = product.getP_url();
//
//
//            URL url = new URL();
//
//            long startTime=System.currentTimeMillis();
//            url.setId(Constants.URL_UUID_PREFIX +UUID.randomUUID().toString());
//            url.setUrl(productUrl);
//            url.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//            url.setTimesProcessed(0);
//            // urlService.save(url);
//            LOG.info("URL received:{}",productUrl);
//            //System.out.println(url);
//            urlService.save(new HashSet<>(){{
//                add(url);
//            }});
//            LOG.info("Request processed in {} mills",(System.currentTimeMillis()-startTime));
//            // return ResponseEntity.ok().build(); //public ResponseEntity<Void> submitURL(@RequestBody URL url) {
//           // return ResponseEntity.ok(url); //public ResponseEntity<URL> submitURL(@RequestBody URL url) {
//
//
//
//
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            // Check for scraped title
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            // Set the product name from scraped title
//            product.setP_name(productTitle);
//
//            // Set the current price in the product object
//            product.setT_price(product.getT_price()); // Assuming product_price is a String
//
//            // Check threshold price logic
//            if (currentPrice != null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!!");
//            }
//
//            String productImageUrl;
//            if (existingProductApi != null) {
//                // Product exists in productapi table, use its image URL
//                productImageUrl = existingProductApi.getProduct_image();
//                System.out.println("Product already exists in ProductAPI. Using existing image URL: " + productImageUrl);
//
//                // Update the product price in productapi if currentPrice is available
//                if (currentPrice != null) {
//                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
//                    apiRepository.save(existingProductApi);
//                    System.out.println("Product price updated in ProductAPI with currentPrice: " + currentPrice);
//                }
//            } else {
//                // Product does not exist in productapi, scrape the image URL
//                productImageUrl = productScraperService.scrapeProductImage(productUrl);
//
//                if (productImageUrl != null) {
//                    // Save the scraped image URL to productapi for future use
//                    Productapi newProductApi = new Productapi();
//                    newProductApi.setProduct_name(product.getP_name());
//                    newProductApi.setProduct_url(UrlCoding.extractProductName(product.getP_url()));
//                    newProductApi.setProduct_image(productImageUrl);
//                    if (currentPrice != null)
//                        newProductApi.setProduct_price(String.valueOf(currentPrice));
//                    else
//                        newProductApi.setProduct_price("Currently Unavailable");
//
//                    apiRepository.save(newProductApi);
//                    System.out.println("Scraped product saved to ProductAPI table.");
//                } else {
//                    session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//                }
//            }
//
//            // Set the product image URL in the session
//            session.setAttribute("productImage", productImageUrl);
//
//            // Check for validation errors after scraping and setting the product fields
//            if (result.hasErrors()) {
//                result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
//                model.addAttribute("product", product);
//                model.addAttribute("user", user);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors occurred. Please check the provided information.");
//            }
//
//            // Save the product entity
//            product.setUser(user); // Associate product with the user
//            productRepository.save(product); // Save the product to the repository
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception for debugging purposes
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//
//
//
//
//
//
//
//    }
//
//
//
//
//
//    private boolean isProductExistsForUser(Product product, User user) {
//        List<Product> userProducts = user.getProduct();
//        for (Product userProduct : userProducts) {
//            if (userProduct.getP_name().equals(product.getP_name()) && userProduct.getP_url().equals(product.getP_url())) {
//                return true; // Product already exists for the user
//            }
//        }
//        return false; // Product does not exist for the user
//    }
//
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @RequestMapping("/product-list")
//    public String List(Model model, Principal principal){
//        try{
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//            List<Product> products = user.getProduct();
//            model.addAttribute("user",user);
//            model.addAttribute("list",products);
//            model.addAttribute("productService", new ProductService());
//            model.addAttribute("UrlCoding",new UrlCoding());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "product_list1";
//    }
//
//
//
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @GetMapping("/products-list")
//    public ResponseEntity<List<Product>> listProducts(Principal principal) {
//        List<Product> products = null;
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//            products = user.getProduct();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 in case of error
//        }
//        return ResponseEntity.ok(products); // Return the list of products in the response body
//    }
//
//
//    @RequestMapping("/deleteproduct/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String DeleteProduct(@PathVariable("productID") int productID,Principal principal,HttpSession session){
//        try {
//            Optional<Product> product = this.productRepository.findById(productID);
//            if(product.get().getUser() == this.userRepository.getUserByUserName(principal.getName())){
//                Product pro = product.get();
//                User user = pro.getUser();
//                user.getProduct().remove(pro);
//                this.userRepository.save(user); // Update the user entity
//                this.productRepository.deleteById(productID); // Delete the product
//            }else {
//                session.setAttribute("message", new Message("You cannot access other's product!", "alert-danger"));
//                System.out.println("you are accessing other contact");
//                return "redirect:/user/dashboard";
//            }
//
//
//        } catch (Exception e) {
//            // Log the exception
//            e.printStackTrace();
//        }
//
//        return "redirect:/user/product-list";
//    }
//
//
//
//    @RequestMapping("/deleteproducts/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<List<Product>> DeleteProducts(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        List<Product> updatedProductList = new ArrayList<>(); // Initialize with an empty list
//
//        try {
//            // Find the product by ID
//            Optional<Product> product = this.productRepository.findById(productID);
//
//            if (product.isPresent()) {
//                Product pro = product.get();
//                User currentUser = this.userRepository.getUserByUserName(principal.getName());
//
//                // Check if the product belongs to the logged-in user
//                if (pro.getUser().equals(currentUser)) {
//                    // Remove the product from the user's product list
//                    currentUser.getProduct().remove(pro);
//
//                    // Save the updated user entity
//                    this.userRepository.save(currentUser);
//
//                    // Delete the product from the repository
//                    this.productRepository.deleteById(productID);
//
//                    // Fetch the updated product list for the user
//                    updatedProductList = currentUser.getProduct();
//                    return ResponseEntity.ok(updatedProductList); // Return 200 OK with the updated list
//                } else {
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(updatedProductList); // Return 403 Forbidden
//                }
//            } else {
//                // If product is not found
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedProductList); // Return 404 Not Found
//            }
//
//        } catch (Exception e) {
//            // Log the exception and return 500 Internal Server Error
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedProductList); // Return 500 with an empty list
//        }
//    }
//
//    @RequestMapping("/editproduct/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String EditProduct(@PathVariable("productID") int productID,Model model,Principal principal,HttpSession session){
//        try{
//            Optional<Product> product = this.productRepository.findById(productID);
//            if(product.get().getUser() == this.userRepository.getUserByUserName(principal.getName())){
//                //username
//                String email = principal.getName();
//                User user = this.userRepository.getUserByUserName(email);
//                model.addAttribute("user",user);
//
//                //edit
//
//                Product pro = product.get();
////            System.out.println(pro.getP_name()+" "+pro.getP_url()+" "+pro.getT_price());
//                model.addAttribute("product",pro);
//                return "edit";
//            }else {
//                session.setAttribute("message", new Message("You are the owner of that product!", "alert-danger"));
//                System.out.println("you are accessing other contact");
//                return "redirect:/user/dashboard";
//            }
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "edit";
//    }
//
//    @RequestMapping("/edit-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String Edit_Product(@ModelAttribute Product product,Principal principal,HttpSession session){
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//            if(product.getUser(user) == this.userRepository.getUserByUserName(principal.getName())){
//                product.setUser(user);
//                this.productRepository.save(product);
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//            }else {
//                session.setAttribute("message", new Message("You are the owner of that product!", "alert-danger"));
//                System.out.println("user"+user);
//                System.out.println("product.getuser"+product.getUser() );
//                System.out.println("product.getuserbyname"+this.userRepository.getUserByUserName(principal.getName()));
//                System.out.println("you are accessing other contact");
//                return "redirect:/user/dashboard";
//            }
//
//
//        }catch (Exception e){
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//
//        }
//        return "redirect:/user/product-list";
//    }
//
//
//@RequestMapping(value = "/edit-products/{id}", method = RequestMethod.GET)
//@PreAuthorize("hasAuthority('ROLE_USER')")
//public ResponseEntity<Product> getProductForEdit(@PathVariable Integer id, Principal principal) {
//    // Fetch the product using the provided ID
//    Product product = this.productRepository.findById(id).orElse(null);
//    if (product == null) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if product not found
//    }
//    // Return the product data
//    return ResponseEntity.ok(product);
//}
//
//
//
//
//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    //public ResponseEntity<String> Edit_Products(@ModelAttribute Product product, Principal principal, HttpSession session) {
//    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//        User user=null;
//        try {
//            String email = principal.getName();
//            user = this.userRepository.getUserByUserName(email);
//
//            // Check if the product belongs to the user
//            //if (product.getUser(user).equals(user)) { // Assuming getUser() returns User object
//            if (product.getUser(user)==this.userRepository.getUserByUserName(principal.getName())) { // Assuming getUser() returns User object
//                product.setUser(user);
//                this.productRepository.save(product);
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                return ResponseEntity.ok("Product updated successfully.");
//            } else {
//                session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//            }
//
//        } catch (Exception e) {
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            System.out.println("user: " + user);
//            System.out.println("product.getUser(): " + product.getUser(user));
//            System.out.println("product.getUserByName(): " + this.userRepository.getUserByUserName(principal.getName()));
//            e.printStackTrace();  // Log the exception details
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }
//
//
//
//
//
//
//
//
//
//}







package com.pricedrop.Controller;

import com.pricedrop.admin.dao.ApiRepository;
import com.pricedrop.admin.entities.Productapi;
import com.pricedrop.common.Constants;
import com.pricedrop.dao.ProductRepository;
import com.pricedrop.dao.UserRepository;
import com.pricedrop.entities.Product;
import com.pricedrop.entities.User;
import com.pricedrop.helper.Message;
import com.pricedrop.model.ProductDetails;
import com.pricedrop.model.URL;
import com.pricedrop.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


import org.slf4j.LoggerFactory;

import org.slf4j.Logger;


import com.pricedrop.dao.SequenceGeneratorRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private ProductService productService;


    @Autowired
    private ProductScraperService productScraperService;

    @Autowired
    private URLService urlService;

    @Autowired
    private SequenceGeneratorRepository sequenceGeneratorRepository;

    @Autowired
    @Lazy
    private CacheService cacheService;

    private static Logger LOG = LoggerFactory.getLogger(URLService.class);

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    //int newPrdId=sequenceGeneratorRepository.getNextProductId();
//    private int newPrdId; // Declare the variable without initialization
//
//    @PostConstruct // This method runs after Spring initializes the bean
//    public void init() {
//        this.newPrdId = sequenceGeneratorRepository.getNextProductId();
//    }


    @GetMapping("/api/user") // New endpoint for fetching user data in JSON format
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<User> getUser(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/dashboard")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String Dashboard(Principal principal, Model model) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        model.addAttribute("user", user);

        return "dashboard1";
    }

    @RequestMapping("/add-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String product(Principal principal, Model model) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("product", new Product());
        return "add_product1";
    }


//    @RequestMapping("/added-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String addedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Log the incoming product details
//            // System.out.println("Received Product URL: " + product.getP_url());
//            //   System.out.println("Received Threshold Price: " + product.getT_price());
//
//            // Check if the product exists in the ProductAPI repository
//            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
//
//            // Scrape product details from the Amazon URL
//            String productUrl = product.getP_url();
//
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            // Check for scraped title and price
//            //if (productTitle == null || currentPrice == null)
//            if (productTitle == null) {
//                //   System.out.println("Scraped Product Title: " + productTitle);
//                //   System.out.println("Scraped Product Price: " + currentPrice);
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            // Set the product name from scraped title
//            product.setP_name(productTitle);
//            //   System.out.println("Scraped Product Title: " + productTitle);
//            //  System.out.println("Scraped Product Price: " + currentPrice);
//
//            // Set the current price in the product object
//            product.setT_price(product.getT_price()); // Assuming product_price is a String
//
//            //  System.out.println("Current Price: " + currentPrice);
//            // System.out.println("Threshold Price: " + product.getT_price());
//
//            // Check threshold price logic
//            if (currentPrice != null && currentPrice <= product.getT_price().doubleValue()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            String productImageUrl;
//            if (existingProductApi != null) {
//                // Product exists in productapi table, use its image URL
//                productImageUrl = existingProductApi.getProduct_image();
//                System.out.println("Product already exists in ProductAPI. Using existing image URL: " + productImageUrl);
//                // Update the product price in productapi if currentPrice is available
//                if (currentPrice != null) {
//                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
//                    apiRepository.save(existingProductApi);
//                    System.out.println("Product price updated in ProductAPI with currentPrice: " + currentPrice);
//                }
//            } else {
//                // Product does not exist in productapi, scrape the image URL
//                productImageUrl = productScraperService.scrapeProductImage(productUrl);
//                //   System.out.println("Product not found in ProductAPI. Scraped image URL: " + productImageUrl);
//
//                if (productImageUrl != null) {
//                    // Save the scraped image URL to productapi for future use
//                    Productapi newProductApi = new Productapi();
//                    newProductApi.setProductName(product.getP_name());
//                    // newProductApi.setProduct_url(product.getP_url());
//                    newProductApi.setProduct_url(UrlCoding.extractProductName(product.getP_url()));
//                    newProductApi.setProduct_image(productImageUrl);
//                    //  newProductApi.setProduct_price(String.valueOf(currentPrice));
//                    if (currentPrice != null)
//                        newProductApi.setProduct_price(String.valueOf(currentPrice));
//                    else
//                        newProductApi.setProduct_price("Currently Unavailable");
//
//
//                    apiRepository.save(newProductApi);
//                    System.out.println("Scraped product saved to ProductAPI table.");
//                } else {
//                    session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                    return "redirect:/user/add-product";
//                }
//            }
//
//            // Set the product image URL in the session
//            session.setAttribute("productImage", productImageUrl);
//
//            // Check for validation errors after scraping and setting the product fields
//            if (result.hasErrors()) {
//                result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
//                model.addAttribute("product", product);
//                model.addAttribute("user", user);
//                return "add_product1";
//            }
//
//            // Save the product entity
//            // product.setUser(user); // Associate product with the user
//            product.setUser_id(user.getU_id()); //  Store only the user's ID
//
//            productRepository.save(product); // Save the product to the repository
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception for debugging purposes
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//        }
//        return "redirect:/user/add-product";
//    }


    //for thymeleaf


//    @RequestMapping("/added-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String addedProduct(
//            @Valid @ModelAttribute Product product,
//            BindingResult result,
//            Principal principal,
//            HttpSession session,
//            Model model) {
//
//        try {
//            // Get the authenticated user
//            String email = principal.getName();
//            User user = userRepository.getUserByUserName(email);
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            // Validate scraped product title
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price().doubleValue()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            // Check if product already exists in productapi table
//            Productapi existingProductApi = apiRepository.findByProductUrl(productUrl);
//
//            int productId;
//            if (existingProductApi != null) {
//                // Use existing product_id
//                productId = existingProductApi.getProduct_id();
//
//                // Update product price if available
//                if (currentPrice != null) {
//                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
//                    apiRepository.save(existingProductApi);
//                }
//            } else {
//                // Generate new unique product_id
//                productId = sequenceGeneratorRepository.getNextProductId();
//
//                // Insert new product in productapi
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // Insert product into Cassandra
//            product.setUserId(user.getU_id());
//            product.setP_id(productId);  // Set unique product ID
//
//            productRepository.save(product);
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return "redirect:/user/add-product";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return "redirect:/user/add-product";
//        }
//    }





    //spring boot
//    @RequestMapping("/added-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String addedProduct(
//            @Valid @ModelAttribute Product product,
//            BindingResult result,
//            Principal principal,
//            HttpSession session,
//            Model model) {
//
//        try {
//            // Get the authenticated user
//            String email = principal.getName();
//            User user = userRepository.getUserByUserName(email);
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price().doubleValue()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            // Save URL entity
//            URL url = new URL();
//            url.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
//            url.setUrl(productUrl);
//            url.setCreatedDate(Instant.now());
//            url.setTimesProcessed(0);
//            urlService.save(new HashSet<>() {{
//                add(url);
//            }});
//            LOG.info("URL received: {}", productUrl);
//
//            // Check if product already exists in productapi table
//            Productapi existingProductApi = apiRepository.findByProductUrl(productUrl);
//
//            int productId;
//            if (existingProductApi != null) {
//                // Use existing product_id
//                productId = existingProductApi.getProduct_id();
//
//                // Update product price if available
//                if (currentPrice != null) {
//                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
//                    apiRepository.save(existingProductApi);
//                }
//            } else {
//                // Generate new unique product_id
//                productId = sequenceGeneratorRepository.getNextProductId();
//
//                // Insert new product in productapi
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // Insert product into Cassandra
//            product.setUserId(user.getU_id());
//            product.setP_id(productId);  // Set unique product ID
//
//            productRepository.save(product);
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return "redirect:/user/add-product";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return "redirect:/user/add-product";
//        }
//    }






//            @RequestMapping("/added-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String addedProduct(
//            @Valid @ModelAttribute Product product,
//            BindingResult result,
//            Principal principal,
//            HttpSession session,
//            Model model) {
//
//        try {
//            // Get authenticated user
//            String email = principal.getName();
//            User user = userRepository.getUserByUserName(email);
//
//            String productUrl = product.getP_url();
//
//            // Check Redis Cache before scraping
//            ProductDetails cachedProduct = cacheService.getProductDetails(productUrl);
//            String productTitle = (cachedProduct != null) ? cachedProduct.getProductName() : null;
//            Double currentPrice = (cachedProduct != null) ? Double.valueOf(cachedProduct.getProductPrice()) : null;
//
//            if (productTitle == null) {
//                productTitle = productScraperService.scrapeProductTitle(productUrl);
//                currentPrice = productScraperService.scrapeProductPrice(productUrl);
//            }
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product details. Check URL.", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            product.setP_name(productTitle);
//
//            if (currentPrice != null && currentPrice <= product.getT_price().doubleValue()) {
//                session.setAttribute("message", new Message("Enter a valid threshold price!", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            String productImageUrl = (cachedProduct != null) ? cachedProduct.getProductImage() : productScraperService.scrapeProductImage(productUrl);
//
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image.", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            // Store in Redis Cache
//            cacheService.cacheProductDetails(productUrl, productTitle, productImageUrl);
//
//            // Save URL in Cassandra
////            URL url = new URL(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString(), productUrl, Instant.now(), 0);
////            urlService.save(Collections.singleton(url));
//
//                        URL url = new URL();
//            url.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
//            url.setUrl(productUrl);
//            url.setCreatedDate(Instant.now());
//            url.setTimesProcessed(0);
//            urlService.save(new HashSet<>() {{
//                add(url);
//            }});
//
//            LOG.info("URL processed: {}", productUrl);
//
//            // Check if product exists in Cassandra
//            Productapi existingProductApi = apiRepository.findByProductUrl(productUrl);
//            int productId = (existingProductApi != null) ? existingProductApi.getProduct_id() : sequenceGeneratorRepository.getNextProductId();
//
//            if (existingProductApi != null && currentPrice != null) {
//                existingProductApi.setProduct_price(String.valueOf(currentPrice));
//                apiRepository.save(existingProductApi);
//            } else {
//                // Save new product in Cassandra
////                Productapi newProductApi = new Productapi(productId, productTitle, UrlCoding.extractProductName(productUrl), productImageUrl, String.valueOf(currentPrice));
////                apiRepository.save(newProductApi);
//
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // Publish event to Kafka for price monitoring
//          //  kafkaService.send("product_price_updates", productUrl);
//
//            // Save product to Cassandra
//            product.setUserId(user.getU_id());
//            product.setP_id(productId);
//            productRepository.save(product);
//
//            session.setAttribute("message", new Message("Product added successfully!", "alert-success"));
//            return "redirect:/user/add-product";
//
//        } catch (Exception e) {
//            LOG.error("Error adding product: ", e);
//            session.setAttribute("message", new Message("Something went wrong. Try again.", "alert-danger"));
//            return "redirect:/user/add-product";
//        }
//    }


//previous working
//    @RequestMapping("/added-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String addedProduct(
//            @Valid @ModelAttribute Product product,
//            BindingResult result,
//            Principal principal,
//            HttpSession session,
//            Model model) {
//
//        try {
//            // Get authenticated user
//            String email = principal.getName();
//            User user = userRepository.getUserByUserName(email);
//
//            String productUrl = product.getP_url();
//
//            // Check Redis Cache before scraping
//            ProductDetails cachedProduct = cacheService.getProductDetails(productUrl);
//            String productTitle = (cachedProduct != null) ? cachedProduct.getProductName() : null;
//            String productImageUrl = (cachedProduct != null) ? cachedProduct.getProductImageUrl() : null;
//
//            // If product details are missing, scrape them
//            if (productTitle == null || productImageUrl == null) {
//                productTitle = (productTitle == null) ? productScraperService.scrapeProductTitle(productUrl) : productTitle;
//                productImageUrl = (productImageUrl == null) ? productScraperService.scrapeProductImage(productUrl) : productImageUrl;
//            }
//
//            // If title is missing, return with an error
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product details. Check URL.", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            // Scrape product price (not in cache)
//            String productPrice = String.valueOf(productScraperService.scrapeProductPrice(productUrl));
//
//            // Validate threshold price
//            if (productPrice != null && Double.valueOf(productPrice) <= product.getT_price().doubleValue()) {
//                session.setAttribute("message", new Message("Enter a valid threshold price!", "alert-danger"));
//                return "redirect:/user/add-product";
//            }
//
//            // Save URL in Cassandra
//            URL url = new URL();
//            url.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
//            url.setUrl(productUrl);
//            url.setCreatedDate(Instant.now());
//            url.setTimesProcessed(0);
////            urlService.save(Collections.singleton(url));
//            urlService.save(new HashSet<>() {{
//                add(url);
//            }});
//
//            LOG.info("URL processed: {}", productUrl);
//
//            // Check if product exists in Cassandra
//            Productapi existingProductApi = apiRepository.findByProductUrl(productUrl);
//            int productId = (existingProductApi != null) ? existingProductApi.getProduct_id() : sequenceGeneratorRepository.getNextProductId();
//
//            if (existingProductApi != null) {
//                existingProductApi.setProduct_price(productPrice);
//                apiRepository.save(existingProductApi);
//            } else {
//                // Save new product in Cassandra
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(productUrl);
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(productPrice != null ? productPrice : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // Publish event to Kafka for price monitoring
//            //kafkaService.send("product_price_updates", productUrl);
//
//            // Save product to Cassandra
//            product.setUserId(user.getU_id());
//            product.setP_name(productTitle);
//            product.setP_id(productId);
//            productRepository.save(product);
//
//            session.setAttribute("message", new Message("Product added successfully!", "alert-success"));
//            return "redirect:/user/add-product";
//
//        } catch (Exception e) {
//            LOG.error("Error adding product: ", e);
//            session.setAttribute("message", new Message("Something went wrong. Try again.", "alert-danger"));
//            return "redirect:/user/add-product";
//        }
//    }


    @RequestMapping("/added-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String addedProduct(
            @Valid @ModelAttribute Product product,
            BindingResult result,
            Principal principal,
            HttpSession session,
            Model model) {

        try {
            // Get authenticated user
            String email = principal.getName();
            User user = userRepository.getUserByUserName(email);
            String productUrl = product.getP_url();

            // Check Redis Cache before scraping
            ProductDetails cachedProduct = cacheService.getProductDetails(productUrl);
            String productTitle = (cachedProduct != null) ? cachedProduct.getProductName() : null;
            String productImageUrl = (cachedProduct != null) ? cachedProduct.getProductImageUrl() : null;

            // If product details are missing, scrape them
            if (productTitle == null || productImageUrl == null) {
                productTitle = (productTitle == null) ? productScraperService.scrapeProductTitle(productUrl) : productTitle;
                productImageUrl = (productImageUrl == null) ? productScraperService.scrapeProductImage(productUrl) : productImageUrl;
            }

            // If title is missing, return with an error
            if (productTitle == null) {
                session.setAttribute("message", new Message("Failed to retrieve product details. Check URL.", "alert-danger"));
                return "redirect:/user/add-product";
            }

            // Scrape product price (not in cache)
            String productPrice = String.valueOf(productScraperService.scrapeProductPrice(productUrl));

            // Validate threshold price only if price is valid
            if (productPrice != null && !productPrice.equalsIgnoreCase("null") && !productPrice.isBlank()) {
                try {
                    double currentPrice = Double.parseDouble(productPrice);
                    if (currentPrice <= product.getT_price().doubleValue()) {
                        session.setAttribute("message", new Message("Enter a valid threshold price!", "alert-danger"));
                        return "redirect:/user/add-product";
                    }
                } catch (NumberFormatException e) {
                    session.setAttribute("message", new Message("Invalid product price format.", "alert-danger"));
                    return "redirect:/user/add-product";
                }
            }

            // If price is null or blank
            if (productPrice == null || productPrice.isBlank() || productPrice.equalsIgnoreCase("null")) {
                LOG.warn("Product price is null or blank for URL: {}", productUrl);
                productPrice = "Currently Unavailable";
            }

            // Save URL in Cassandra
            URL url = new URL();
            url.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
            url.setUrl(productUrl);
            url.setCreatedDate(Instant.now());
            url.setTimesProcessed(0);
            urlService.save(Collections.singleton(url));

            LOG.info("URL processed: {}", productUrl);

            // Check if product already exists in Cassandra
            Productapi existingProductApi = apiRepository.findByProductUrl(productUrl);
            int productId = (existingProductApi != null)
                    ? existingProductApi.getProduct_id()
                    : sequenceGeneratorRepository.getNextProductId();

            if (existingProductApi != null) {
                existingProductApi.setProduct_price(
                        (productPrice != null && !productPrice.isBlank()) ? productPrice : "Currently Unavailable"
                );
                apiRepository.save(existingProductApi);
            } else {
                Productapi newProductApi = new Productapi();
                newProductApi.setProduct_id(productId);
                newProductApi.setProductName(productTitle);
                newProductApi.setProduct_url(productUrl);
                newProductApi.setProduct_image(productImageUrl);
                newProductApi.setProduct_price(
                        (productPrice != null && !productPrice.isBlank()) ? productPrice : "Currently Unavailable"
                );
                apiRepository.save(newProductApi);
            }

            // Save product details
            product.setUserId(user.getU_id());
            product.setP_name(productTitle);
            product.setP_id(productId);
            productRepository.save(product);

            session.setAttribute("message", new Message("Product added successfully!", "alert-success"));
            return "redirect:/user/add-product";

        } catch (Exception e) {
            LOG.error("Error adding product: ", e);
            session.setAttribute("message", new Message("Something went wrong. Try again.", "alert-danger"));
            return "redirect:/user/add-product";
        }
    }




//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                                BindingResult result,
//                                                Principal principal,
//                                                HttpSession session, Model model) {
//
//
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Log the incoming product details
//            // System.out.println("Received Product URL: " + product.getP_url());
//            // System.out.println("Received Threshold Price: " + product.getT_price());
//
//            // Check if the product exists in the ProductAPI repository
//            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
//
//            // Scrape product details from the Amazon URL
//            String productUrl = product.getP_url();
//
//
//            URL url = new URL();
//
//            long startTime = System.currentTimeMillis();
//            url.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
//            url.setUrl(productUrl);
//            //url.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//            url.setCreatedDate(Instant.now()); //  Correct
//
//            url.setTimesProcessed(0);
//            // urlService.save(url);
//            LOG.info("URL received:{}", productUrl);
//            //System.out.println(url);
//            urlService.save(new HashSet<>() {{
//                add(url);
//            }});
//            LOG.info("Request processed in {} mills", (System.currentTimeMillis() - startTime));
//            // return ResponseEntity.ok().build(); //public ResponseEntity<Void> submitURL(@RequestBody URL url) {
//            // return ResponseEntity.ok(url); //public ResponseEntity<URL> submitURL(@RequestBody URL url) {
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//            // Check for scraped title
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            // Set the product name from scraped title
//            product.setP_name(productTitle);
//
//            // Set the current price in the product object
//            product.setT_price(product.getT_price()); // Assuming product_price is a String
//
//            // Check threshold price logic
//            if (currentPrice != null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!!");
//            }
//
//            String productImageUrl;
//            if (existingProductApi != null) {
//                // Product exists in productapi table, use its image URL
//                productImageUrl = existingProductApi.getProduct_image();
//                System.out.println("Product already exists in ProductAPI. Using existing image URL: " + productImageUrl);
//
//                // Update the product price in productapi if currentPrice is available
//                if (currentPrice != null) {
//                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
//                    apiRepository.save(existingProductApi);
//                    System.out.println("Product price updated in ProductAPI with currentPrice: " + currentPrice);
//                }
//            } else {
//                // Product does not exist in productapi, scrape the image URL
//                productImageUrl = productScraperService.scrapeProductImage(productUrl);
//
//                if (productImageUrl != null) {
//                    // Save the scraped image URL to productapi for future use
//                    Productapi newProductApi = new Productapi();
//                    newProductApi.setProductName(product.getP_name());
//                    newProductApi.setProduct_url(UrlCoding.extractProductName(product.getP_url()));
//                    newProductApi.setProduct_image(productImageUrl);
//                    //newPrdId = sequenceGeneratorRepository.getNextProductId();
//                    //newProductApi.setProduct_id(product.getP_id()); // S
//                    newProductApi.setProduct_id(newPrdId); // S
//
//                    if (currentPrice != null)
//                        newProductApi.setProduct_price(String.valueOf(currentPrice));
//                    else
//                        newProductApi.setProduct_price("Currently Unavailable");
//
//                    apiRepository.save(newProductApi);
//                    System.out.println("Scraped product saved to ProductAPI table.");
//                } else {
//                    session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//                }
//            }
//
//            // Set the product image URL in the session
//            session.setAttribute("productImage", productImageUrl);
//
//            // Check for validation errors after scraping and setting the product fields
//            if (result.hasErrors()) {
//                result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
//                model.addAttribute("product", product);
//                model.addAttribute("user", user);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors occurred. Please check the provided information.");
//            }
//
//// Generate a new product ID using the sequence generator
//           //int newProductId = sequenceGeneratorRepository.getNextProductId();
//            //product.setP_id(newProductId); // Set the unique product ID
//            product.setP_id(newPrdId); // Set the unique product ID
//            // Save the product entity
//            // product.setUser(user); // Associate product with the user
//            product.setUser_id(user.getU_id()); //  Store only the user's ID
//
//            productRepository.save(product); // Save the product to the repository
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception for debugging purposes
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//
//
//    }






//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                                BindingResult result,
//                                                Principal principal,
//                                                HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Generate a new ID for this product (ensures the same ID is used in both tables)
//            int newProductId = sequenceGeneratorRepository.getNextProductId();
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!!");
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//            }
//
//            // Insert into `productapi`
//            Productapi newProductApi = new Productapi();
//            newProductApi.setProduct_id(newProductId); // Use the same generated ID
//            newProductApi.setProductName(productTitle);
//            newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//            newProductApi.setProduct_image(productImageUrl);
//            newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//            apiRepository.save(newProductApi);
//
//            // Insert into `product`
//            product.setP_id(newProductId); // Use the same generated ID
//            product.setUser_id(user.getU_id());
//            productRepository.save(product);
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//    }



//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                                BindingResult result,
//                                                Principal principal,
//                                                HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!!");
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//            }
//
//            // Check if the product URL exists in `productapi`
//            Productapi existingProductApi = apiRepository.findByProductUrl(UrlCoding.extractProductName(productUrl));
//
//            int productId;
//            if (existingProductApi != null) {
//                // Use existing product_id
//                productId = existingProductApi.getProduct_id();
//            } else {
//                // Generate a new product_id and insert into `productapi`
//                productId = sequenceGeneratorRepository.getNextProductId();
//
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // Insert into `product` table
//            product.setP_id(productId); // Use the same ID
//            product.setUser_id(user.getU_id());
//            productRepository.save(product);
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//    }


//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                                BindingResult result,
//                                                Principal principal,
//                                                HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!!");
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//            }
//
//            // Check if the product URL exists in `productapi`
//            Productapi existingProductApi = apiRepository.findByProductUrl(UrlCoding.extractProductName(productUrl));
//
//            int productId;
//            if (existingProductApi != null) {
//                // Use existing product_id
//                productId = existingProductApi.getProduct_id();
//            } else {
//                // Generate a new product_id and insert into `productapi`
//                productId = sequenceGeneratorRepository.getNextProductId();
//
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // **Always insert a new row in `product` table**
//            Product newProductEntry = new Product();
//            newProductEntry.setP_id(productId); // Use the same ID
//            newProductEntry.setUser_id(user.getU_id());
//            newProductEntry.setP_name(productTitle);
//            newProductEntry.setP_url(productUrl);
//            newProductEntry.setT_price(product.getT_price());
//
//            productRepository.save(newProductEntry);
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//    }


//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                                BindingResult result,
//                                                Principal principal,
//                                                HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!!");
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//            }
//
//            // Check if the product URL exists in `productapi`
//            Productapi existingProductApi = apiRepository.findByProductUrl(UrlCoding.extractProductName(productUrl));
//
//            int productId;
//            if (existingProductApi != null) {
//                // Use existing product_id
//                productId = existingProductApi.getProduct_id();
//            } else {
//                // Generate a new product_id and insert into `productapi`
//                productId = sequenceGeneratorRepository.getNextProductId();
//
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // **Always insert a new row in `product` table**
//            Product newProductEntry = new Product();
//            //newProductEntry.setP_id(0);  // **Ensure Hibernate treats it as a new row**
//            newProductEntry.setP_id(null); // Hibernate treats it as a new row and auto-generates the ID
//            newProductEntry.setUser_id(user.getU_id());
//            newProductEntry.setP_name(productTitle);
//            newProductEntry.setP_url(productUrl);
//            newProductEntry.setT_price(product.getT_price());
//
//            productRepository.save(newProductEntry);
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//    }



//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                                BindingResult result,
//                                                Principal principal,
//                                                HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!!");
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//            }
//
//            // Check if the product URL exists in `productapi`
//            Productapi existingProductApi = apiRepository.findByProductUrl(UrlCoding.extractProductName(productUrl));
//
//            int productId;
//            if (existingProductApi != null) {
//                // Use existing product_id from productapi table
//                productId = existingProductApi.getProduct_id();
//            } else {
//                // Generate a new unique product_id using the sequence generator
//                productId = sequenceGeneratorRepository.getNextProductId();  // Use the sequence generator to get a new product ID
//
//                // Insert into `productapi` table
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);  // Use the generated product_id
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // Insert into `product` table with the same unique product_id
//            product.setP_id(productId);  // Use the same product_id for insertion
//            product.setUser_id(user.getU_id());
//            productRepository.save(product);  // Insert a new row into the product table
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//    }


//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                                BindingResult result,
//                                                Principal principal,
//                                                HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!!");
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//            }
//
//            // Check if the product URL exists in `productapi`
//            Productapi existingProductApi = apiRepository.findByProductUrl(productUrl);
//
//            int productId;
//            if (existingProductApi != null) {
//                // Use existing product_id from productapi table
//                productId = existingProductApi.getProduct_id();
//                System.out.println("Found existing product with ID: " + productId);  // Debugging line
//            } else {
//                // Generate a new unique product_id using the sequence generator
//                productId = sequenceGeneratorRepository.getNextProductId();  // Use the sequence generator to get a new product ID
//                System.out.println("Generated new product ID: " + productId);  // Debugging line
//
//                // Insert into `productapi` table
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);  // Use the generated product_id
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // Insert into `product` table with a new unique UUID for every insertion
//            product.setUser_id(user.getU_id());  // Store user_id
//
//            // Set the productId to the product's p_id
//            product.setP_id(productId);  // Make sure p_id is set here
//
//            // Create ProductKey with composite primary key
//            ProductKey key = new ProductKey(productId, product.getT_price());  // Use p_id and t_price combination as the key
//            product.setKey(key);  // Set the key
//
//            productRepository.save(product);  // Insert a new row into the product table
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//    }


//for react

//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                                BindingResult result,
//                                                Principal principal,
//                                                HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = userRepository.getUserByUserName(email);
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price().doubleValue()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!");
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//            }
//
//            // Check if the product URL exists in `productapi`
//            Productapi existingProductApi = apiRepository.findByProductUrl(productUrl);
//
//            int productId;
//            if (existingProductApi != null) {
//                // Use existing product_id from productapi table
//                productId = existingProductApi.getProduct_id();
//            } else {
//                // Generate a new unique product_id using sequence generator
//                productId = sequenceGeneratorRepository.getNextProductId();
//
//                // Insert into `productapi` table
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // Insert into `product` table (Cassandra)
//            product.setUserId(user.getU_id());  // Store user_id
//            product.setP_id(productId);  // Set unique p_id
//            //product.setT_price(currentPrice != null ? currentPrice : 0.0); // Ensure a valid price
//            product.setT_price(product.getT_price());
//
//            productRepository.save(product);  // Save product in Cassandra
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//    }



//    @PostMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
//                                                BindingResult result,
//                                                Principal principal,
//                                                HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = userRepository.getUserByUserName(email);
//
//            // Scrape product details
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
//            }
//
//            product.setP_name(productTitle);
//
//            // Validate threshold price
//            if (currentPrice != null && currentPrice <= product.getT_price().doubleValue()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!");
//            }
//
//            // Scrape product image
//            String productImageUrl = productScraperService.scrapeProductImage(productUrl);
//            if (productImageUrl == null) {
//                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
//            }
//
//            // Save URL entity
//            URL url = new URL();
//            url.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
//            url.setUrl(productUrl);
//            url.setCreatedDate(Instant.now());
//            url.setTimesProcessed(0);
//            urlService.save(new HashSet<>() {{ add(url); }});
//            LOG.info("URL received: {}", productUrl);
//
//            // Check if the product URL exists in `productapi`
//            Productapi existingProductApi = apiRepository.findByProductUrl(productUrl);
//
//            int productId;
//            if (existingProductApi != null) {
//                // Use existing product_id from productapi table
//                productId = existingProductApi.getProduct_id();
//            } else {
//                // Generate a new unique product_id using sequence generator
//                productId = sequenceGeneratorRepository.getNextProductId();
//
//                // Insert into `productapi` table
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_id(productId);
//                newProductApi.setProductName(productTitle);
//                newProductApi.setProduct_url(UrlCoding.extractProductName(productUrl));
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                apiRepository.save(newProductApi);
//            }
//
//            // Insert into `product` table (Cassandra)
//            product.setUserId(user.getU_id());  // Store user_id
//            product.setP_id(productId);  // Set unique p_id
//            product.setT_price(product.getT_price());
//
//            productRepository.save(product);  // Save product in Cassandra
//
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
//        }
//    }


    @PostMapping("/added-products")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
                                                BindingResult result,
                                                Principal principal) {

        try {
            // Get authenticated user
            String email = principal.getName();
            User user = userRepository.getUserByUserName(email);

            String productUrl = product.getP_url();

            // Check Redis Cache before scraping
            ProductDetails cachedProduct = cacheService.getProductDetails(productUrl);
            String productTitle = (cachedProduct != null) ? cachedProduct.getProductName() : null;
            String productImageUrl = (cachedProduct != null) ? cachedProduct.getProductImageUrl() : null;

            // If product details are missing, scrape them
            if (productTitle == null || productImageUrl == null) {
                productTitle = (productTitle == null) ? productScraperService.scrapeProductTitle(productUrl) : productTitle;
                productImageUrl = (productImageUrl == null) ? productScraperService.scrapeProductImage(productUrl) : productImageUrl;
            }

            // If title is missing, return an error response
            if (productTitle == null) {
                return ResponseEntity.badRequest().body("Failed to retrieve product details. Check URL.");
            }

            // Scrape product price (not in cache)
            String productPrice = String.valueOf(productScraperService.scrapeProductPrice(productUrl));

            // Validate threshold price
            if (productPrice != null && Double.valueOf(productPrice) <= product.getT_price().doubleValue()) {
                return ResponseEntity.badRequest().body("Enter a valid threshold price!");
            }

            // Save URL in Cassandra
            URL url = new URL();
            url.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
            url.setUrl(productUrl);
           // url.setCreatedDate(Instant.now());
           // url.setCreatedDate(Instant.now().atZone(ZoneId.of("Asia/Kolkata")));
            url.setCreatedDate(Instant.now());

            url.setTimesProcessed(0);
            urlService.save(Collections.singleton(url));

            LOG.info("URL processed: {}", productUrl);

            // Check if product exists in Cassandra
            Productapi existingProductApi = apiRepository.findByProductUrl(productUrl);
            int productId = (existingProductApi != null) ? existingProductApi.getProduct_id() : sequenceGeneratorRepository.getNextProductId();

            if (existingProductApi != null) {
                existingProductApi.setProduct_price(productPrice);
                apiRepository.save(existingProductApi);
            } else {
                // Save new product in Cassandra
                Productapi newProductApi = new Productapi();
                newProductApi.setProduct_id(productId);
                newProductApi.setProductName(productTitle);
                newProductApi.setProduct_url(productUrl);
                newProductApi.setProduct_image(productImageUrl);
                newProductApi.setProduct_price(productPrice != null ? productPrice : "Currently Unavailable");

                apiRepository.save(newProductApi);
            }

            // Save product to Cassandra
            product.setUserId(user.getU_id());
            product.setP_name(productTitle);
            product.setP_id(productId);
            productRepository.save(product);

            return ResponseEntity.ok("Product added successfully!");

        } catch (Exception e) {
            LOG.error("Error adding product: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Try again.");
        }
    }




    private boolean isProductExistsForUser(Product product, User user) {
        //List<Product> userProducts = user.getProduct();
        List<Product> userProducts = productRepository.findProductsByUserId(user.getU_id());

        for (Product userProduct : userProducts) {
            if (userProduct.getP_name().equals(product.getP_name()) && userProduct.getP_url().equals(product.getP_url())) {
                return true; // Product already exists for the user
            }
        }
        return false; // Product does not exist for the user
    }

//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @RequestMapping("/product-list")
//    public String List(Model model, Principal principal) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//            //List<Product> products = user.getProduct();
//            List<Product> products = productRepository.findProductsByUserId(user.getU_id());
//
//            model.addAttribute("user", user);
//            model.addAttribute("list", products);
//            model.addAttribute("productService", new ProductService());
//            model.addAttribute("UrlCoding", new UrlCoding());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "product_list1";
//    }


        @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping("/product-list")
    public String List(Model model, Principal principal) {
        logger.debug("Entering the product-list endpoint.");

        try {
            String email = principal.getName();
            logger.debug("User email from principal: {}", email);

            User user = this.userRepository.getUserByUserName(email);
            if (user == null) {
                logger.error("User not found for email: {}", email);
                model.addAttribute("error", "User not found.");
                return "error";  // Or another appropriate error page
            }
            logger.debug("User found: {}", user);

            List<Product> products = productRepository.findProductsByUserId(user.getU_id());
            if (products == null || products.isEmpty()) {
                logger.warn("No products found for user with ID: {}", user.getU_id());
            } else {
                logger.debug("Found {} products for user: {}", products.size(), user.getU_id());
            }

            model.addAttribute("user", user);
            model.addAttribute("list", products);
            model.addAttribute("productService", new ProductService());
            model.addAttribute("UrlCoding", new UrlCoding());

        } catch (Exception e) {
            logger.error("An error occurred while fetching product list", e);
            model.addAttribute("error", "An error occurred while fetching products.");
            return "error";  // Or another appropriate error page
        }

        logger.debug("Returning view 'product_list1'.");
        return "product_list1";
    }



    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/products-list")
    public ResponseEntity<List<Product>> listProducts(Principal principal) {
        List<Product> products = null;
        try {
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);
            //products = user.getProduct();
            products = productRepository.findProductsByUserId(user.getU_id());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 in case of error
        }
        return ResponseEntity.ok(products); // Return the list of products in the response body
    }

//
//    @RequestMapping("/deleteproduct/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String DeleteProduct(@PathVariable("productID") int productID,Principal principal,HttpSession session){
//        try {
//            Optional<Product> product = this.productRepository.findById(productID);
//            //if(product.get().getUser() == this.userRepository.getUserByUserName(principal.getName()))
//            //if(product.getUserId() == this.userRepository.getUserByUserName(principal.getName()))
//            //if (product.getUserId() == this.userRepository.getUserByUserName(principal.getName()).getU_id())
//            if (product.getUser(user) == this.userRepository.getUserByUserName(principal.getName()))
//
//            {
//                Product pro = product.get();
//                User user = pro.getUser();
//                user.getProduct().remove(pro);
////                Optional<User> userOptional = userRepository.findById(userId);
////                User user1 = userOptional.get();
////                Optional<Product> productToRemove = userProducts.stream()
////                        .filter(p -> p.getP_id() == productId)
////                        .findFirst();
////                productRepository.deleteProductByUserIdAndProductId(user1.getU_id(), productId);
//
//
//
//                this.userRepository.save(user); // Update the user entity
//                this.productRepository.deleteById(productID); // Delete the product
//            }else {
//                session.setAttribute("message", new Message("You cannot access other's product!", "alert-danger"));
//                System.out.println("you are accessing other contact");
//                return "redirect:/user/dashboard";
//            }
//
//
//        } catch (Exception e) {
//            // Log the exception
//            e.printStackTrace();
//        }
//
//        return "redirect:/user/product-list";
//    }


//    @RequestMapping("/deleteproduct/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String DeleteProduct(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        try {
//            Optional<Product> productOptional = this.productRepository.findById(productID);
//
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                User user = this.userRepository.getUserByUserName(principal.getName());
//
//                if (product.getUserId() == user.getU_id()) {
//                    this.productRepository.deleteById(productID);
//                } else {
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return "redirect:/user/dashboard";
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "redirect:/user/product-list";
//    }


//    @RequestMapping("/deleteproduct/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String DeleteProduct(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        try {
//            Optional<Product> productOptional = this.productRepository.findById(productID);
//
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                User user = this.userRepository.getUserByUserName(principal.getName());
//
//                if (product.getUserId() == user.getU_id()) {
//                    // Use deleteProductByIdAndPrice method with both p_id and t_price
//                    this.productRepository.deleteProductByIdAndPrice(productID, product.getT_price());
//                } else {
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return "redirect:/user/dashboard";
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "redirect:/user/product-list";
//    }

//    @RequestMapping("/deleteproduct/{productID}/{price}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String DeleteProduct(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        try {
//            Optional<Product> productOptional = this.productRepository.findById(productID);
//
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                User user = this.userRepository.getUserByUserName(principal.getName());
//
//                if (product.getUserId() == user.getU_id()) {
//                    // Use deleteProductByIdAndPrice method with both p_id and t_price
//                    this.productRepository.deleteProductByIdAndPrice(productID, price);
//                } else {
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return "redirect:/user/dashboard";
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "redirect:/user/product-list";
//    }



//    @RequestMapping("/deleteproduct/{productID}/{price}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String DeleteProduct(@PathVariable("productID") int productID,
//                                @PathVariable("price") BigDecimal price,  // Change to BigDecimal
//                                Principal principal,
//                                HttpSession session) {
//        try {
//            Optional<Product> productOptional = this.productRepository.findById(productID);
//
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                User user = this.userRepository.getUserByUserName(principal.getName());
//
//                if (product.getUserId() == user.getU_id()) {
//                    // Use deleteProductByIdAndPrice method with both p_id and t_price
//                    this.productRepository.deleteProductByIdAndPrice(productID, price);
//                } else {
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return "redirect:/user/dashboard";
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//       // productRepository.deleteProductByIdAndPrice(productID, price);
//        return "redirect:/user/product-list";
//    }


//    @RequestMapping("/deleteproduct/{productID}/{price}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String DeleteProduct(@PathVariable("productID") int productID,
//                                @PathVariable("price") BigDecimal price,  // Change to BigDecimal
//                                Principal principal,
//                                HttpSession session) {
//        try {
//            System.out.println("DELETE REQUEST RECEIVED - Product ID: " + productID + ", Price: " + price);
//
//            Optional<Product> productOptional = this.productRepository.findById(productID);
//            String username = principal.getName();
//            System.out.println("Authenticated User: " + username);
//
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                System.out.println("Product found: " + product);
//
//                User user = this.userRepository.getUserByUserName(username);
//                System.out.println("User found in DB: " + user);
//
//                if (product.getUserId() == user.getU_id()) {
//                    System.out.println("User is authorized to delete this product. Proceeding with deletion...");
//
//                    // Use deleteProductByIdAndPrice method with both p_id and t_price
//                    this.productRepository.deleteProductByIdAndPrice(productID, price);
//
//                    System.out.println("Product successfully deleted.");
//                } else {
//                    System.out.println("Unauthorized access attempt. Product owner ID: " + product.getUserId() + ", User ID: " + user.getU_id());
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return "redirect:/user/dashboard";
//                }
//            } else {
//                System.out.println("Product not found with ID: " + productID);
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//            }
//
//        } catch (Exception e) {
//            System.out.println("Exception occurred while deleting product: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return "redirect:/user/product-list";
//    }


    @RequestMapping("/deleteproducts/{productID}/{price}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String DeleteProduct(@PathVariable("productID") int productID,
                                @PathVariable("price") BigDecimal price,
                                Principal principal,
                                HttpSession session) {
        try {
            List<Product> products = this.productRepository.findProductById(productID); // Use custom query
            if (!products.isEmpty()) {
                Product product = products.get(0); // Get the first product
                User user = this.userRepository.getUserByUserName(principal.getName());

                if (product.getUserId() == user.getU_id()) {
                    System.out.println("Deleting product with ID: " + productID + " and Price: " + price);
                    this.productRepository.deleteProductByIdAndPrice(productID, price);
                    System.out.println("Product deleted successfully.");
                } else {
                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
                    return "redirect:/user/dashboard";
                }
            } else {
                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/user/product-list";
    }




//    @RequestMapping("/deleteproducts/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<List<Product>> DeleteProducts(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        List<Product> updatedProductList = new ArrayList<>(); // Initialize with an empty list
//
//        try {
//            // Find the product by ID
//            Optional<Product> product = this.productRepository.findById(productID);
//
//            if (product.isPresent()) {
//                Product pro = product.get();
//                User currentUser = this.userRepository.getUserByUserName(principal.getName());
//
//                // Check if the product belongs to the logged-in user
//                if (pro.getUser().equals(currentUser)) {
//                    // Remove the product from the user's product list
//                    currentUser.getProduct().remove(pro);
//
//                    // Save the updated user entity
//                    this.userRepository.save(currentUser);
//
//                    // Delete the product from the repository
//                    this.productRepository.deleteById(productID);
//
//                    // Fetch the updated product list for the user
//                    updatedProductList = currentUser.getProduct();
//                    return ResponseEntity.ok(updatedProductList); // Return 200 OK with the updated list
//                } else {
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(updatedProductList); // Return 403 Forbidden
//                }
//            } else {
//                // If product is not found
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedProductList); // Return 404 Not Found
//            }
//
//        } catch (Exception e) {
//            // Log the exception and return 500 Internal Server Error
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedProductList); // Return 500 with an empty list
//        }
//    }

//    @RequestMapping("/deleteproducts/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<List<Product>> DeleteProducts(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        List<Product> updatedProductList = new ArrayList<>(); // Initialize with an empty list
//
//        try {
//            // Find the product by ID
//            Optional<Product> productOptional = this.productRepository.findById(productID);
//
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                User currentUser = this.userRepository.getUserByUserName(principal.getName());
//
//                // Check if the product belongs to the logged-in user
//                if (product.getUserId() == currentUser.getU_id()) {
//                    // Delete the product from the repository
//                    this.productRepository.deleteById(productID);
//
//                    // Fetch the updated product list for the user
//                    updatedProductList = this.productRepository.findByUserId(currentUser.getU_id());
//                    return ResponseEntity.ok(updatedProductList); // Return 200 OK with the updated list
//                } else {
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(updatedProductList); // Return 403 Forbidden
//                }
//            } else {
//                // If product is not found
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedProductList); // Return 404 Not Found
//            }
//
//        } catch (Exception e) {
//            // Log the exception and return 500 Internal Server Error
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedProductList); // Return 500 with an empty list
//        }
//    }



//    @RequestMapping("/deleteproducts/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<List<Product>> DeleteProducts(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        List<Product> updatedProductList = new ArrayList<>(); // Initialize with an empty list
//
//        try {
//            // Find the product by ID
//            //Optional<Product> productOptional = this.productRepository.findById(productID);
//            Product productOptional = productRepository.findProductByIdAndPrice(productID, product.getT_price());
//
//
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                User currentUser = this.userRepository.getUserByUserName(principal.getName());
//
//                // Check if the product belongs to the logged-in user
//                if (product.getUserId() == currentUser.getU_id()) {
//                    // Delete the product using both p_id and t_price (composite primary key)
//                    this.productRepository.deleteProductByIdAndPrice(productID, product.getT_price());
//
//                    // Fetch the updated product list for the user
//                    updatedProductList = this.productRepository.findByUserId(currentUser.getU_id());
//                    return ResponseEntity.ok(updatedProductList); // Return 200 OK with the updated list
//                } else {
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(updatedProductList); // Return 403 Forbidden
//                }
//            } else {
//                // If product is not found
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedProductList); // Return 404 Not Found
//            }
//
//        } catch (Exception e) {
//            // Log the exception and return 500 Internal Server Error
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedProductList); // Return 500 with an empty list
//        }
//    }



//    @RequestMapping("/deleteproducts/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<List<Product>> deleteProducts(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        List<Product> updatedProductList = new ArrayList<>(); // Initialize with an empty list
//
//        try {
//            // Find the product by ID first
//            Product product = productRepository.findProductById(productID); // Fetch product by p_id only
//
//            if (product != null) {
//                User currentUser = this.userRepository.getUserByUserName(principal.getName());
//
//                // Check if the product belongs to the logged-in user
//                if (product.getUserId() == currentUser.getU_id()) {
//                    // Delete the product using both p_id and t_price (composite primary key)
//                    this.productRepository.deleteProductByIdAndPrice(productID, product.getT_price());
//
//                    // Fetch the updated product list for the user
//                    updatedProductList = this.productRepository.findByUserId(currentUser.getU_id());
//                    return ResponseEntity.ok(updatedProductList); // Return 200 OK with the updated list
//                } else {
//                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(updatedProductList); // Return 403 Forbidden
//                }
//            } else {
//                // If product is not found
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedProductList); // Return 404 Not Found
//            }
//
//        } catch (Exception e) {
//            // Log the exception and return 500 Internal Server Error
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedProductList); // Return 500 with an empty list
//        }
//    }



//    @RequestMapping("/deleteproducts/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<List<Product>> deleteProducts(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        List<Product> updatedProductList = new ArrayList<>(); // Initialize with an empty list
//
//        try {
//            // Fetch product by p_id, and handle the case where there might be multiple results
//            List<Product> products = productRepository.findProductById(productID);
//
//            if (products.isEmpty()) {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedProductList); // Return 404 Not Found
//            }
//
//            // Assuming only one result is expected, you can handle multiple results as needed
//            Product product = products.get(0);  // Use the first product (or handle accordingly if there are more)
//
//            User currentUser = this.userRepository.getUserByUserName(principal.getName());
//
//            // Check if the product belongs to the logged-in user
//            if (product.getUserId() == currentUser.getU_id()) {
//                // Delete the product using both p_id and t_price (composite primary key)
//                this.productRepository.deleteProductByIdAndPrice(productID, product.getT_price());
//
//                // Fetch the updated product list for the user
//                updatedProductList = this.productRepository.findByUserId(currentUser.getU_id());
//                return ResponseEntity.ok(updatedProductList); // Return 200 OK with the updated list
//            } else {
//                session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(updatedProductList); // Return 403 Forbidden
//            }
//
//        } catch (Exception e) {
//            // Log the exception and return 500 Internal Server Error
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedProductList); // Return 500 with an empty list
//        }
//    }




//    @RequestMapping("/editproduct/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String EditProduct(@PathVariable("productID") int productID,Model model,Principal principal,HttpSession session){
//        try{
//            Optional<Product> product = this.productRepository.findById(productID);
//            if(product.get().getUser() == this.userRepository.getUserByUserName(principal.getName())){
//                //username
//                String email = principal.getName();
//                User user = this.userRepository.getUserByUserName(email);
//                model.addAttribute("user",user);
//
//                //edit
//
//                Product pro = product.get();
////            System.out.println(pro.getP_name()+" "+pro.getP_url()+" "+pro.getT_price());
//                model.addAttribute("product",pro);
//                return "edit";
//            }else {
//                session.setAttribute("message", new Message("You are the owner of that product!", "alert-danger"));
//                System.out.println("you are accessing other contact");
//                return "redirect:/user/dashboard";
//            }
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "edit";
//    }

//now commented
//    @RequestMapping("/editproduct/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String EditProduct(@PathVariable("productID") int productID, Model model, Principal principal, HttpSession session) {
//        try {
//            Optional<Product> productOptional = this.productRepository.findById(productID);
//
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                User currentUser = this.userRepository.getUserByUserName(principal.getName());
//
//                // Check if the product belongs to the logged-in user using user_id
//                if (product.getUserId() == currentUser.getU_id()) {
//                    // Add user details to model
//                    model.addAttribute("user", currentUser);
//
//                    // Add product details to model
//                    model.addAttribute("product", product);
//                    return "edit";
//                } else {
//                    session.setAttribute("message", new Message("You cannot edit another user's product!", "alert-danger"));
//                    return "redirect:/user/dashboard";
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return "redirect:/user/dashboard";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "edit";
//    }


//    @RequestMapping("/edit-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String Edit_Product(@ModelAttribute Product product,Principal principal,HttpSession session){
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//            if(product.getUser(user) == this.userRepository.getUserByUserName(principal.getName())){
//                //product.setUser(user);
//                product.setUser_id(user.getU_id()); //  Store only the user's ID
//
//                this.productRepository.save(product);
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//            }else {
//                session.setAttribute("message", new Message("You are the owner of that product!", "alert-danger"));
//                System.out.println("user"+user);
//                System.out.println("product.getuser"+product.getUser() );
//                System.out.println("product.getuserbyname"+this.userRepository.getUserByUserName(principal.getName()));
//                System.out.println("you are accessing other contact");
//                return "redirect:/user/dashboard";
//            }
//
//
//        }catch (Exception e){
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//
//        }
//        return "redirect:/user/product-list";
//    }



    //now commented
//    @RequestMapping("/edit-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String Edit_Product(@ModelAttribute Product product, Principal principal, HttpSession session) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Find the existing product to check ownership
//            Optional<Product> existingProductOptional = this.productRepository.findById(product.getP_id());
//
//            if (existingProductOptional.isPresent()) {
//                Product existingProduct = existingProductOptional.get();
//
//                // Check if the product belongs to the logged-in user using user_id
//                if (existingProduct.getUserId() == user.getU_id()) {
//                    product.setUser_id(user.getU_id()); // Store only user ID (Cassandra way)
//                    this.productRepository.save(product); // Save updated product
//
//                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                } else {
//                    session.setAttribute("message", new Message("You cannot edit another user's product!", "alert-danger"));
//                    return "redirect:/user/dashboard";
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return "redirect:/user/dashboard";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//        }
//        return "redirect:/user/product-list";
//    }


//    @RequestMapping(value = "/edit-products/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<Product> getProductForEdit(@PathVariable Integer id, Principal principal) {
//        // Fetch the product using the provided ID
//        Product product = this.productRepository.findById(id).orElse(null);
//        if (product == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if product not found
//        }
//        // Return the product data
//        return ResponseEntity.ok(product);
//    }


//    @RequestMapping(value = "/edit-products/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<Product> getProductForEdit(@PathVariable Integer id, Principal principal) {
//        // Fetch the product using the provided ID
//        List<Product> products = this.productRepository.findProductById(id);
//
//        // Check if the product is found
//        if (products.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if product not found
//        }
//
//        // Assuming the list contains only one product since p_id is unique
//        Product product = products.get(0);
//
//        // Optionally, check the role or ownership of the product if needed
//        // If needed, you can check if the principal matches the product's owner
//        // For example:
//        // if (!product.getUserId().equals(principal.getName())) {
//        //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        // }
//
//        // Return the product data
//        return ResponseEntity.ok(product);
//    }



//@RequestMapping(value = "/edit-products/{id}/{price}", method = RequestMethod.GET)
//@PreAuthorize("hasAuthority('ROLE_USER')")
//public ResponseEntity<Product> getProductForEdit(@PathVariable Integer id, @PathVariable BigDecimal price, Principal principal) {
//    // Fetch the product using the provided ID and price
//    List<Product> products = this.productRepository.findProductByIdAndPrice(id, price);
//
//    // Check if the product is found
//    if (products.isEmpty()) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if product not found
//    }
//
//    // Assuming the list contains only one product since the combination of p_id and t_price is unique
//    Product product = products.get(0);
//
//    // Optionally, check the role or ownership of the product if needed
//    // If needed, you can check if the principal matches the product's owner
//    // For example:
//    // if (!product.getUserId().equals(principal.getName())) {
//    //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//    // }
//
//    // Return the product data
//    return ResponseEntity.ok(product);
//}


    //below are for thymeleaf
//    @GetMapping("/edit-product/{id}/{price}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String getProductForEdit(
//            @PathVariable Integer id,
//            @PathVariable BigDecimal price,
//            Principal principal,
//            Model model) {
//
//        // Fetch the product using the provided ID and price
//        Product product = this.productRepository.findProductByIdAndPrice(id, price);
//
//        // Check if the product is found
//        if (product == null) {
//            return "redirect:/user/product-list"; // Redirect if product not found
//        }
//
//        // Add the product to the model for Thymeleaf rendering
//        model.addAttribute("product", product);
//
//        return "edit";  // ✅ This will render edit-product.html
//    }

    @GetMapping("/edit-product/{id}/{price}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getProductForEdit(@PathVariable Integer id,
                                    @PathVariable BigDecimal price,
                                    Model model,
                                    Principal principal) {
        // Fetch product
        Product product = this.productRepository.findProductByIdAndPrice(id, price);

        if (product == null) {
            return "redirect:/user/product-list"; // Redirect if product not found
        }

        // Fetch logged-in user
        User user = this.userRepository.getUserByUserName(principal.getName());

        if (user == null) {
            return "redirect:/login"; // Redirect if user not found
        }

        model.addAttribute("product", product);
        model.addAttribute("user", user);  // ✅ Add user to model

        return "edit";  // Thymeleaf template name (edit.html)
    }





    @PostMapping("/edit-product/{price}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String editProduct(
            @PathVariable("price") BigDecimal oldPrice,
            @ModelAttribute("product") Product product,
            Principal principal,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Received request to edit product: " + product);

            // Step 1: Get logged-in user email
            String email = principal.getName();
            System.out.println("Fetching user details for email: " + email);
            User user = this.userRepository.getUserByUserName(email);

            if (user == null) {
                System.out.println("Error: User not found for email: " + email);
                redirectAttributes.addFlashAttribute("message", new Message("User not found!", "alert-danger"));
                return "redirect:/user/product-list";
            }

            System.out.println("Logged-in user ID: " + user.getU_id());

            // Step 2: Find existing product using p_id and oldPrice
            System.out.println("Searching for product with ID: " + product.getP_id() + " and price: " + oldPrice);
            Product existingProduct = this.productRepository.findProductByIdAndPrice(product.getP_id(), oldPrice);

            if (existingProduct != null) {
                System.out.println("Existing product found: " + existingProduct);

                // Step 3: Check if the product belongs to the logged-in user
                if (existingProduct.getUserId() == user.getU_id()) {
                    System.out.println("User is authorized to modify this product.");

                    // Step 4: Delete the old product row using the old price
                    System.out.println("Deleting existing product with old price: " + oldPrice);
                    this.productRepository.deleteProductByIdAndPrice(existingProduct.getP_id(), oldPrice);
                    System.out.println("Product deleted successfully.");

                    // Step 5: Insert the updated product row
                    System.out.println("Inserting updated product...");
                    Product newProduct = new Product(
                            existingProduct.getP_id(),  // Keep original ID
                            product.getP_name(),        // Updated name
                            product.getT_price(),       // Updated price
                            existingProduct.getP_url(), // Keep original URL
                            existingProduct.getUserId() // Keep original user ID
                    );
                    System.out.println("New product object created: " + newProduct);
                    this.productRepository.save(newProduct);
                    System.out.println("Updated product saved successfully.");

                    redirectAttributes.addFlashAttribute("message", new Message("Your product has been modified!", "alert-success"));
                    return "redirect:/user/product-list";
                } else {
                    System.out.println("Access denied: User ID mismatch. Product belongs to user ID: " + existingProduct.getUserId());
                    redirectAttributes.addFlashAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
                    return "redirect:/user/product-list";
                }
            } else {
                System.out.println("Error: Product not found.");
                redirectAttributes.addFlashAttribute("message", new Message("Product not found!", "alert-danger"));
                return "redirect:/user/product-list";
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while editing product: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
            return "redirect:/user/product-list";
        }
    }




    //below are for react

    @RequestMapping(value = "/edit-products/{id}/{price}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Product> getProductForEdit(@PathVariable Integer id, @PathVariable BigDecimal price, Principal principal) {
        // Fetch the product using the provided ID and price
        Product product = this.productRepository.findProductByIdAndPrice(id, price);

        // Check if the product is found
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if product not found
        }

        // Optionally, check the role or ownership of the product if needed
        // If needed, you can check if the principal matches the product's owner
        // For example:
        // if (!product.getUserId().equals(principal.getName())) {
        //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        // }

        // Return the product data
        return ResponseEntity.ok(product);
    }



//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    //public ResponseEntity<String> Edit_Products(@ModelAttribute Product product, Principal principal, HttpSession session) {
//    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//        User user=null;
//        try {
//            String email = principal.getName();
//            user = this.userRepository.getUserByUserName(email);
//
//            // Check if the product belongs to the user
//            //if (product.getUser(user).equals(user)) { // Assuming getUser() returns User object
//            if (product.getUser(user)==this.userRepository.getUserByUserName(principal.getName()))
//            { // Assuming getUser() returns User object
//                //product.setUser(user);
//                product.setUser_id(user.getU_id()); //  Store only the user's ID
//
//                this.productRepository.save(product);
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                return ResponseEntity.ok("Product updated successfully.");
//            } else {
//                session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//            }
//
//        } catch (Exception e) {
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            System.out.println("user: " + user);
//            System.out.println("product.getUser(): " + product.getUser(user));
//            System.out.println("product.getUserByName(): " + this.userRepository.getUserByUserName(principal.getName()));
//            e.printStackTrace();  // Log the exception details
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }


//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Find the existing product to check ownership
//            Optional<Product> existingProductOptional = this.productRepository.findById(product.getP_id());
//
//            if (existingProductOptional.isPresent()) {
//                Product existingProduct = existingProductOptional.get();
//
//                // Check if the product belongs to the logged-in user using user_id
//                if (existingProduct.getUserId() == user.getU_id()) {
//                    product.setUser_id(user.getU_id()); // Store only user ID (Cassandra way)
//                    this.productRepository.save(product); // Save updated product
//
//                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                    return ResponseEntity.ok("Product updated successfully.");
//                } else {
//                    session.setAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }

//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Find the existing product to check ownership
//            List<Product> existingProducts = this.productRepository.findProductById(product.getP_id());
//
//            if (!existingProducts.isEmpty()) {
//                Product existingProduct = existingProducts.get(0); // Get the first (and only) product from the list
//
//                // Check if the product belongs to the logged-in user using user_id
//                if (existingProduct.getUserId() == user.getU_id()) {
//                    product.setUser_id(user.getU_id()); // Store only user ID (Cassandra way)
//                    this.productRepository.save(product); // Save updated product
//
//                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                    return ResponseEntity.ok("Product updated successfully.");
//                } else {
//                    session.setAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }



//@RequestMapping("/edit-products")
//@PreAuthorize("hasAuthority('ROLE_USER')")
//public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//    try {
//        String email = principal.getName();
//        User user = this.userRepository.getUserByUserName(email);
//
//        // Find the existing product to check ownership
//        List<Product> existingProducts = this.productRepository.findProductById(product.getP_id());
//
//        if (!existingProducts.isEmpty()) {
//            Product existingProduct = existingProducts.get(0); // Get the first (and only) product from the list
//
//            // Check if the product belongs to the logged-in user using user_id
//            if (existingProduct.getUserId() == user.getU_id()) {
//                System.out.println("Updating product with ID: " + product.getP_id());
//
//                // Call update query instead of save
//                this.productRepository.updateProduct(product.getP_id(), product.getP_name(), product.getT_price());
//
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                return ResponseEntity.ok("Product updated successfully.");
//            } else {
//                session.setAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//            }
//        } else {
//            session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//    }
//}


//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Find the existing product using p_id and t_price
//            Product existingProduct = this.productRepository.findProductByIdAndPrice(product.getP_id(), product.getT_price());
//
//            if (existingProduct != null) {
//                // Check if the product belongs to the logged-in user
//                if (existingProduct.getUserId()==user.getU_id()) {
//                    System.out.println("Updating product with ID: " + product.getP_id());
//
//                    // Step 1: Delete the old product row
//                    this.productRepository.deleteProductByIdAndPrice(existingProduct.getP_id(), existingProduct.getT_price());
//
//                    // Step 2: Insert the updated product row
//                    this.productRepository.insertProduct(
//                            product.getP_id(), product.getP_name(), product.getT_price(),
//                            existingProduct.getP_url(), existingProduct.getUserId()
//                    );
//
//                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                    return ResponseEntity.ok("Product updated successfully.");
//                } else {
//                    session.setAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }



//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Find the existing product using p_id and t_price
//            Product existingProduct = this.productRepository.findProductByIdAndPrice(product.getP_id(), product.getT_price());
//
//            if (existingProduct != null) {
//                // Check if the product belongs to the logged-in user
//                if (existingProduct.getUserId() == user.getU_id()) {
//                    System.out.println("Updating product with ID: " + product.getP_id());
//
//                    // Step 1: Delete the old product row
//                    this.productRepository.deleteProductByIdAndPrice(existingProduct.getP_id(), existingProduct.getT_price());
//
//                    // Step 2: Create a new product object and save it
//                    Product newProduct = new Product(
//                            product.getP_id(), product.getP_name(), product.getT_price(),
//                            existingProduct.getP_url(), existingProduct.getUserId()
//                    );
//                    this.productRepository.save(newProduct);
//
//                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                    return ResponseEntity.ok("Product updated successfully.");
//                } else {
//                    session.setAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }


   // @RequestMapping("/edit-products")
//   @PostMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Find the existing product using p_id and t_price
//            Product existingProduct = this.productRepository.findProductByIdAndPrice(product.getP_id(), product.getT_price());
//
//            if (existingProduct != null) {
//                // Check if the product belongs to the logged-in user
//                if (existingProduct.getUserId() == user.getU_id()) {
//                    System.out.println("Updating product with ID: " + product.getP_id());
//
//                    // Step 1: Delete the old product row
//                    this.productRepository.deleteProductByIdAndPrice(existingProduct.getP_id(), existingProduct.getT_price());
//
//                    // Step 2: Insert the updated product row
//                    Product newProduct = new Product(
//                            product.getP_id(), product.getP_name(), product.getT_price(),
//                            existingProduct.getP_url(), existingProduct.getUserId()
//                    );
//                    this.productRepository.save(newProduct);
//
//                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                    return ResponseEntity.ok("Product updated successfully.");
//                } else {
//                    session.setAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//                }
//            } else {
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }

//    @PostMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//        try {
//            System.out.println("Received request to edit product: " + product);
//
//            // Step 1: Get logged-in user email
//            String email = principal.getName();
//            System.out.println("Fetching user details for email: " + email);
//            User user = this.userRepository.getUserByUserName(email);
//
//            if (user == null) {
//                System.out.println("Error: User not found for email: " + email);
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
//            }
//
//            System.out.println("Logged-in user ID: " + user.getU_id());
//
//            // Step 2: Find existing product using p_id and t_price
//            System.out.println("Searching for product with ID: " + product.getP_id() + " and price: " + product.getT_price());
//            Product existingProduct = this.productRepository.findProductByIdAndPrice(product.getP_id(), product.getT_price());
//
//            if (existingProduct != null) {
//                System.out.println("Existing product found: " + existingProduct);
//
//                // Step 3: Check if the product belongs to the logged-in user
//                if (existingProduct.getUserId() == user.getU_id()) {
//                    System.out.println("User is authorized to modify this product.");
//
//                    // Step 4: Delete the old product row
//                    System.out.println("Deleting existing product...");
//                    this.productRepository.deleteProductByIdAndPrice(existingProduct.getP_id(), existingProduct.getT_price());
//                    System.out.println("Product deleted successfully.");
//
//                    // Step 5: Insert the updated product row
//                    System.out.println("Inserting updated product...");
//                    Product newProduct = new Product(
//                            product.getP_id(), product.getP_name(), product.getT_price(),
//                            existingProduct.getP_url(), existingProduct.getUserId()
//                    );
//                    System.out.println("New product object created: " + newProduct);
//                    this.productRepository.save(newProduct);
//                    System.out.println("Updated product saved successfully.");
//
//                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                    return ResponseEntity.ok("Product updated successfully.");
//                } else {
//                    System.out.println("Access denied: User ID mismatch. Product belongs to user ID: " + existingProduct.getUserId());
//                    session.setAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//                }
//            } else {
//                System.out.println("Error: Product not found.");
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
//            }
//        } catch (Exception e) {
//            System.out.println("Exception occurred while editing product: " + e.getMessage());
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }




//    @PostMapping("/edit-products/{price}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
//        try {
//            System.out.println("Received request to edit product: " + product);
//
//            // Step 1: Get logged-in user email
//            String email = principal.getName();
//            System.out.println("Fetching user details for email: " + email);
//            User user = this.userRepository.getUserByUserName(email);
//
//            if (user == null) {
//                System.out.println("Error: User not found for email: " + email);
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
//            }
//
//            System.out.println("Logged-in user ID: " + user.getU_id());
//
//            // Step 2: Find existing product using p_id and t_price
//            System.out.println("Searching for product with ID: " + product.getP_id() + " and price: " + product.getT_price());
//            Product existingProduct = this.productRepository.findProductByIdAndPrice(product.getP_id(), product.getT_price());
//
//            if (existingProduct != null) {
//                System.out.println("Existing product found: " + existingProduct);
//
//                // Step 3: Check if the product belongs to the logged-in user
//                if (existingProduct.getUserId() == user.getU_id()) {
//                    System.out.println("User is authorized to modify this product.");
//
//                    // Step 4: Delete the old product row
//                    System.out.println("Deleting existing product...");
//                    this.productRepository.deleteProductByIdAndPrice(existingProduct.getP_id(), price);
//                    System.out.println("Product deleted successfully.");
//
//                    // Step 5: Insert the updated product row
//                    System.out.println("Inserting updated product...");
//                    Product newProduct = new Product(
//                            product.getP_id(), product.getP_name(), product.getT_price(),
//                            existingProduct.getP_url(), existingProduct.getUserId()
//                    );
//                    System.out.println("New product object created: " + newProduct);
//                    this.productRepository.save(newProduct);
//                    System.out.println("Updated product saved successfully.");
//
//                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                    return ResponseEntity.ok("Product updated successfully.");
//                } else {
//                    System.out.println("Access denied: User ID mismatch. Product belongs to user ID: " + existingProduct.getUserId());
//                    session.setAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//                }
//            } else {
//                System.out.println("Error: Product not found.");
//                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
//            }
//        } catch (Exception e) {
//            System.out.println("Exception occurred while editing product: " + e.getMessage());
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }

    @PostMapping("/edit-products/{price}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> Edit_Products(
            @PathVariable("price") BigDecimal oldPrice,  // Extract old price from the URL
            @RequestBody Product product,
            Principal principal,
            HttpSession session) {
        try {
            System.out.println("Received request to edit product: " + product);

            // Step 1: Get logged-in user email
            String email = principal.getName();
            System.out.println("Fetching user details for email: " + email);
            User user = this.userRepository.getUserByUserName(email);

            if (user == null) {
                System.out.println("Error: User not found for email: " + email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
            }

            System.out.println("Logged-in user ID: " + user.getU_id());

            // Step 2: Find existing product using p_id and oldPrice
            System.out.println("Searching for product with ID: " + product.getP_id() + " and price: " + oldPrice);
            Product existingProduct = this.productRepository.findProductByIdAndPrice(product.getP_id(), oldPrice);

            if (existingProduct != null) {
                System.out.println("Existing product found: " + existingProduct);

                // Step 3: Check if the product belongs to the logged-in user
                if (existingProduct.getUserId() == user.getU_id()) {
                    System.out.println("User is authorized to modify this product.");

                    // Step 4: Delete the old product row using the old price
                    System.out.println("Deleting existing product with old price: " + oldPrice);
                    this.productRepository.deleteProductByIdAndPrice(existingProduct.getP_id(), oldPrice);
                    System.out.println("Product deleted successfully.");

                    // Step 5: Insert the updated product row
                    System.out.println("Inserting updated product...");
                    Product newProduct = new Product(
                            existingProduct.getP_id(),  // Keep original ID
                            product.getP_name(),        // Updated name
                            product.getT_price(),       // Updated price
                            existingProduct.getP_url(), // Keep original URL
                            existingProduct.getUserId() // Keep original user ID
                    );
                    System.out.println("New product object created: " + newProduct);
                    this.productRepository.save(newProduct);
                    System.out.println("Updated product saved successfully.");

                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
                    return ResponseEntity.ok("Product updated successfully.");
                } else {
                    System.out.println("Access denied: User ID mismatch. Product belongs to user ID: " + existingProduct.getUserId());
                    session.setAttribute("message", new Message("You are not the owner of this product!", "alert-danger"));
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
                }
            } else {
                System.out.println("Error: Product not found.");
                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while editing product: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }



}
