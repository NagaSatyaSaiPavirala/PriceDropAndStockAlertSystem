package com.pricedrop.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;

@Service
public class ProductScraperService {

    public String scrapeProductTitle(String productUrl) throws IOException {
        Document doc = Jsoup.connect(productUrl)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        Element titleElement = doc.selectFirst("#productTitle");
        return titleElement != null ? titleElement.text().trim() : null;
    }

//    public Double scrapeProductPrice(String productUrl) throws IOException {
//        Document doc = Jsoup.connect(productUrl)
//                .userAgent("Mozilla/5.0")
//                .timeout(10000)
//                .get();
//
//        Element wholePriceElement = doc.selectFirst(".a-price .a-price-whole");
//        Element fractionPriceElement = doc.selectFirst(".a-price .a-price-fraction");
//
//        double currentPrice = 0.0;
//        if (wholePriceElement != null) {
//            String wholePrice = wholePriceElement.text().replace(",", "");
//            if (fractionPriceElement != null) {
//                String fractionPrice = fractionPriceElement.text().replace(",", "");
//                currentPrice = Double.parseDouble(wholePrice + "." + fractionPrice);
//            } else {
//                currentPrice = Double.parseDouble(wholePrice);
//            }
//        }
//        return currentPrice;
//    }


    /*
public Double scrapeProductPrice(String productUrl) throws IOException {
    Document doc = Jsoup.connect(productUrl)
            .userAgent("Mozilla/5.0")
            .timeout(10000)
            .get();

   // Element wholePriceElement = doc.selectFirst(".a-price .a-price-whole");
    Element wholePriceElement = doc.selectFirst(".a-price.aok-align-center.reinventPricePriceToPayMargin.priceToPay span[aria-hidden='true'] .a-price-whole");

    Element fractionPriceElement = doc.selectFirst(".a-price .a-price-fraction");

    double currentPrice = 0.0;
    String priceString = ""; // Initialize price string

    if (wholePriceElement != null) {
        String wholePrice = wholePriceElement.text().replace(",", ""); // Remove commas

        // Check if fractionPriceElement exists and get its value
        String fractionPrice = fractionPriceElement != null ? fractionPriceElement.text().replace(",", "") : "00"; // Default to "00" if not present

        // Create full price string without introducing multiple decimal points
        priceString = wholePrice + "" + fractionPrice;

        // Log the raw price string for debugging
        System.out.println("Raw Price String: " + priceString);

        // Check if the price string is valid before parsing
        if (priceString != null && priceString.split("\\.").length <= 2) {
            try {
                currentPrice = Double.parseDouble(priceString);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing price: " + priceString);
                return null; // Handle the parsing error
            }
        } else {
            System.out.println("Invalid price format: " + priceString);
            return null; // Return null or handle the error accordingly
        }
    } else {
        System.out.println("Whole price element not found.");
    }

    return currentPrice;
}

     */

/*
//below is working code
 /*
    public Double scrapeProductPrice(String productUrl) throws IOException {
        Document doc = Jsoup.connect(productUrl)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        Element wholePriceElement = doc.selectFirst(".a-price.aok-align-center.reinventPricePriceToPayMargin.priceToPay span[aria-hidden='true'] .a-price-whole");
        Element fractionPriceElement = doc.selectFirst(".a-price .a-price-fraction");

        double currentPrice = 0.0;

        if (wholePriceElement != null) {
            String wholePrice = wholePriceElement.text().replace(",", ""); // Remove commas
            String fractionPrice = (fractionPriceElement != null) ? fractionPriceElement.text().replace(",", "") : "00"; // Default to "00"

            // Create the full price string
            String priceString = wholePrice + "." + fractionPrice; // Concatenate whole and fraction

            // Log the raw price string for debugging
          //  System.out.println("Raw Price String: " + priceString);

            // Validate the price format
            if (priceString.split("\\.").length <= 2) {
                try {
                    currentPrice = Double.parseDouble(priceString);
                    // Format to show one decimal place
                    DecimalFormat decimalFormat = new DecimalFormat("#.0");
                    String formattedPrice = decimalFormat.format(currentPrice);
                  //  System.out.println("Formatted Product Price: " + formattedPrice); // This will print as 131999.0
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing price: " + priceString);
                    return null; // Handle the parsing error
                }
            } else {
                System.out.println("Invalid price format: " + priceString);
                return null; // Return null or handle the error accordingly
            }
        } else {
            System.out.println("Whole price element not found for "+productUrl);
        }

        return currentPrice; // Return the price as a Double
    }
*/
public Double scrapeProductPrice(String productUrl) throws IOException {
    Document doc = Jsoup.connect(productUrl)
            .userAgent("Mozilla/5.0")
            .timeout(10000)
            .get();

    // Check if the product is currently unavailable
    Element unavailableElement = doc.selectFirst("span.a-size-medium.a-color-success");
    if (unavailableElement != null && unavailableElement.text().contains("Currently unavailable")) {
        System.out.println("Product is currently unavailable.");
        return null; // Return null or handle unavailable case accordingly
    }

    // Try to fetch the price elements
    Element wholePriceElement = doc.selectFirst(".a-price.aok-align-center.reinventPricePriceToPayMargin.priceToPay span[aria-hidden='true'] .a-price-whole");
    Element fractionPriceElement = doc.selectFirst(".a-price .a-price-fraction");

    double currentPrice = 0.0;

    if (wholePriceElement != null) {
        String wholePrice = wholePriceElement.text().replace(",", ""); // Remove commas
        String fractionPrice = (fractionPriceElement != null) ? fractionPriceElement.text().replace(",", "") : "00"; // Default to "00"

        // Create the full price string
        String priceString = wholePrice + "." + fractionPrice; // Concatenate whole and fraction

        // Validate the price format
        if (priceString.split("\\.").length <= 2) {
            try {
                currentPrice = Double.parseDouble(priceString);
                // Format to show one decimal place
                DecimalFormat decimalFormat = new DecimalFormat("#.0");
                String formattedPrice = decimalFormat.format(currentPrice);
                System.out.println("Scraped Product Price for "+productUrl+": " + formattedPrice); // This will print as 131999.0
            } catch (NumberFormatException e) {
                System.out.println("Error parsing price: " + priceString);
                return null; // Handle the parsing error
            }
        } else {
            System.out.println("Invalid price format: " + priceString);
            return null; // Return null or handle the error accordingly
        }
    } else {
        System.out.println("Amazon not allowing to scrape price for " + productUrl);
    }

    return currentPrice; // Return the price as a Double
}



    public String scrapeProductImage(String productUrl) throws IOException {
        Document doc = Jsoup.connect(productUrl)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        Element imageElement = doc.selectFirst("#landingImage");
        return imageElement != null ? imageElement.attr("src") : null;
    }

}
