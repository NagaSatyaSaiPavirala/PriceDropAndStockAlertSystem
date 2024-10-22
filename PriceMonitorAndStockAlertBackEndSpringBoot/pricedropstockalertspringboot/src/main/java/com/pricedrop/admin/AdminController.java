package com.pricedrop.admin;

import com.pricedrop.admin.dao.ApiRepository;
import com.pricedrop.admin.entities.Productapi;
import com.pricedrop.services.UrlCoding;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
            product.setProduct_name(updatedProduct.getProduct_name());
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



/*
    @GetMapping("/getproductbyurl/{url}")
    public Productapi getProductByUrl(@PathVariable("url") String url) {
//        url = UrlCoding.decodeUrl(url);
       // url = "https://www.example.com/product/" + url;
        Optional<Productapi> optionalProduct = Optional.ofNullable(apiRepository.findByProductUrl(url));
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new RuntimeException("Product not found with URL: " + url);
        }
    }
*/

    /*
//working for curl -u "p.nagasatyasai.123@gmail.com:satya" "http://localhost:8080/admin/getproductbyurl?url=http://localhost/"
//{"product_id":302,"product_url":"http://localhost/","product_name":"Samsung Galaxy S24 Ultra 5G AI Smartphone (Titanium Gray, 12GB, 512GB Storage)","product_image":"https://m.media-amazon.com/images/I/41fCDR6pjpL._SX300_SY300_QL70_ML2_.jpg","product_price":"121999.0"}
@GetMapping("/getproductbyurl")
public Productapi getProductByUrl(@RequestParam("url") String url) {
    // Decode the URL
    //url = UrlCoding.decodeUrl(url);
    //System.out.println(url);
    Optional<Productapi> optionalProduct = Optional.ofNullable(apiRepository.findByProductUrl(url));
    if (optionalProduct.isPresent()) {
        return optionalProduct.get();
    } else {
        throw new RuntimeException("Product not found with URL: " + url);
    }
}
//@RequestParam("url") in getProductByUrl
     */
    @GetMapping("/getproductbyurl")
    public Productapi getProductByUrl( String url) {
//        try {
//            // Decode the URL to ensure it matches the stored format
//            //url = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("Failed to decode URL", e);
//        }

        System.out.println("Decoded URL: " + url);

        // Query the database with the full URL including query parameters
        Optional<Productapi> optionalProduct = Optional.ofNullable(apiRepository.findByProductUrl(url));

        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            // Debugging statement to show exactly what URL was used in the query
            System.out.println("Product not found with URL: " + url);
            throw new RuntimeException("Product not found with URL: " + url);
        }
    }


    /*
@GetMapping("/getproductbyurl/{url}")
public Productapi getProductByUrl(@PathVariable("url") String url) {
    // Optionally decode the URL if necessary
    // url = UrlCoding.decodeUrl(url);
    System.out.println(url);
    Optional<Productapi> optionalProduct = Optional.ofNullable(apiRepository.findByProductUrl(url));
    if (optionalProduct.isPresent()) {
        return optionalProduct.get();
    } else {
        throw new RuntimeException("Product not found with URL: " + url);
    }
}

     */

    /*
    @GetMapping("/getproductbyurl/**")
    public Productapi getProductByUrl(HttpServletRequest request) {
        // Retrieve the complete URL from the request
        String url = request.getRequestURI().substring(request.getContextPath().length() + "/getproductbyurl/".length());

        // Check if the URL is valid and perform your database query
        Optional<Productapi> optionalProduct = Optional.ofNullable(apiRepository.findByProductUrl(url));

        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new RuntimeException("Product not found with URL: " + url);
        }
    }
*/
    @GetMapping("/getproductbylikename/{name}")
    public List<Productapi> getProductbyLikeName(@PathVariable("name") String name){
        List<Productapi> productapiList = apiRepository.findByProductNameContains(name);
        return productapiList;
    }



}
