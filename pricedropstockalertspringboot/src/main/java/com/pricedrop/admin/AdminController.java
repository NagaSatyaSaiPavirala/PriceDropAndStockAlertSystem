package com.pricedrop.admin;

import com.pricedrop.admin.dao.ApiRepository;
import com.pricedrop.admin.entities.Productapi;
import com.pricedrop.services.UrlCoding;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private ApiRepository apiRepository;

    @RequestMapping("/getproduct")
    public List<Productapi> GetProduct(){
        return this.apiRepository.findAll();
    }

    @PostMapping("/add-product")
    public Productapi addUser(@RequestBody Productapi productapi){
        return this.apiRepository.save(productapi);
    }

    @PutMapping("/edit-product/{id}")
    public Productapi editProduct(@PathVariable int id, @RequestBody Productapi updatedProduct) {
        Optional<Productapi> optionalProduct = apiRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Productapi product = optionalProduct.get();
            product.setProduct_url(updatedProduct.getProduct_url());
            product.setProductName(updatedProduct.getProductName());
            product.setProduct_image(updatedProduct.getProduct_image());
            product.setProduct_price(updatedProduct.getProduct_price());
            return apiRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @DeleteMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable int id) {
        apiRepository.deleteById(id);
        return "Product deleted successfully";
    }

    @GetMapping("/getproductbyname/{name}")
    public Productapi getProductByName(@PathVariable String name) {
        Optional<Productapi> optionalProduct = apiRepository.findByProductName(name);
        System.out.println(name);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new RuntimeException("Product not found with name: " + name);
        }
    }



//previous working
//    @GetMapping("/getproductbyurl")
//    public Productapi getProductByUrl( String url) {
////        try {
////            // Decode the URL to ensure it matches the stored format
////            //url = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
////        } catch (UnsupportedEncodingException e) {
////            throw new RuntimeException("Failed to decode URL", e);
////        }
//
//        System.out.println("Decoded URL: " + url);
//
//        // Query the database with the full URL including query parameters
//        Optional<Productapi> optionalProduct = Optional.ofNullable(apiRepository.findByProductUrl(url));
//
//        if (optionalProduct.isPresent()) {
//            return optionalProduct.get();
//        } else {
//            // Debugging statement to show exactly what URL was used in the query
//            System.out.println("Product not found with URL: " + url);
//            throw new RuntimeException("Product not found with URL: " + url);
//        }
//    }

    @GetMapping("/getproductbyurl")
    public ResponseEntity<Productapi> getProductByUrl(@RequestParam("url") String url) {
        try {
            System.out.println("Decoded URL: " + url);

            // Query the database with the full URL
            Productapi product = apiRepository.findByProductUrl(url);

            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                System.out.println("Product not found with URL: " + url);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null); // or use a custom error response object if preferred
            }

        } catch (Exception e) {
            System.err.println("Error retrieving product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // or return a detailed error body if needed
        }
    }


    @GetMapping("/getproductbylikename/{name}")
    public List<Productapi> getProductbyLikeName(@PathVariable("name") String name){
        List<Productapi> productapiList = apiRepository.findByProductNameContains(name);
        return productapiList;
    }



}
