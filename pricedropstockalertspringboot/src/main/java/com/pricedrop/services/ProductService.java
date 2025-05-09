package com.pricedrop.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricedrop.dao.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    //public static Double getCurrentPrice(String productUrl) throws IOException, InterruptedException
    public static String getCurrentPrice(String productUrl) throws IOException, InterruptedException
    {
        try {
            // Construct the request URL
            //String url = "http://localhost:8080/admin/getproductbyurl/" + productUrl;
            String url = "http://localhost:8080/admin/getproductbyurl?url=" + productUrl;
            System.out.println("Request getCurrentPrice URL: " + url);

            // Set up the username and password for basic authentication
            String username = "p.nagasatyasai.123@gmail.com";
            String password = "satya"; // Ensure you use the correct password
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            // Build the HTTP GET request with the Authorization header
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .header("Authorization", "Basic " + encodedAuth) // Add basic authentication header
                    .build();

            // Create HTTP client and send the request
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print response status and headers for debugging
         //   System.out.println("Response Code: " + response.statusCode());
        //    System.out.println("Response Headers: " + response.headers());

            // Check if the response body is empty or null
            if (response.body() == null || response.body().isEmpty()) {
                System.out.println("Response is empty or null");
                return null;
            }

            // Check if the content type is JSON before attempting to parse
            String contentType = response.headers().firstValue("Content-Type").orElse("");
            if (!contentType.contains("application/json")) {
                System.out.println("Unexpected content type: " + contentType);
                System.out.println("Response Body: " + response.body());
                return null;
            }

            // Parse the JSON response using ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            ApiProduct productApi = objectMapper.readValue(response.body(), ApiProduct.class);

            // Check if the parsed product object is null
            if (productApi == null) {
                System.out.println("Parsed object is null");
                return null;
            }

            // Print and return the product price
            System.out.println("Product Price: " + productApi.getProduct_price());
            if(productApi.getProduct_price()!="Currently Unavailable")
                return productApi.getProduct_price();
            //return Double.valueOf(productApi.getProduct_price());
            else
                return productApi.getProduct_price();
               // return null;
           //
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception details for debugging
            return null;
        }
    }

    public String getImageUrl(String productUrl){
        try {
            var url = "http://localhost:8080/admin/getproductbyurl?url=" + productUrl;
            System.out.println("Request getImageUrl "+url);
            var username = "p.nagasatyasai.123@gmail.com";
            var password = "satya";

            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            //System.out.println(encodedAuth);
//String encodedAuth=auth;
            var request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .header("Authorization", "Basic " + encodedAuth) // Add basic authentication header
                    .build();

            var client = HttpClient.newBuilder().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            ApiProduct productApisArray = objectMapper.readValue(response.body(), ApiProduct.class);

            // Convert array to List


            // Access specific fields
        //    System.out.println("Product Image url: " + productApisArray.getProduct_image());
            return String.valueOf(productApisArray.getProduct_image());
        }catch (Exception e){
            return null;
        }

    }

    /*
public String getImageUrl(String productUrl) {
        /*
    System.out.println("getImageUrl called with productUrl: " + productUrl);

    try {
        var url = "http://localhost:8080/admin/getproductbyurl/" + productUrl;
        System.out.println("Constructed URL: " + url);

        var username = "p.nagasatyasai.123@gmail.com";
        var password = "satya";
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Authorization", "Basic " + encodedAuth)
                .build();

        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print response code and body
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        // Parse JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        ApiProduct productApisArray = objectMapper.readValue(response.body(), ApiProduct.class);

        // Access specific fields
        System.out.println("Product Image url: " + productApisArray.getProduct_image());
        return productApisArray.getProduct_image();
    } catch (Exception e) {
        e.printStackTrace(); // Print stack trace for debugging
        return null;
    }


    try {
        Document doc = Jsoup.connect(productUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .timeout(10000)
                .get();

        Element imgElement = doc.selectFirst("#landingImage"); // Use the selector to find the image element
        if (imgElement != null) {
            String imageUrl = imgElement.attr("src");
            System.out.println("Amazon Image URL: " + imageUrl);
            return imageUrl; // Return the scraped image URL
        } else {
            System.out.println("Amazon Image URL not found.");
            return null; // Return null if the image is not found
        }
    } catch (Exception e) {
        e.printStackTrace(); // Log any exceptions that occur during scraping
        return null; // Return null in case of an error
    }
}
*/

    public void deleteProduct(Integer id) {
        // Check if the product exists before trying to delete it
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product not found");
        }
    }
}
