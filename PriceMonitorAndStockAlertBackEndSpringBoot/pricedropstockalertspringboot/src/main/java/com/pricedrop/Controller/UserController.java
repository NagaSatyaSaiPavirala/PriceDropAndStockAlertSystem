package com.pricedrop.Controller;

import com.pricedrop.admin.dao.ApiRepository;
import com.pricedrop.admin.entities.Productapi;
import com.pricedrop.dao.ProductRepository;
import com.pricedrop.dao.UserRepository;
import com.pricedrop.entities.Product;
import com.pricedrop.entities.User;
import com.pricedrop.helper.Message;
import com.pricedrop.services.ProductScraperService;
import com.pricedrop.services.ProductService;
import com.pricedrop.services.UrlCoding;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

    @GetMapping("/api/user") // New endpoint for fetching user data in JSON format
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<User> getUser(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/dashboard")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String Dashboard(Principal principal,Model model){
        User user = this.userRepository.getUserByUserName(principal.getName());
        model.addAttribute("user",user);

        return "dashboard1";
    }
    @RequestMapping("/add-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String product(Principal principal,Model model){
        User user = this.userRepository.getUserByUserName(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("product",new Product());
        return "add_product1";
    }

//    @RequestMapping("/added-product")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String AddedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session, Model model) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//            if (result.hasErrors()) {
//                model.addAttribute("product", product);
//                model.addAttribute("user", user);
//                return "add_product1";
//            } else {
//                // Check if the product already exists for the user
//                if (isProductExistsForUser(product, user)) {
//                    session.setAttribute("message", new Message("You have already added this product.", "alert-danger"));
//                } else if (ProductService.getCurrentPrice(UrlCoding.extractProductName(product.getP_url())) != null && product.getP_url().contains("https://www.example.com/product/")) {
//                    if(ProductService.getCurrentPrice(UrlCoding.extractProductName(product.getP_url())) <= product.getT_price() ){
//                        session.setAttribute("message", new Message("Please enter correct threshold price!!", "alert-danger"));
//                    }
//                    else {
//                        product.setT_price(Double.parseDouble(String.valueOf(product.getT_price())));
//                        user.getProduct().add(product);
//                        product.setUser(user);
//                        this.userRepository.save(user);
//                        session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//                    }
//                } else {
//                    session.setAttribute("message", new Message("Invalid URL. Please try again.", "alert-danger"));
//                }
//            }
//        } catch (Exception e) {
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//        }
//        return "redirect:/user/add-product";
//    }




//@RequestMapping("/added-product")
//@PreAuthorize("hasAuthority('ROLE_USER')")
//public String AddedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session, Model model) {
//    try {
//        String email = principal.getName();
//        User user = this.userRepository.getUserByUserName(email);
//
//        // Log the incoming product details
//        System.out.println("Received Product URL: " + product.getP_url());
//        System.out.println("Received Threshold Price: " + product.getT_price());
//
//        if (result.hasErrors()) {
//            model.addAttribute("product", product);
//            model.addAttribute("user", user);
//            return "add_product1";
//        } else {
//            // Check if the product already exists for the user
//            if (isProductExistsForUser(product, user)) {
//                session.setAttribute("message", new Message("You have already added this product.", "alert-danger"));
//            } else if (ProductService.getCurrentPrice(UrlCoding.extractProductName(product.getP_url())) != null && product.getP_url().contains("https://www.example.com/product/")) {
//                // Log the current price fetched from ProductService
//                System.out.println("Current Price from ProductService: " + ProductService.getCurrentPrice(UrlCoding.extractProductName(product.getP_url())));
//
//                if (ProductService.getCurrentPrice(UrlCoding.extractProductName(product.getP_url())) <= product.getT_price()) {
//                    session.setAttribute("message", new Message("Please enter correct threshold price!!", "alert-danger"));
//                } else {
//                    product.setT_price(Double.parseDouble(String.valueOf(product.getT_price())));
//                    user.getProduct().add(product);
//                    product.setUser(user);
//                    this.userRepository.save(user);
//                    session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//                }
//            } else {
//                session.setAttribute("message", new Message("Invalid URL. Please try again.", "alert-danger"));
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace(); // Log the exception for debugging purposes
//        session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//    }
//    return "redirect:/user/add-product";
//}


/*
    @RequestMapping("/added-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String AddedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session, Model model) {
        try {
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);

            // Log the incoming product details
            System.out.println("Received Product URL: " + product.getP_url());
            System.out.println("Received Threshold Price: " + product.getT_price());

            // Scrape product details from the Amazon URL
            String productUrl = product.getP_url();
            if (productUrl.contains("https://www.amazon.in/")||productUrl.contains("https://amzn.in/")||productUrl.contains("localhost")) {
                try {
                    Document doc = Jsoup.connect(productUrl)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                            .timeout(10000)
                            .get();

                    // Extract Product Name
                    Element titleElement = doc.selectFirst("#productTitle");
                    if (titleElement != null) {
                        String productTitle = titleElement.text().trim();
                        product.setP_name(productTitle); // Update the product name
                        System.out.println("Scraped Product Title: " + productTitle);
                    } else {
                        session.setAttribute("message", new Message("Product title not found on Amazon.", "alert-danger"));
                        return "redirect:/user/add-product";
                    }

                    // Extract Product Price
                    Element wholePriceElement = doc.selectFirst(".a-price.aok-align-center.reinventPricePriceToPayMargin.priceToPay span[aria-hidden='true'] .a-price-whole");
                    Element fractionPriceElement = doc.selectFirst(".a-price-fraction");

                    double currentPrice = 0.0;
                    if (wholePriceElement != null) {
                        String wholePrice = wholePriceElement.text().replace(",", ""); // Remove commas
                        if (fractionPriceElement != null) {
                            String fractionPrice = fractionPriceElement.text().replace(",", ""); // Remove commas
                            currentPrice = Double.parseDouble(wholePrice + "." + fractionPrice);
                        } else {
                            currentPrice = Double.parseDouble(wholePrice);
                        }
                        System.out.println("Scraped Product Price: " + currentPrice);
                    } else {
                        session.setAttribute("message", new Message("Product price not found on Amazon.", "alert-danger"));
                        return "redirect:/user/add-product";
                    }

                    // Check threshold price logic
                    if (currentPrice <= product.getT_price()) {
                        session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
                        return "redirect:/user/add-product";
                    }

                    // Associate the product with the user and save it
                    product.setUser(user);
                    productRepository.save(product); // Save the product to the repository
                    session.setAttribute("message", new Message("Your product has been added!", "alert-success"));

                } catch (Exception e) {
                    e.printStackTrace(); // Log the exception for debugging purposes
                    session.setAttribute("message", new Message("Failed to scrape product data. Please try again.", "alert-danger"));
                }

            } else {
                session.setAttribute("message", new Message("Invalid URL. Please try again.", "alert-danger"));
            }

            // Check for validation errors after scraping
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
                model.addAttribute("product", product);
                model.addAttribute("user", user);
                return "add_product1";
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
        }
        return "redirect:/user/add-product";
    }


 */

    /*
@RequestMapping("/added-product")
@PreAuthorize("hasAuthority('ROLE_USER')")
public String addedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session, Model model) {
    try {
        String email = principal.getName();
        User user = this.userRepository.getUserByUserName(email);

        // Log the incoming product details
        System.out.println("Received Product URL: " + product.getP_url());
        System.out.println("Received Threshold Price: " + product.getT_price());

        // Check if the product exists in the ProductAPI repository
        Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());





        // Scrape product details from the Amazon URL
        String productUrl = product.getP_url();
        if (productUrl.contains("https://www.amazon.in/")||productUrl.contains("https://amzn.in/")||productUrl.contains("localhost")) {
            try {
                Document doc = Jsoup.connect(productUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                        .timeout(10000)
                        .get();

                // Extract Product Name
                Element titleElement = doc.selectFirst("#productTitle");
                if (titleElement != null) {
                    String productTitle = titleElement.text().trim();
                    product.setP_name(productTitle); // Update the product name
                    System.out.println("Scraped Product Title: " + productTitle);
                } else {
                    session.setAttribute("message", new Message("Product title not found on Amazon.", "alert-danger"));
                    return "redirect:/user/add-product";
                }

                // Extract Product Price
                Element wholePriceElement = doc.selectFirst(".a-price.aok-align-center.reinventPricePriceToPayMargin.priceToPay span[aria-hidden='true'] .a-price-whole");
                Element fractionPriceElement = doc.selectFirst(".a-price-fraction");

                double currentPrice = 0.0;
                if (wholePriceElement != null) {
                    String wholePrice = wholePriceElement.text().replace(",", ""); // Remove commas
                    if (fractionPriceElement != null) {
                        String fractionPrice = fractionPriceElement.text().replace(",", ""); // Remove commas
                        currentPrice = Double.parseDouble(wholePrice + "." + fractionPrice);
                    } else {
                        currentPrice = Double.parseDouble(wholePrice);
                    }
                    System.out.println("Scraped Product Price: " + currentPrice);
                } else {
                    session.setAttribute("message", new Message("Product price not found on Amazon.", "alert-danger"));
                    return "redirect:/user/add-product";
                }

                // Check threshold price logic
                if (currentPrice <= product.getT_price()) {
                    session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
                    return "redirect:/user/add-product";
                }



        String productImageUrl = null;
        if (existingProductApi != null) {
            // Product exists in productapi table, use its image URL
            productImageUrl = existingProductApi.getProduct_image();
            System.out.println("Product already exists in ProductAPI. Using existing image URL: " + productImageUrl);
        } else {
            // Product does not exist in productapi, scrape the image URL
            productImageUrl = productService.getImageUrl(product.getP_url());
            System.out.println("Product not found in ProductAPI. Scraped image URL: " + productImageUrl);

            if (productImageUrl != null) {
                // Save the scraped image URL to productapi for future use
                Productapi newProductApi = new Productapi();
                newProductApi.setProduct_name(product.getP_name()); // Assuming you've scraped the product name
                newProductApi.setProduct_url(product.getP_url());
                newProductApi.setProduct_image(productImageUrl);
                apiRepository.save(newProductApi); // Save to productapi table
                System.out.println("Scraped product saved to ProductAPI table.");
            } else {
                session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
                return "redirect:/user/add-product";
            }
        }

        // Now use `productImageUrl` as needed
        // If your `product` entity doesn't have an image field, you can just log or display it in the session
        session.setAttribute("productImage", productImageUrl);

        // Check for validation errors after scraping
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
            model.addAttribute("product", product);
            model.addAttribute("user", user);
            return "add_product1";
        }

        // Further logic to save the product entity and other operations...
        product.setUser(user); // Associate product with the user
        productRepository.save(product); // Save the product to the repository
        session.setAttribute("message", new Message("Your product has been added!", "alert-success"));

    } catch (Exception e) {
        e.printStackTrace(); // Log the exception for debugging purposes
        session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
    }
    return "redirect:/user/add-product";
}


/*
    @RequestMapping("/added-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String addedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session, Model model) {
        try {
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);

            // Log the incoming product details
            System.out.println("Received Product URL: " + product.getP_url());
            System.out.println("Received Threshold Price: " + product.getT_price());

            // Check if the product exists in the ProductAPI repository
            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());

            String productImageUrl = null;
            Double productPrice = null; // To hold the extracted product price

            if (existingProductApi != null) {
                // Product exists in productapi table, use its image URL and price
                productImageUrl = existingProductApi.getProduct_image();
                productPrice = existingProductApi.getProduct_price(); // Get existing price from ProductAPI
                System.out.println("Product already exists in ProductAPI. Using existing image URL: " + productImageUrl);
            } else {
                // Product does not exist in productapi, scrape the image URL and price
                productImageUrl = productService.getImageUrl(product.getP_url());
                productPrice = productService.scrapeProductPrice(product.getP_url()); // Assume you have a method to extract price from URL

                System.out.println("Product not found in ProductAPI. Scraped image URL: " + productImageUrl);
                System.out.println("Extracted Product Price: " + productPrice);

                if (productImageUrl != null && productPrice != null) {
                    // Save the scraped image URL and price to productapi for future use
                    Productapi newProductApi = new Productapi();
                    newProductApi.setProduct_name(product.getP_name()); // Set the product name
                    newProductApi.setProduct_url(product.getP_url());   // Set the product URL
                    newProductApi.setProduct_image(productImageUrl);     // Set the scraped image URL
                    newProductApi.setProduct_price(productPrice);         // Set the extracted price

                    apiRepository.save(newProductApi); // Save to productapi table
                    System.out.println("Scraped product saved to ProductAPI table.");
                } else {
                    session.setAttribute("message", new Message("Failed to retrieve product image or price. Please check the product URL.", "alert-danger"));
                    return "redirect:/user/add-product";
                }
            }

            // Now use `productImageUrl` and `productPrice` as needed
            session.setAttribute("productImage", productImageUrl);

            // Check for validation errors after scraping
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
                model.addAttribute("product", product);
                model.addAttribute("user", user);
                return "add_product1";
            }

            // Further logic to save the product entity and other operations...
            product.setUser(user); // Associate product with the user
            product.setP_price(productPrice); // Set the extracted price to the product entity if needed
            productRepository.save(product); // Save the product to the repository
            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
        }
        return "redirect:/user/add-product";
    }


 */
    /*
    @RequestMapping("/added-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String addedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session, Model model) {
        try {
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);

            // Log the incoming product details
            System.out.println("Received Product URL: " + product.getP_url());
            System.out.println("Received Threshold Price: " + product.getT_price());

            // Check if the product exists in the ProductAPI repository
            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());

            // Scrape product details from the Amazon URL
            String productUrl = product.getP_url();
            String productTitle = productScraperService.scrapeProductTitle(productUrl);
            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);

            // Check for scraped title and price
            if (productTitle == null || currentPrice == null) {
                System.out.println(productTitle);
                System.out.println(currentPrice);
                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
                return "redirect:/user/add-product";
            }

            // Set the product name from scraped title
            product.setP_name(productTitle);
            System.out.println("Scraped Product Title: " + productTitle);
            System.out.println("Scraped Product Price: " + currentPrice);


            System.out.println("Current Price: " + currentPrice);
            System.out.println("Threshold Price: " + product.getT_price());

            // Check threshold price logic
            if (currentPrice <= product.getT_price()) {
                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
                return "redirect:/user/add-product";
            }

            String productImageUrl;
            if (existingProductApi != null) {
                // Product exists in productapi table, use its image URL
                productImageUrl = existingProductApi.getProduct_image();
                System.out.println("Product already exists in ProductAPI. Using existing image URL: " + productImageUrl);
            } else {
                // Product does not exist in productapi, scrape the image URL
                productImageUrl = productScraperService.scrapeProductImage(productUrl);
                System.out.println("Product not found in ProductAPI. Scraped image URL: " + productImageUrl);

                if (productImageUrl != null) {
                    // Save the scraped image URL to productapi for future use
                    Productapi newProductApi = new Productapi();
                    newProductApi.setProduct_name(product.getP_name());
                    newProductApi.setProduct_url(product.getP_url());
                    newProductApi.setProduct_image(productImageUrl);
                    newProductApi.setProduct_price(String.valueOf(currentPrice));

                    apiRepository.save(newProductApi);
                    System.out.println("Scraped product saved to ProductAPI table.");
                } else {
                    session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
                    return "redirect:/user/add-product";
                }
            }

            // Set the product image URL in the session
            session.setAttribute("productImage", productImageUrl);

            // Check for validation errors after scraping
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
                model.addAttribute("product", product);
                model.addAttribute("user", user);
                return "add_product1";
            }

            // Save the product entity and other operations...
            product.setUser(user); // Associate product with the user
            productRepository.save(product); // Save the product to the repository
            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
        }
        return "redirect:/user/add-product";
    }


     */
    @RequestMapping("/added-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String addedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session, Model model) {
        try {
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);

            // Log the incoming product details
           // System.out.println("Received Product URL: " + product.getP_url());
         //   System.out.println("Received Threshold Price: " + product.getT_price());

            // Check if the product exists in the ProductAPI repository
            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());

            // Scrape product details from the Amazon URL
            String productUrl = product.getP_url();

            String productTitle = productScraperService.scrapeProductTitle(productUrl);
            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);

            // Check for scraped title and price
            //if (productTitle == null || currentPrice == null)
            if (productTitle == null)
            {
             //   System.out.println("Scraped Product Title: " + productTitle);
             //   System.out.println("Scraped Product Price: " + currentPrice);
                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
                return "redirect:/user/add-product";
            }

            // Set the product name from scraped title
            product.setP_name(productTitle);
         //   System.out.println("Scraped Product Title: " + productTitle);
          //  System.out.println("Scraped Product Price: " + currentPrice);

            // Set the current price in the product object
            product.setT_price(product.getT_price()); // Assuming product_price is a String

          //  System.out.println("Current Price: " + currentPrice);
           // System.out.println("Threshold Price: " + product.getT_price());

            // Check threshold price logic
            if (currentPrice!=null && currentPrice <= product.getT_price()) {
                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
                return "redirect:/user/add-product";
            }

            String productImageUrl;
            if (existingProductApi != null) {
                // Product exists in productapi table, use its image URL
                productImageUrl = existingProductApi.getProduct_image();
                System.out.println("Product already exists in ProductAPI. Using existing image URL: " + productImageUrl);
                // Update the product price in productapi if currentPrice is available
                if (currentPrice != null) {
                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
                    apiRepository.save(existingProductApi);
                    System.out.println("Product price updated in ProductAPI with currentPrice: " + currentPrice);
                }
            } else {
                // Product does not exist in productapi, scrape the image URL
                productImageUrl = productScraperService.scrapeProductImage(productUrl);
             //   System.out.println("Product not found in ProductAPI. Scraped image URL: " + productImageUrl);

                if (productImageUrl != null) {
                    // Save the scraped image URL to productapi for future use
                    Productapi newProductApi = new Productapi();
                    newProductApi.setProduct_name(product.getP_name());
                   // newProductApi.setProduct_url(product.getP_url());
                    newProductApi.setProduct_url(UrlCoding.extractProductName(product.getP_url()));
                    newProductApi.setProduct_image(productImageUrl);
                    //  newProductApi.setProduct_price(String.valueOf(currentPrice));
                    if(currentPrice!=null)
                    newProductApi.setProduct_price(String.valueOf(currentPrice));
                    else
                        newProductApi.setProduct_price("Currently Unavailable");


                    apiRepository.save(newProductApi);
                    System.out.println("Scraped product saved to ProductAPI table.");
                } else {
                    session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
                    return "redirect:/user/add-product";
                }
            }

            // Set the product image URL in the session
            session.setAttribute("productImage", productImageUrl);

            // Check for validation errors after scraping and setting the product fields
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
                model.addAttribute("product", product);
                model.addAttribute("user", user);
                return "add_product1";
            }

            // Save the product entity
            product.setUser(user); // Associate product with the user
            productRepository.save(product); // Save the product to the repository
            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
        }
        return "redirect:/user/add-product";
    }


//    @RequestMapping("/added-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public void addedProduct(@Valid @ModelAttribute Product product, BindingResult result, Principal principal, HttpSession session) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Scrape product details from the Amazon URL
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            // Check for scraped title
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return; // Exit without returning anything
//            }
//
//            // Set the product name in the product object
//            product.setP_name(productTitle);
//            product.setT_price(product.getT_price()); // Assuming product_price is a String
//
//            // Check threshold price logic
//            if (currentPrice != null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!", "alert-danger"));
//                return; // Exit without returning anything
//            }
//
//            String productImageUrl;
//
//            // Check if the product already exists
//            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
//            if (existingProductApi != null) {
//                // Product exists, update its price if currentPrice is available
//                productImageUrl = existingProductApi.getProduct_image();
//                if (currentPrice != null) {
//                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
//                    apiRepository.save(existingProductApi);
//                    session.setAttribute("message", new Message("Product updated successfully!", "alert-success"));
//                } else {
//                    session.setAttribute("message", new Message("Product already exists, but no price update occurred.", "alert-info"));
//                }
//            } else {
//                // Product does not exist, scrape the image URL
//                productImageUrl = productScraperService.scrapeProductImage(productUrl);
//                if (productImageUrl != null) {
//                    // Save the scraped product details to productapi
//                    Productapi newProductApi = new Productapi();
//                    newProductApi.setProduct_name(product.getP_name());
//                    newProductApi.setProduct_url(UrlCoding.extractProductName(product.getP_url()));
//                    newProductApi.setProduct_image(productImageUrl);
//                    newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//
//                    apiRepository.save(newProductApi);
//                    session.setAttribute("message", new Message("Product added successfully!", "alert-success"));
//                } else {
//                    session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                    return; // Exit without returning anything
//                }
//            }
//        } catch (Exception e) {
//            // Handle any unexpected errors
//            session.setAttribute("message", new Message("An error occurred while adding the product: " + e.getMessage(), "alert-danger"));
//        }
//
//        // No return statement, just handle the session messages
//    }


    @PostMapping("/added-products")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> addedProducts(@Valid @RequestBody Product product,
                                               BindingResult result,
                                               Principal principal,
                                               HttpSession session,Model model) {
//        try {
//            // Retrieve user from the principal
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Check if the product exists in the ProductAPI repository
//            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
//
//            // Scrape product details from the Amazon URL
//            String productUrl = product.getP_url();
//            String productTitle = productScraperService.scrapeProductTitle(productUrl);
//            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);
//
//            // Validate scraped details
//            if (productTitle == null) {
//                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details.");
//            }
//
//            // Set product details
//            product.setP_name(productTitle);
//            product.setT_price(product.getT_price()); // Assuming this is the threshold price
//
//            // Check threshold price logic
//            if (currentPrice!=null && currentPrice <= product.getT_price()) {
//                session.setAttribute("message", new Message("Please enter a correct threshold price!", "alert-danger"));
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect threshold price.");
//            }
//
//            String productImageUrl;
//
//            // Handle existing product case
//            if (existingProductApi != null) {
//                productImageUrl = existingProductApi.getProduct_image();
//                if (currentPrice != null) {
//                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
//                    apiRepository.save(existingProductApi);
//                }
//            } else {
//                // Scrape the image URL if the product is new
//                productImageUrl = productScraperService.scrapeProductImage(productUrl);
//                if (productImageUrl == null) {
//                    session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image.");
//                }
//
//                // Save the new product to the ProductAPI repository
//                Productapi newProductApi = new Productapi();
//                newProductApi.setProduct_name(product.getP_name());
//                newProductApi.setProduct_url(product.getP_url()); // Store the original URL
//                newProductApi.setProduct_image(productImageUrl);
//                newProductApi.setProduct_price(currentPrice != null ? String.valueOf(currentPrice) : "Currently Unavailable");
//                apiRepository.save(newProductApi);
//            }
//
//            // Set product details and associate it with the user
//            product.setUser(user);
//            productRepository.save(product);
//
//            // Success message
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//            return ResponseEntity.ok("Product added successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the product.");
//        }

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
//            if (productTitle == null)
//            {
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
//                //   System.out.println("Product not found in ProductAPI. Scraped image URL: " + productImageUrl);
//
//                if (productImageUrl != null) {
//                    // Save the scraped image URL to productapi for future use
//                    Productapi newProductApi = new Productapi();
//                    newProductApi.setProduct_name(product.getP_name());
//                    // newProductApi.setProduct_url(product.getP_url());
//                    newProductApi.setProduct_url(UrlCoding.extractProductName(product.getP_url()));
//                    newProductApi.setProduct_image(productImageUrl);
//                    //  newProductApi.setProduct_price(String.valueOf(currentPrice));
//                    if(currentPrice!=null)
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
//            product.setUser(user); // Associate product with the user
//            productRepository.save(product); // Save the product to the repository
//            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the exception for debugging purposes
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//        }
//        return "redirect:/user/add-product";

        try {
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);

            // Log the incoming product details
            // System.out.println("Received Product URL: " + product.getP_url());
            // System.out.println("Received Threshold Price: " + product.getT_price());

            // Check if the product exists in the ProductAPI repository
            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());

            // Scrape product details from the Amazon URL
            String productUrl = product.getP_url();
            String productTitle = productScraperService.scrapeProductTitle(productUrl);
            Double currentPrice = productScraperService.scrapeProductPrice(productUrl);

            // Check for scraped title
            if (productTitle == null) {
                session.setAttribute("message", new Message("Failed to scrape product details. Please check the product URL.", "alert-danger"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to scrape product details. Please check the product URL.");
            }

            // Set the product name from scraped title
            product.setP_name(productTitle);

            // Set the current price in the product object
            product.setT_price(product.getT_price()); // Assuming product_price is a String

            // Check threshold price logic
            if (currentPrice != null && currentPrice <= product.getT_price()) {
                session.setAttribute("message", new Message("Please enter a correct threshold price!!", "alert-danger"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a correct threshold price!!");
            }

            String productImageUrl;
            if (existingProductApi != null) {
                // Product exists in productapi table, use its image URL
                productImageUrl = existingProductApi.getProduct_image();
                System.out.println("Product already exists in ProductAPI. Using existing image URL: " + productImageUrl);

                // Update the product price in productapi if currentPrice is available
                if (currentPrice != null) {
                    existingProductApi.setProduct_price(String.valueOf(currentPrice));
                    apiRepository.save(existingProductApi);
                    System.out.println("Product price updated in ProductAPI with currentPrice: " + currentPrice);
                }
            } else {
                // Product does not exist in productapi, scrape the image URL
                productImageUrl = productScraperService.scrapeProductImage(productUrl);

                if (productImageUrl != null) {
                    // Save the scraped image URL to productapi for future use
                    Productapi newProductApi = new Productapi();
                    newProductApi.setProduct_name(product.getP_name());
                    newProductApi.setProduct_url(UrlCoding.extractProductName(product.getP_url()));
                    newProductApi.setProduct_image(productImageUrl);
                    if (currentPrice != null)
                        newProductApi.setProduct_price(String.valueOf(currentPrice));
                    else
                        newProductApi.setProduct_price("Currently Unavailable");

                    apiRepository.save(newProductApi);
                    System.out.println("Scraped product saved to ProductAPI table.");
                } else {
                    session.setAttribute("message", new Message("Failed to retrieve product image. Please check the product URL.", "alert-danger"));
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve product image. Please check the product URL.");
                }
            }

            // Set the product image URL in the session
            session.setAttribute("productImage", productImageUrl);

            // Check for validation errors after scraping and setting the product fields
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
                model.addAttribute("product", product);
                model.addAttribute("user", user);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors occurred. Please check the provided information.");
            }

            // Save the product entity
            product.setUser(user); // Associate product with the user
            productRepository.save(product); // Save the product to the repository
            session.setAttribute("message", new Message("Your product has been added!", "alert-success"));
            return ResponseEntity.status(HttpStatus.CREATED).body("Your product has been added!");

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! Please try again.");
        }


    }





    private boolean isProductExistsForUser(Product product, User user) {
        List<Product> userProducts = user.getProduct();
        for (Product userProduct : userProducts) {
            if (userProduct.getP_name().equals(product.getP_name()) && userProduct.getP_url().equals(product.getP_url())) {
                return true; // Product already exists for the user
            }
        }
        return false; // Product does not exist for the user
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping("/product-list")
    public String List(Model model, Principal principal){
        try{
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);
            List<Product> products = user.getProduct();
            model.addAttribute("user",user);
            model.addAttribute("list",products);
            model.addAttribute("productService", new ProductService());
            model.addAttribute("UrlCoding",new UrlCoding());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "product_list1";
    }

//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @RequestMapping("/products-list")
//    public List<Product> Lists(Model model, Principal principal) {
//        List<Product> products = null;
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//            products = user.getProduct();
//            model.addAttribute("user", user);
//            model.addAttribute("list", products);
//            model.addAttribute("productService", new ProductService());
//            model.addAttribute("UrlCoding", new UrlCoding());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return products;
//    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/products-list")
    public ResponseEntity<List<Product>> listProducts(Principal principal) {
        List<Product> products = null;
        try {
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);
            products = user.getProduct();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 in case of error
        }
        return ResponseEntity.ok(products); // Return the list of products in the response body
    }


    @RequestMapping("/deleteproduct/{productID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String DeleteProduct(@PathVariable("productID") int productID,Principal principal,HttpSession session){
        try {
            Optional<Product> product = this.productRepository.findById(productID);
            if(product.get().getUser() == this.userRepository.getUserByUserName(principal.getName())){
                Product pro = product.get();
                User user = pro.getUser();
                user.getProduct().remove(pro);
                this.userRepository.save(user); // Update the user entity
                this.productRepository.deleteById(productID); // Delete the product
            }else {
                session.setAttribute("message", new Message("You cannot access other's product!", "alert-danger"));
                System.out.println("you are accessing other contact");
                return "redirect:/user/dashboard";
            }


        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }

        return "redirect:/user/product-list";
    }
//    @RequestMapping("/deleteproducts/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public List<Product> DeleteProducts(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        List<Product> updatedProductList = null;
//
//        try {
//            Optional<Product> product = this.productRepository.findById(productID);
//            if (product.isPresent() && product.get().getUser().equals(this.userRepository.getUserByUserName(principal.getName()))) {
//                Product pro = product.get();
//                User user = pro.getUser();
//                user.getProduct().remove(pro); // Remove product from user's list
//                this.userRepository.save(user); // Update user in the repository
//                this.productRepository.deleteById(productID); // Delete product from the repository
//
//                updatedProductList = user.getProduct(); // Get the updated product list
//            } else {
//                session.setAttribute("message", new Message("You cannot access other's product!", "alert-danger"));
//                System.out.println("You are accessing another user's product.");
//            }
//
//        } catch (Exception e) {
//            // Log the exception
//            e.printStackTrace();
//        }
//
//        return updatedProductList; // Return the updated product list
//    }

//    @RequestMapping("/deleteproducts/{productID}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public List<Product> DeleteProducts(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
//        List<Product> updatedProductList = new ArrayList<>(); // Initialize with an empty list
//
//        try {
//            // Find the product by ID
//            Optional<Product> product = this.productRepository.findById(productID);
//
//            // Check if the product exists and belongs to the logged-in user
//            if (product.isPresent() && product.get().getUser().equals(this.userRepository.getUserByUserName(principal.getName()))) {
//                Product pro = product.get();
//                User user = pro.getUser();
//
//                // Remove the product from the user's product list
//                user.getProduct().remove(pro);
//
//                // Save the updated user
//                this.userRepository.save(user);
//
//                // Delete the product from the repository
//                this.productRepository.deleteById(productID);
//
//                // Fetch the updated product list for the user
//                updatedProductList = user.getProduct();
//            } else {
//                // Handle unauthorized access to someone else's product
//                session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
//                System.out.println("Unauthorized product access attempt.");
//            }
//
//        } catch (Exception e) {
//            // Log the exception
//            e.printStackTrace();
//        }
//
//        // Return the updated product list, or an empty list in case of failure
//        return updatedProductList;
//    }
//


    @RequestMapping("/deleteproducts/{productID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Product>> DeleteProducts(@PathVariable("productID") int productID, Principal principal, HttpSession session) {
        List<Product> updatedProductList = new ArrayList<>(); // Initialize with an empty list

        try {
            // Find the product by ID
            Optional<Product> product = this.productRepository.findById(productID);

            if (product.isPresent()) {
                Product pro = product.get();
                User currentUser = this.userRepository.getUserByUserName(principal.getName());

                // Check if the product belongs to the logged-in user
                if (pro.getUser().equals(currentUser)) {
                    // Remove the product from the user's product list
                    currentUser.getProduct().remove(pro);

                    // Save the updated user entity
                    this.userRepository.save(currentUser);

                    // Delete the product from the repository
                    this.productRepository.deleteById(productID);

                    // Fetch the updated product list for the user
                    updatedProductList = currentUser.getProduct();
                    return ResponseEntity.ok(updatedProductList); // Return 200 OK with the updated list
                } else {
                    session.setAttribute("message", new Message("You cannot access another user's product!", "alert-danger"));
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(updatedProductList); // Return 403 Forbidden
                }
            } else {
                // If product is not found
                session.setAttribute("message", new Message("Product not found!", "alert-danger"));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedProductList); // Return 404 Not Found
            }

        } catch (Exception e) {
            // Log the exception and return 500 Internal Server Error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedProductList); // Return 500 with an empty list
        }
    }

    @RequestMapping("/editproduct/{productID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String EditProduct(@PathVariable("productID") int productID,Model model,Principal principal,HttpSession session){
        try{
            Optional<Product> product = this.productRepository.findById(productID);
            if(product.get().getUser() == this.userRepository.getUserByUserName(principal.getName())){
                //username
                String email = principal.getName();
                User user = this.userRepository.getUserByUserName(email);
                model.addAttribute("user",user);

                //edit

                Product pro = product.get();
//            System.out.println(pro.getP_name()+" "+pro.getP_url()+" "+pro.getT_price());
                model.addAttribute("product",pro);
                return "edit";
            }else {
                session.setAttribute("message", new Message("You are the owner of that product!", "alert-danger"));
                System.out.println("you are accessing other contact");
                return "redirect:/user/dashboard";
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return "edit";
    }

    @RequestMapping("/edit-product")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String Edit_Product(@ModelAttribute Product product,Principal principal,HttpSession session){
        try {
            String email = principal.getName();
            User user = this.userRepository.getUserByUserName(email);
            if(product.getUser(user) == this.userRepository.getUserByUserName(principal.getName())){
                product.setUser(user);
                this.productRepository.save(product);
                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
            }else {
                session.setAttribute("message", new Message("You are the owner of that product!", "alert-danger"));
                System.out.println("user"+user);
                System.out.println("product.getuser"+product.getUser() );
                System.out.println("product.getuserbyname"+this.userRepository.getUserByUserName(principal.getName()));
                System.out.println("you are accessing other contact");
                return "redirect:/user/dashboard";
            }


        }catch (Exception e){
            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));

        }
        return "redirect:/user/product-list";
    }

//    @RequestMapping(value = "/edit-products", method = RequestMethod.POST)
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<Message> editProduct(@ModelAttribute Product product, Principal principal, HttpSession session) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Check if the user is the owner of the product
//            Product existingProduct = this.productRepository.findById(product.getP_id()).orElse(null);
//            if (existingProduct != null && existingProduct.getUser().equals(user)) {
//                // Update the product details
//                existingProduct.setP_name(product.getP_name());
//                existingProduct.setP_url(product.getP_url());
//                existingProduct.setT_price(product.getT_price());
//                this.productRepository.save(existingProduct);
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                return ResponseEntity.ok(new Message("Product updated successfully!", "alert-success"));
//            } else {
//                session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
//                System.out.println("You are trying to edit a product that does not belong to you.");
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("You are not authorized to edit this product.", "alert-danger"));
//            }
//
//        } catch (Exception e) {
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            e.printStackTrace(); // Log the exception for debugging
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("An error occurred. Please try again.", "alert-danger"));
//        }
//    }
@RequestMapping(value = "/edit-products/{id}", method = RequestMethod.GET)
@PreAuthorize("hasAuthority('ROLE_USER')")
public ResponseEntity<Product> getProductForEdit(@PathVariable Integer id, Principal principal) {
    // Fetch the product using the provided ID
    Product product = this.productRepository.findById(id).orElse(null);
    if (product == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if product not found
    }
    // Return the product data
    return ResponseEntity.ok(product);
}
//    @RequestMapping(value = "/edit-products", method = RequestMethod.POST)
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<Message> editProduct(@ModelAttribute Product product, Principal principal) {
//        try {
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//            if (product.getUser() == user) {
//                product.setUser(user);
//                this.productRepository.save(product);
//                return ResponseEntity.ok(new Message("Your product has been modified!", "alert-success"));
//            } else {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body(new Message("You are not the owner of that product!", "alert-danger"));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new Message("Something went wrong! Please try again.", "alert-danger"));
//        }
//    }



//@RequestMapping("/edit-products")
//@PreAuthorize("hasAuthority('ROLE_USER')")
//public ResponseEntity<?> editProduct(@ModelAttribute Product product, Principal principal, HttpSession session) {
//    try {
//        String email = principal.getName();
//        User user = this.userRepository.getUserByUserName(email);
//
//        // Check if the current user is the owner of the product
//        //if (product.getUser().getId().equals(user.getId())) {
//        if(product.getUser() == this.userRepository.getUserByUserName(principal.getName())){
//            product.setUser(user);
//            this.productRepository.save(product);
//            session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//
//            // Return success response with HTTP 200 OK
//            return ResponseEntity.ok("Product successfully updated.");
//        } else {
//            session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
//
//            // Return HTTP 403 Forbidden when trying to modify someone else's product
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to modify this product.");
//        }
//    } catch (Exception e) {
//        session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//
//        // Return HTTP 500 Internal Server Error for any exception
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while modifying the product.");
//    }
//}


//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<?> editProduct(@ModelAttribute Product product, Principal principal, HttpSession session) {
//        try {
//            // Get the currently authenticated user's email
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Ensure that the user modifying the product is the owner
//            //if (product.getUser() != null && product.getUser().getId().equals(user.getId())) {
//            if (product.getUser() != null && product.getUser().getU_id() == user.getU_id()) {
//
//                // Assign the product to the current user and save changes
//                product.setUser(user);
//                this.productRepository.save(product);
//
//                // Add success message to session
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//
//                // Return success message with HTTP 200 OK
//                return ResponseEntity.ok("Product successfully updated.");
//            } else {
//                // Add error message to session
//                session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
//
//                // Return HTTP 403 Forbidden since the user is not authorized
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to modify this product.");
//            }
//        } catch (Exception e) {
//            // Log the exception for debugging purposes (optional)
//            e.printStackTrace();
//
//            // Add error message to session
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//
//            // Return HTTP 500 Internal Server Error with error message
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while modifying the product.");
//        }
//    }



//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<?> editProduct(@ModelAttribute Product product, Principal principal, HttpSession session) {
//        try {
//            // Get the currently authenticated user's email
//            String email = principal.getName();
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Ensure that the user modifying the product is the owner
//            if (product.getUser() != null) {
//                System.out.println("Product's user ID: " + product.getUser().getU_id());
//                System.out.println("Authenticated user's ID: " + user.getU_id());
//
//                if (product.getUser().getU_id() == user.getU_id()) {
//                    // Assign the product to the current user and save changes
//                    product.setUser(user);
//                    this.productRepository.save(product);
//
//                    // Add success message to session
//                    session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//
//                    // Return success message with HTTP 200 OK
//                    return ResponseEntity.ok("Product successfully updated.");
//                } else {
//                    // Add error message to session
//                    session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
//
//                    // Return HTTP 403 Forbidden since the user is not authorized
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to modify this product.");
//                }
//            } else {
//                // Handle case where the product's user is null
//                session.setAttribute("message", new Message("Product does not have an associated user!", "alert-danger"));
//                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product does not have an associated user.");
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product does not have an associated user."+user);
//            }
//        } catch (Exception e) {
//            // Log the exception for debugging purposes
//            e.printStackTrace();
//
//            // Add error message to session
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//
//            // Return HTTP 500 Internal Server Error with error message
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while modifying the product.");
//        }
 //   }


//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<?> editProduct(@ModelAttribute Product product, Principal principal, HttpSession session) {
//        try {
//            // Get the currently authenticated user's email
//            String email = principal.getName(); // This should match "sofia@gmail.com"
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Now, check if the user is allowed to modify the product
//            System.out.println("Authenticated user's ID: " + user.getU_id());
//
//            // Assuming product ID is being used to fetch the product from the database
//            Product existingProduct = this.productRepository.findById(product.getP_id())
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//
//            // Compare the authenticated user's ID with the product's user ID
//            if (user.getU_id()==(existingProduct.getUser().getU_id())) {
//                // Set the user on the product
//                product.setUser(user); // Ensures the user is associated with the product
//
//                // Save the changes to the product
//                this.productRepository.save(product);
//
//                // Add success message to session
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//
//                // Return success message with HTTP 200 OK
//                return ResponseEntity.ok("Product successfully updated.");
//            } else {
//                // Add error message to session
//                session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
//
//                // Return HTTP 403 Forbidden since the user is not authorized
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to modify this product.");
//            }
//        } catch (Exception e) {
//            // Log the exception for debugging purposes
//            e.printStackTrace();
//
//            // Add error message to session
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//
//            // Return HTTP 500 Internal Server Error with error message
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while modifying the product.");
//        }
//    }


//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<?> editProduct(@ModelAttribute Product product, Principal principal, HttpSession session) {
//        try {
//            // Get the currently authenticated user's email
//            String email = principal.getName(); // This should match "sofia@gmail.com"
//            User user = this.userRepository.getUserByUserName(email);
//
//            // Fetch the existing product with its associated user
//            Product existingProduct = this.productRepository.findProductWithUser(product.getP_id());
//
//            // Check if the authenticated user is the owner of the product
//            if (existingProduct != null && user.getU_id()==(existingProduct.getUser().getU_id())) {
//                // Set the user on the product
//                product.setUser(user); // Ensure the user is associated with the product
//
//                // Save the changes to the product
//                this.productRepository.save(product);
//
//                // Add success message to session
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//
//                // Return success message with HTTP 200 OK
//                return ResponseEntity.ok("Product successfully updated.");
//            } else {
//                // Add error message to session
//                session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
//
//                // Return HTTP 403 Forbidden since the user is not authorized
//                //return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to modify this product.");
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are the owner of that product.");
//
//            }
//        } catch (Exception e) {
//            // Log the exception for debugging purposes
//            e.printStackTrace();
//
//            // Add error message to session
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//
//            // Return HTTP 500 Internal Server Error with error message
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while modifying the product.");
//        }
//    }

//    @RequestMapping("/edit-products")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<String> Edit_Products(@ModelAttribute Product product, Principal principal, HttpSession session) {
//        User user;
//        try {
//            String email = principal.getName();
//             user = this.userRepository.getUserByUserName(email);
//            if (product.getUser(user) == this.userRepository.getUserByUserName(principal.getName())) {
//                product.setUser(user);
//                this.productRepository.save(product);
//                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
//                return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully.");
//            } else {
//                session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
//                System.out.println("user: " + user);
//                System.out.println("product.getUser(): " + product.getUser());
//                System.out.println("product.getUserByName(): " + this.userRepository.getUserByUserName(principal.getName()));
//                System.out.println("You are trying to access another user's product.");
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//            }
//
//        } catch (Exception e) {
//            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
//            System.out.println("user: " + user);
//            System.out.println("product.getUser(): " + product.getUser());
//            System.out.println("product.getUserByName(): " + this.userRepository.getUserByUserName(principal.getName()));
//            System.out.println("You are trying to access another user's product.");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//        //return ResponseEntity.status(HttpStatus.OK).body("Redirecting to product list.");
//    }

    @RequestMapping("/edit-products")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    //public ResponseEntity<String> Edit_Products(@ModelAttribute Product product, Principal principal, HttpSession session) {
    public ResponseEntity<String> Edit_Products(@RequestBody Product product, Principal principal, HttpSession session) {
        User user=null;
        try {
            String email = principal.getName();
            user = this.userRepository.getUserByUserName(email);

            // Check if the product belongs to the user
            //if (product.getUser(user).equals(user)) { // Assuming getUser() returns User object
            if (product.getUser(user)==this.userRepository.getUserByUserName(principal.getName())) { // Assuming getUser() returns User object
                product.setUser(user);
                this.productRepository.save(product);
                session.setAttribute("message", new Message("Your product has been modified!", "alert-success"));
                return ResponseEntity.ok("Product updated successfully.");
            } else {
                session.setAttribute("message", new Message("You are not the owner of that product!", "alert-danger"));
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
            }

        } catch (Exception e) {
            session.setAttribute("message", new Message("Something went wrong! Please try again.", "alert-danger"));
            System.out.println("user: " + user);
            System.out.println("product.getUser(): " + product.getUser(user));
            System.out.println("product.getUserByName(): " + this.userRepository.getUserByUserName(principal.getName()));
            e.printStackTrace();  // Log the exception details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

}
