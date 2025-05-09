//for sql
//package com.pricedrop.resources;
//import com.pricedrop.model.PageInfo;
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
//@RestController
//public class URLResource {
//    private static final Logger LOG= LoggerFactory.getLogger(URLResource.class);
//@Autowired
//private URLService urlService;
//    @GetMapping("/ping")
//    public String ping()
//    {
////        URL u=new URL();
////        u.
//        return "pong from pricedrop";
//    }
//    @GetMapping("/kafka/{id}")
//    public ResponseEntity<URL> getQueue(@PathVariable String id) {
//        Optional<URL> opt = urlService.getq(id);
//        if (opt.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(opt.get());
//    }
//    @GetMapping("/mongo/{urlId}")
//    public ResponseEntity<PageInfo> getMongo(@PathVariable String urlId) {
//        Optional<PageInfo> opt = urlService.getm(urlId);
//        if (opt.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(opt.get());
//    }
//    @PostMapping("/batch")
//    public ResponseEntity<Void> submitBatchURL(@RequestBody Set<URL> urls) {
//        long startTime = System.currentTimeMillis();
//        LOG.info("Batch request received: {}", urls);
//        urls.forEach(u -> {
//            u.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
//            u.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//            u.setTimesProcessed(0);
//        });
//        urlService.save(urls);
//        LOG.info("Request processed in {} mills", (System.currentTimeMillis() - startTime));
//        return ResponseEntity.ok().build();
//    }
//
//    /*
//    @PostMapping
//    public ResponseEntity<Void> submitURL(@RequestBody String url) {
//        System.out.println(url);
//        return ResponseEntity.ok().build();
//    }
//     */
//    @PostMapping
//    public ResponseEntity<URL> submitURL(@RequestBody URL url) {
//        long startTime=System.currentTimeMillis();
//        url.setId(Constants.URL_UUID_PREFIX +UUID.randomUUID().toString());
//        url.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//        url.setTimesProcessed(0);
//       // urlService.save(url);
//        LOG.info("URL received:{}",url);
//        //System.out.println(url);
//        urlService.save(new HashSet<>(){{
//            add(url);
//        }});
//LOG.info("Request processed in {} mills",(System.currentTimeMillis()-startTime));
//       // return ResponseEntity.ok().build(); //public ResponseEntity<Void> submitURL(@RequestBody URL url) {
//        return ResponseEntity.ok(url); //public ResponseEntity<URL> submitURL(@RequestBody URL url) {
//    }
//
//}




package com.pricedrop.resources;
import com.pricedrop.model.PageInfo;
import com.pricedrop.model.URL;
import com.pricedrop.common.Constants;
import com.pricedrop.services.URLService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.sql.Timestamp;



@RestController
public class URLResource {
    private static final Logger LOG= LoggerFactory.getLogger(URLResource.class);
    @Autowired
    private URLService urlService;
    @GetMapping("/ping")
    public String ping()
    {
//        URL u=new URL();
//        u.
        return "pong from pricedrop";
    }
    @GetMapping("/kafka/{id}")
    public ResponseEntity<URL> getQueue(@PathVariable String id) {
        Optional<URL> opt = urlService.getq(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(opt.get());
    }
    @GetMapping("/mongo/{urlId}")
    public ResponseEntity<PageInfo> getMongo(@PathVariable String urlId) {
        Optional<PageInfo> opt = urlService.getm(urlId);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(opt.get());
    }
    @PostMapping("/batch")
    public ResponseEntity<Void> submitBatchURL(@RequestBody Set<URL> urls) {
        long startTime = System.currentTimeMillis();
        LOG.info("Batch request received: {}", urls);
        urls.forEach(u -> {
            u.setId(Constants.URL_UUID_PREFIX + UUID.randomUUID().toString());
            //u.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            u.setCreatedDate(Instant.now()); //  Correct
            u.setTimesProcessed(0);
        });
        urlService.save(urls);
        LOG.info("Request processed in {} mills", (System.currentTimeMillis() - startTime));
        return ResponseEntity.ok().build();
    }

    /*
    @PostMapping
    public ResponseEntity<Void> submitURL(@RequestBody String url) {
        System.out.println(url);
        return ResponseEntity.ok().build();
    }
     */
    @PostMapping
    public ResponseEntity<URL> submitURL(@RequestBody URL url) {
        long startTime=System.currentTimeMillis();
        url.setId(Constants.URL_UUID_PREFIX +UUID.randomUUID().toString());
        //url.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        url.setCreatedDate(Instant.now()); // Correct

        url.setTimesProcessed(0);
        // urlService.save(url);
        LOG.info("URL received:{}",url);
        //System.out.println(url);
        urlService.save(new HashSet<>(){{
            add(url);
        }});
        LOG.info("Request processed in {} mills",(System.currentTimeMillis()-startTime));
        // return ResponseEntity.ok().build(); //public ResponseEntity<Void> submitURL(@RequestBody URL url) {
        return ResponseEntity.ok(url); //public ResponseEntity<URL> submitURL(@RequestBody URL url) {
    }

}
