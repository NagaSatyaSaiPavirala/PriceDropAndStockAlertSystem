//package com.pricedrop.services;
//
//import com.pricedrop.Controller.UserController;
//import com.pricedrop.admin.dao.ApiRepository;
//import com.pricedrop.admin.entities.Productapi;
//import com.pricedrop.dao.ProductRepository;
//import com.pricedrop.dao.UserRepository;
//import com.pricedrop.entities.Product;
//import com.pricedrop.entities.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class ProductCheckPrice {
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ApiRepository apiRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private ProductScraperService productScraperService;
//
////10000 milli seconds that is 10 seconds
//   // 6000000
//    @Scheduled(fixedRate = 10000)
//    public void CheckPrice() throws IOException, InterruptedException {
//        List<Product> productList = productRepository.findAll();
//        for(Product products : productList){
//            Double currentPrice=productScraperService.scrapeProductPrice(UrlCoding.extractProductName(products.getP_url()));
//            //if((products.getT_price()) >= ProductService.getCurrentPrice(UrlCoding.extractProductName(products.getP_url())))
//            //if(((products.getT_price()) >=currentPrice )&&( currentPrice!=null))
//            if (currentPrice!=null && currentPrice != 0.0 && products.getT_price() >= currentPrice)
//            {
//                System.out.println("send price notification");
//                //sendNotification(products,ProductService.getCurrentPrice(UrlCoding.extractProductName(products.getP_url())));
//                sendPriceNotification(products,currentPrice);
//                deleteProduct(products.getP_id());
//
//        }
//            Productapi existingProductApi = apiRepository.findByProductUrl(products.getP_url());
//            if(currentPrice!=null && currentPrice!=0.0 &&"Currently Unavailable".equals(existingProductApi.getProduct_price()) )
//            {
//                System.out.println("send stock notification");
//                //String productTitle=existingProductApi.getProduct_name();
//                //String productUrl=existingProductApi.getProduct_url();
//                sendStockNotification(products);
//                deleteProduct(products.getP_id());
//            }
//    }
//    }
//
//    public void sendPriceNotification(Product product,Double price){
//        int u_id = product.getUser().getU_id();
//        Optional<User> user = this.userRepository.findById(u_id);
//        //boolean b = this.emailService.sendEmail("Price has been dropped !!","The price of the product "+UrlCoding.extractProductName(product.getP_url())+" has dropped to "+price+" <br>link:-  "+product.getP_url(),user.get().getEmail());
//        boolean b = this.emailService.sendEmail("Price is dropped !!","The price of the product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>with threshold price "+product.getT_price()+" <br>has dropped to "+price+" <br>Link to purchase product:-  "+product.getP_url(),user.get().getEmail());
//    }
//    public void sendStockNotification(Product product){
//        int u_id = product.getUser().getU_id();
//        Optional<User> user = this.userRepository.findById(u_id);
//        //boolean b = this.emailService.sendEmail("Price has been dropped !!","The price of the product "+UrlCoding.extractProductName(product.getP_url())+" has dropped to "+price+" <br>link:-  "+product.getP_url(),user.get().getEmail());
//        boolean b = this.emailService.sendEmail("Stock is back !!","The product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>came back into stock.  <br>Link to purchase product:-  "+product.getP_url(),user.get().getEmail());
//    }
//    @Transactional
//    public void deleteProduct(int productID) {
//        try {
//            Optional<Product> productOptional = this.productRepository.findById(productID);
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//                User user = product.getUser();
//                user.getProduct().remove(product);
//                this.userRepository.save(user);// Update the user entity
//                this.productRepository.deleteById(productID); // Delete the product by ID
//                System.out.println("Product deleted successfully: " + productID);
//            } else {
//                System.out.println("Product not found: " + productID);
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to delete product: " + productID);
//            e.printStackTrace();
//        }
//    }
//}


//Observable


//import com.pricedrop.dao.ProductRepository;
//import com.pricedrop.entities.Product;
//import com.pricedrop.services.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Transactional
//public class ProductCheckPrice {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductScraperService productScraperService;
//
//    @Autowired
//    private PriceDropObserver priceDropObserver;
//
//    @Autowired
//    private StockAvailabilityObserver stockAvailabilityObserver;
//
//    private final List<ProductObserver> observers = new ArrayList<>();
//
//    public ProductCheckPrice() {
//        // Register observers
//        observers.add(priceDropObserver);
//        observers.add(stockAvailabilityObserver);
//    }
//
//    // Notify all observers
//    private void notifyObservers(Product product, Double currentPrice) {
//        for (ProductObserver observer : observers) {
//            observer.update(product, currentPrice);
//        }
//    }
//
//    // Scheduled task to check prices
//    //10000 milli seconds that is 10 seconds
//    //6000000
//  //  @Scheduled(fixedRate = 10000)
////    public void checkPrice() throws IOException {
////        List<Product> productList = productRepository.findAll();
////
////        for (Product product : productList) {
////            Double currentPrice = productScraperService.scrapeProductPrice(UrlCoding.extractProductName(product.getP_url()));
////
////            // Notify observers about the price change
////            if (currentPrice != null && currentPrice > 0) {
////                notifyObservers(product, currentPrice);
////            }
////        }
////    }
//    @Scheduled(fixedRate = 10000)
//    public void checkPrice() throws IOException {
//        List<Product> productList = productRepository.findAll();
//
//        for (Product product : productList) {
//            Double currentPrice = productScraperService.scrapeProductPrice(UrlCoding.extractProductName(product.getP_url()));
//
//            System.out.println("Checking product: " + product.getP_name() + " | Threshold: " + product.getT_price() + " | Current: " + currentPrice);
//
//            if (currentPrice != null && currentPrice > 0) {
//                System.out.println("Notifying observers for product: " + product.getP_name());
//                notifyObservers(product, currentPrice);
//            } else {
//                System.out.println("Skipping notification for: " + product.getP_name() + " due to invalid price.");
//            }
//        }
//    }
//}



////for sql
//package com.pricedrop.services;
//import com.pricedrop.admin.dao.ApiRepository;
//import com.pricedrop.admin.entities.Productapi;
//import com.pricedrop.dao.ProductRepository;
//import com.pricedrop.entities.Product;
//import com.pricedrop.services.ProductNotifier;
//import com.pricedrop.services.ProductScraperService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.io.IOException;
//import java.util.List;
//
//@Service
//@Transactional
//public class ProductCheckPrice extends ProductNotifier {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ApiRepository apiRepository;
//
//    @Autowired
//    private ProductScraperService productScraperService;
//
//    @Scheduled(fixedRate = 10000)
//    public void checkPrice() throws IOException {
//        List<Product> productList = productRepository.findAll();
//
//        for (Product product : productList) {
//            Double currentPrice = productScraperService.scrapeProductPrice(UrlCoding.extractProductName(product.getP_url()));
//
//            if (currentPrice != null && currentPrice != 0.0 && product.getT_price() >= currentPrice) {
//                System.out.println("Sending price drop notification...");
//                notifyObservers(product, "PRICE_DROP", currentPrice);
//                deleteProduct(product.getP_id());
//            }
//
//            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
//            if (currentPrice != null && currentPrice != 0.0 && "Currently Unavailable".equals(existingProductApi.getProduct_price())) {
//                System.out.println("Sending stock notification...");
//                notifyObservers(product, "STOCK_BACK", null);
//                deleteProduct(product.getP_id());
//            }
//        }
//    }
//
//    @Transactional
//    public void deleteProduct(int productID) {
//        productRepository.findById(productID).ifPresent(product -> {
//            product.getUser().getProduct().remove(product);
//            productRepository.delete(product);
//            System.out.println("Deleted product: " + productID);
//        });
//    }
//}




//for sql
package com.pricedrop.services;
import com.pricedrop.admin.dao.ApiRepository;
import com.pricedrop.admin.entities.Productapi;
import com.pricedrop.dao.ProductRepository;
import com.pricedrop.entities.Product;
import com.pricedrop.entities.User;
import com.pricedrop.services.ProductNotifier;
import com.pricedrop.services.ProductScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.pricedrop.dao.UserRepository;

@Service
@Transactional
public class ProductCheckPrice extends ProductNotifier {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private ProductScraperService productScraperService;

//    @Scheduled(fixedRate = 10000)
//    public void checkPrice() throws IOException {
//        List<Product> productList = productRepository.findAll();
//
//        for (Product product : productList) {
//            Double currentPrice = productScraperService.scrapeProductPrice(UrlCoding.extractProductName(product.getP_url()));
//
//            if (currentPrice != null && currentPrice != 0.0 && product.getT_price().doubleValue() >= currentPrice) {
//                System.out.println("Sending price drop notification...");
//                notifyObservers(product, "PRICE_DROP", currentPrice);
//                //deleteProduct(product.getP_id());
//                deleteProduct(product.getP_id(), currentUser.getUsername());
//
//            }
//
//            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
//            if (currentPrice != null && currentPrice != 0.0 && "Currently Unavailable".equals(existingProductApi.getProduct_price())) {
//                System.out.println("Sending stock notification...");
//                notifyObservers(product, "STOCK_BACK", null);
//               // deleteProduct(product.getP_id());
//                deleteProduct(product.getP_id(), currentUser.getUsername());
//
//            }
//
////            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
////
////            if (existingProductApi != null) { // ✅ Check if it's not null before accessing it
////                if (currentPrice != null && currentPrice != 0.0 && "Currently Unavailable".equals(existingProductApi.getProduct_price())) {
////                    System.out.println("Sending stock notification...");
////                    notifyObservers(product, "STOCK_BACK", null);
////                    deleteProduct(product.getP_id());
////                }
////            } else {
////                System.out.println("Product not found in ProductAPI for URL: " + product.getP_url());
////            }
//
//        }
//    }


    @Scheduled(fixedRate = 10000)
    public void checkPrice() throws IOException {
        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            Double currentPrice = productScraperService.scrapeProductPrice(UrlCoding.extractProductName(product.getP_url()));

            if (currentPrice != null && currentPrice != 0.0 && product.getT_price().doubleValue() >= currentPrice) {
                System.out.println("Sending price drop notification...");
                notifyObservers(product, "PRICE_DROP", currentPrice);

//                // ✅ Fetch the user before calling deleteProduct()
//                User currentUser = userRepository.findById(product.getUserId()).orElse(null);
//                if (currentUser != null) {
                   // deleteProduct(product.getP_id(), currentUser.getUsername());
                deleteProduct(product.getP_id(),product.getT_price());

               // }
            }

            Productapi existingProductApi = apiRepository.findByProductUrl(product.getP_url());
            if (currentPrice != null && currentPrice != 0.0 && "Currently Unavailable".equals(existingProductApi.getProduct_price())) {
                System.out.println("Sending stock notification...");
                notifyObservers(product, "STOCK_BACK", null);

                // ✅ Fetch the user before calling deleteProduct()
//                User currentUser = userRepository.findById(product.getUserId()).orElse(null);
//                if (currentUser != null) {
                    //deleteProduct(product.getP_id(), currentUser.getUsername());
              //  deleteStockProduct(product.getP_id());
                deleteProduct(product.getP_id(),product.getT_price());
                //}
            }
        }
    }






//    @Transactional
//    public void deleteProduct(int productID) {
//        productRepository.findById(productID).ifPresent(product -> {
//            //product.getUser_id().getProduct().remove(product);
//            int userId = product.getUser_id();
//
//// Step 2: Fetch the user from the database
//            List<Product> userProducts = productRepository.findByUserId(userId);
//
////// Step 3: Remove product from user's product list (only if user exists)
////                user.getProduct().remove(product);//  Now it works!
//
//            // Step 3: Remove the product from the list
//            userProducts.remove(product);
//
//// Step 4: Delete the product from Cassandra
//            productRepository.delete(product);
//
//
//            productRepository.delete(product);
//            System.out.println("Deleted product: " + productID);
//        });
//    }


//    @Transactional
//    public void deleteProduct(int productID, String username) {
//        // Fetch product by productID
//        List<Product> products = productRepository.findProductById(productID);
//
//        if (products.isEmpty()) {
//            throw new RuntimeException("Product not found!");
//        }
//
//        // Assuming only one result is expected, use the first product
//        Product product = products.get(0);
//
//        // Fetch the currently logged-in user
//        User currentUser = userRepository.getUserByUserName(username);
//
//        // Ensure the product belongs to the user before deletion
//        if (product.getUserId() == currentUser.getU_id()) {
//            // Delete the product using p_id and t_price
//            productRepository.deleteProductByIdAndPrice(productID, product.getT_price());
//
//            System.out.println("Deleted product: " + productID);
//        } else {
//            throw new RuntimeException("Unauthorized: You cannot delete another user's product!");
//        }
//    }



//    @Transactional
//    public void deleteProduct(int productID) {
//        // Fetch product by productID
//        List<Product> products = productRepository.findProductById(productID);
//
//        if (products.isEmpty()) {
//            throw new RuntimeException("Product not found!");
//        }
//
//        // Assuming only one result is expected, use the first product
//        Product product = products.get(0);
////String username=product.getUserId()
//        // Fetch the currently logged-in user
//        User currentUser = userRepository.getUserByUserName(username);
//
//        // Ensure the product belongs to the user before deletion
//        if (product.getUserId() == currentUser.getU_id()) {
//            // Delete the product using p_id and t_price
//            productRepository.deleteProductByIdAndPrice(productID, product.getT_price());
//
//            System.out.println("Deleted product: " + productID);
//        } else {
//            throw new RuntimeException("Unauthorized: You cannot delete another user's product!");
//        }
//    }


//    @Transactional
//    public void deleteProduct(int productID) {
//        // Fetch product by productID
//        List<Product> products = productRepository.findProductById(productID);
//
//        if (products.isEmpty()) {
//            throw new RuntimeException("Product not found!");
//        }
//
//        // Assuming only one result is expected, use the first product
//        Product product = products.get(0);
//
//        // Delete the product using p_id and t_price
//        productRepository.deleteProductByIdAndPrice(productID, product.getT_price());
//
//        System.out.println("Deleted product: " + productID);
//    }

    @Transactional
    public void deleteProduct(int productID, BigDecimal thresholdPrice) {
//        // Fetch product by productID
//        List<Product> products = productRepository.findProductById(productID);
//
//        if (products.isEmpty()) {
//            throw new RuntimeException("Product not found!");
//        }
//
//        // Assuming only one result is expected, use the first product
//        Product product = products.get(0);
//
//        // Check if current price is less than or equal to the threshold price
//        if (currentPrice<= product.getT_price()) {
//            // Delete the product using p_id and t_price
//            productRepository.deleteProductByIdAndPrice(productID, product.getT_price());
//
//            System.out.println("Deleted product: " + productID);
//        } else {
//            throw new RuntimeException("Product's price is above threshold, cannot delete!");
//        }
        productRepository.deleteProductByIdAndPrice(productID, thresholdPrice);
        System.out.println("Deleted product: " + productID+", Threshold price: "+thresholdPrice);
    }


//    @Transactional
//    public void deleteStockProduct(int productID) {
//        // Fetch product by productID
//        List<Product> products = productRepository.findProductById(productID);
//
//        if (products.isEmpty()) {
//            throw new RuntimeException("Product not found!");
//        }
//
//        // Assuming only one result is expected, use the first product
//        Product product = products.get(0);
//
//
//            productRepository.deleteProductByIdAndPrice(productID, product.getT_price());
//
//            System.out.println("Deleted product: " + productID);
//
//    }


//    @Transactional
//    public void deleteProduct(int productID, String username) {
//        User currentUser = userRepository.getUserByUserName(username);
//
//        productRepository.findById(productID).ifPresent(product -> {
//            if (product.getUserId() == currentUser.getU_id()) {
//                List<Product> userProducts = productRepository.findByUserId(currentUser.getU_id());
//                userProducts.remove(product);
//                productRepository.delete(product);
//                System.out.println("Deleted product: " + productID);
//            } else {
//                System.out.println("Unauthorized delete attempt for product: " + productID);
//            }
//        });
//    }



}
