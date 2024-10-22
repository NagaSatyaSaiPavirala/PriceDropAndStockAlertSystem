package com.pricedrop.Controller;

import com.pricedrop.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getcurrentprice")
    public String getCurrentPrice(@RequestParam String url) {
        try {
            return productService.getCurrentPrice(url);
        } catch (Exception e) {
            return "Error fetching product price: " + e.getMessage();
        }
    }

    @GetMapping("/getimageurl")
    public String getImageUrl(@RequestParam String url) {
        try {
            return productService.getImageUrl(url);
        } catch (Exception e) {
            return "Error fetching product image: " + e.getMessage();
        }
    }
//    @DeleteMapping("/reactdeleteproduct/{id}")
//    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
//        try {
//            productService.deleteProduct(id); // Call the service to delete the product
//            return ResponseEntity.ok().build(); // Return a 200 OK response
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Return a 404 if not found
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product");
//        }
//    }
}
