//package com.pricedrop.services;
//
//import com.pricedrop.dao.PageRepository;
//import com.pricedrop.model.PageInfo;
//import com.pricedrop.model.URL;
//import lombok.Builder;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import retrofit2.Retrofit;
//
//import java.io.IOException;
//import java.util.*;
//@Builder
//@Service
//
//public class URLProcessor {
//    private static final Logger LOG = LoggerFactory.getLogger(URLProcessor.class);
//    @Autowired
//    private Retrofit retrofit;
//    @Autowired
//    private PageRepository pageRepository;
//
//
//    @Async
//    public void process(String url, String urlId) {
//        try {
//            Document doc = Jsoup.connect(url).get();
//            Set<URL> urls = fetchAnchorTags(doc);
//            PageInfo pageInfo = fetchMetaInformation(doc);
//            pageInfo.setUrlId(urlId);
//            pageInfo.setUrl(url);
//            LOG.info("PageInfo: {}", pageInfo);
//            pageRepository.save(pageInfo);
////            Response<Void> response = retrofit.create(URLFeederService.class).batchPublish(urls).execute();
////            if (!response.isSuccessful()) {
////                LOG.info("Retrofit called failed, with response code {}", response.code());
////            }
//        } catch (IOException ex) {
//            LOG.error("Exception: ", ex);
//        }
//    }
//
//
//
//    private PageInfo fetchMetaInformation(Document doc) {
//        return PageInfo.builder()
//                .id(UUID.randomUUID().toString())
//                .title(doc.title())
//                .description(description(doc))
//                .body(body(doc))
//                .keywords(keywords(doc))
//                .createdTime(new Date())
//                .build();
//    }
//
//    private String body(Document doc) {
//        return doc.body().text();
//    }
//
//    private String description(Document doc) {
//        Elements ele = doc.select("meta[name=description]");
//        if (!ele.isEmpty()) {
//            return ele.first().attr("content");
//        }
//        return null;
//    }
//
//    private List<String> keywords(Document doc) {
//        Elements ele = doc.select("meta[name=keywords]");
//        if(!ele.isEmpty()) {
//            String keywords = ele.first().attr("content");
//            return Arrays.asList(keywords.split(","));
//        }
//        return Collections.emptyList();
//    }
//
//    public Set<URL> fetchAnchorTags(Document doc) {
//        Set<URL> urls = new HashSet<>();
//        Elements links = doc.select("a[href]");
//        for (Element link : links) {
//            String path = link.attr("href");
//            urls.add(URL.builder().url(path).build());
//        }
//        return urls;
//    }
//}





package com.pricedrop.services;

import com.pricedrop.dao.PageRepository;
import com.pricedrop.model.PageInfo;
import com.pricedrop.model.URL;
import lombok.Builder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;

@Builder
@Service
public class URLProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(URLProcessor.class);

    @Autowired
    private Retrofit retrofit;

    @Autowired
    private PageRepository pageRepository;

    @Async
    public void process(String url, String urlId) {
        try {
            Document doc = Jsoup.connect(url).get();
            Set<URL> urls = fetchAnchorTags(doc);
            PageInfo pageInfo = fetchMetaInformation(doc, urlId, url);
            LOG.info("PageInfo: {}", pageInfo);
            pageRepository.save(pageInfo);

        } catch (IOException ex) {
            LOG.error("Exception: ", ex);
        }
    }

    private PageInfo fetchMetaInformation(Document doc, String urlId, String url) {
        // Extract new elements
        Element titleElement = doc.selectFirst("#productTitle");
        Element unavailableElement = doc.selectFirst("span.a-size-medium.a-color-success");
        Element wholePriceElement = doc.selectFirst(".a-price.aok-align-center.reinventPricePriceToPayMargin.priceToPay span[aria-hidden='true'] .a-price-whole");
        Element fractionPriceElement = doc.selectFirst(".a-price .a-price-fraction");
        Element imageElement = doc.selectFirst("#landingImage");
       // Elements reviews = doc.select(".card-padding"); // Select elements with class "card-padding"
        List<String> reviewList = extractReviews(doc);


        // Extract values safely
        String title = (titleElement != null) ? titleElement.text().trim() : "N/A";
        String availability = (unavailableElement != null) ? unavailableElement.text().trim() : "Available";
        String wholePrice = (wholePriceElement != null) ? wholePriceElement.text().trim() : "0";
        String fractionPrice = (fractionPriceElement != null) ? fractionPriceElement.text().trim() : "00";
        String imageUrl = (imageElement != null) ? imageElement.attr("src") : "N/A";

        String fullPrice = wholePrice + "." + fractionPrice; // Combine whole and fraction

        LOG.info("Extracted Product Details - Title: {}, Availability: {}, Price: {}, Image: {}",
                title, availability, fullPrice, imageUrl);

        return PageInfo.builder()
                //.id(UUID.randomUUID().toString())
                .pageId(UUID.randomUUID().toString())
                //.urlId(urlId)
                .productUrlId(urlId)
                //.url(url)
                .productUrl(url)
                .productName(title)
                //.description(description(doc))
                .category(description(doc))
                //.body(body(doc))
                //.keywords(keywords(doc))
                .createdTime(new Date())
               // .price(fullPrice)  // Store price
                .productPrice(fullPrice)  // Store price
                //.availability(availability)
                .productAvailability(availability)
                //.imageUrl(imageUrl) // Store image URL
                .productImageUrl(imageUrl) // Store image URL
                .reviews(reviewList)
                .build();
    }

    private String body(Document doc) {
        return doc.body().text();
    }

//    private String description(Document doc) {
//        Elements ele = doc.select("meta[name=description]");
//        return (!ele.isEmpty()) ? ele.first().attr("content") : null;
//    }

    private String description(Document doc) {
        Elements ele = doc.select("meta[name=description]");
        if (!ele.isEmpty()) {
            String fullDescription = ele.first().attr("content");
            return extractCategory(fullDescription); // Extract only category
        }
        return "Unknown";
    }

    private String extractCategory(String description) {
        if (description != null && description.contains(":")) {
            String[] parts = description.split(":");
            return parts[parts.length - 1].trim(); // Get the last word after the last colon
        }
        return "Unknown"; // Default if category is missing
    }

    private List<String> keywords(Document doc) {
        Elements ele = doc.select("meta[name=keywords]");
        return (!ele.isEmpty()) ? Arrays.asList(ele.first().attr("content").split(",")) : Collections.emptyList();
    }

    public Set<URL> fetchAnchorTags(Document doc) {
        Set<URL> urls = new HashSet<>();
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String path = link.attr("href");
            urls.add(URL.builder().url(path).build());
        }
        return urls;
    }
    private List<String> extractReviews(Document doc) {
        List<String> reviewList = new ArrayList<>();
        Elements reviews = doc.select(".card-padding"); // Select elements with class "card-padding"

        for (Element review : reviews) {
            String reviewText = review.text().trim();
            if (!reviewText.isEmpty()) {
                reviewList.add(reviewText);
            }
        }
        return reviewList;
    }

}
