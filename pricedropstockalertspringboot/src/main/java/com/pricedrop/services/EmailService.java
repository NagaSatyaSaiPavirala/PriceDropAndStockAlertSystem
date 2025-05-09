//package com.pricedrop.services;
//
//import org.springframework.stereotype.Service;
//
//import java.util.Properties;
//
//import jdk.jfr.DataAmount;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//import java.util.Properties;
//
//@Service
//public class EmailService {
//    public EmailService() {
//    }
//
//    public boolean sendEmail(String subject, String message, String to){
//
//        try {
//            //rest of the code..
//            boolean f = false;
////            String from = "shubhamjain12368@gmail.com";
////            String password = "leuh idhd bceo ttre";
//            String from = "p.nagasatyasai.pnss@gmail.com";
//            String password = "xvda qzjf tjwp wdgi";
//            //Variable for gmail
//            String host = "smtp.gmail.com";
//
//            //get the system properties
//            Properties properties = System.getProperties();
//            System.out.println("Properties " + properties);
//
//            //setting important information to properties object
//
//            //host set
//            properties.put("mail.smtp.host", host);
//            properties.put("mail.smtp.port", "465");
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.auth", "true");
//
//            //step 1 : to get the session object ..
////        Session session = javax.mail.Session.getInstance()
//
//            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                    return new javax.mail.PasswordAuthentication(from, password);
//                }
//            });
//
//            session.setDebug(true);
//
//            //step 2 : compose the message[text, multi media]
//            MimeMessage m = new MimeMessage(session);
//
//            //from
//            m.setFrom(from);
//
//            //adding recipient to message
//            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            //adding subjrct to message
//            m.setSubject(subject);
//
//            //adding text to message
////            m.setText(message);
//            m.setContent(message,"text/html");
//
//
//            //send
//            //step 3 : send the mesaage using transport class
//            Transport.send(m);
//
//            System.out.println("sent mesaage..........");
//            return true;
//
//            //
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//}











//package com.pricedrop.services;
//import com.pricedrop.entities.Product;
//import com.pricedrop.services.ProductObserver;
//import org.springframework.stereotype.Service;
//
//import java.util.Properties;
//
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//import java.util.Properties;
//
//@Service
//public class EmailService implements ProductObserver {
//
//    @Override
//    public void update(Product product, String eventType, Double price) {
//        String email = product.getUser().getEmail();
//        if ("PRICE_DROP".equals(eventType)) {
//            sendEmail("Price is dropped !!","The price of the product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>with threshold price "+product.getT_price()+" <br>has dropped to "+price+" <br>Link to purchase product:-  "+product.getP_url(),email);
//            //boolean b = this.emailService.sendEmail("Price is dropped !!","The price of the product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>with threshold price "+product.getT_price()+" <br>has dropped to "+price+" <br>Link to purchase product:-  "+product.getP_url(),user.get().getEmail());
////            sendEmail("Price Drop Alert!",
////                    "The price of " + product.getP_name() + " dropped to " + price +
////                            ".\nPurchase here: " + product.getP_url(),
////                    email);
//        } else if ("STOCK_BACK".equals(eventType)) {
//            sendEmail("Stock is back !!","The product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>came back into stock.  <br>Link to purchase product:-  "+product.getP_url(),email);
//            //boolean b = this.emailService.sendEmail("Stock is back !!","The product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>came back into stock.  <br>Link to purchase product:-  "+product.getP_url(),user.get().getEmail());
////            sendEmail("Stock Alert!",
////                    "The product " + product.getP_name() + " is back in stock.\nPurchase here: " + product.getP_url(),
////                    email);
//        }
//    }
//
//    public boolean sendEmail(String subject, String message, String to) {
//                try {
//            //rest of the code..
//            boolean f = false;
////            String from = "shubhamjain12368@gmail.com";
////            String password = "leuh idhd bceo ttre";
//            String from = "p.nagasatyasai.pnss@gmail.com";
//            String password = "xvda qzjf tjwp wdgi";
//            //Variable for gmail
//            String host = "smtp.gmail.com";
//
//            //get the system properties
//            Properties properties = System.getProperties();
//            System.out.println("Properties " + properties);
//
//            //setting important information to properties object
//
//            //host set
//            properties.put("mail.smtp.host", host);
//            properties.put("mail.smtp.port", "465");
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.auth", "true");
//
//            //step 1 : to get the session object ..
////        Session session = javax.mail.Session.getInstance()
//
//            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                    return new javax.mail.PasswordAuthentication(from, password);
//                }
//            });
//
//            session.setDebug(true);
//
//            //step 2 : compose the message[text, multi media]
//            MimeMessage m = new MimeMessage(session);
//
//            //from
//            m.setFrom(from);
//
//            //adding recipient to message
//            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            //adding subjrct to message
//            m.setSubject(subject);
//
//            //adding text to message
////            m.setText(message);
//            m.setContent(message,"text/html");
//
//
//            //send
//            //step 3 : send the mesaage using transport class
//            Transport.send(m);
//
//            System.out.println("sent message..........");
//            return true;
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//}





package com.pricedrop.services;
import com.pricedrop.dao.UserRepository;
import com.pricedrop.entities.Product;
import com.pricedrop.services.ProductObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import com.pricedrop.entities.User;

@Service
public class EmailService implements ProductObserver {

    @Autowired
    private UserRepository userRepository;  // Inject UserRepository


    @Override
    public void update(Product product, String eventType, Double price) {
       // String email = product.getUser().getEmail();


        int userId = product.getUser_id();  // Get user_id from Product
        Optional<User> user = userRepository.findById(userId);
        String email = user.get().getEmail();

//       // String email = product.getUser_id().getEmail();
//        int userId = product.getUser_id();  //  Get user ID from product
//
//        // Fetch user by ID
//        User user = userRepository.findById(userId).orElse(null);


        if ("PRICE_DROP".equals(eventType)) {
            sendEmail("Price is dropped !!","The price of the product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>with threshold price "+product.getT_price()+" <br>has dropped to "+price+" <br>Link to purchase product:-  "+product.getP_url(),email);
            //boolean b = this.emailService.sendEmail("Price is dropped !!","The price of the product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>with threshold price "+product.getT_price()+" <br>has dropped to "+price+" <br>Link to purchase product:-  "+product.getP_url(),user.get().getEmail());
//            sendEmail("Price Drop Alert!",
//                    "The price of " + product.getP_name() + " dropped to " + price +
//                            ".\nPurchase here: " + product.getP_url(),
//                    email);
        } else if ("STOCK_BACK".equals(eventType)) {
            sendEmail("Stock is back !!","The product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>came back into stock.  <br>Link to purchase product:-  "+product.getP_url(),email);
            //boolean b = this.emailService.sendEmail("Stock is back !!","The product<br> "+UrlCoding.extractProductName(product.getP_name())+" <br>came back into stock.  <br>Link to purchase product:-  "+product.getP_url(),user.get().getEmail());
//            sendEmail("Stock Alert!",
//                    "The product " + product.getP_name() + " is back in stock.\nPurchase here: " + product.getP_url(),
//                    email);
        }
    }

    public boolean sendEmail(String subject, String message, String to) {
        try {
            //rest of the code..
            boolean f = false;
//            String from = "shubhamjain12368@gmail.com";
//            String password = "leuh idhd bceo ttre";
            String from = "p.nagasatyasai.pnss@gmail.com";
            String password = "xvda qzjf tjwp wdgi";
            //Variable for gmail
            String host = "smtp.gmail.com";

            //get the system properties
            Properties properties = System.getProperties();
            System.out.println("Properties " + properties);

            //setting important information to properties object

            //host set
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            //step 1 : to get the session object ..
//        Session session = javax.mail.Session.getInstance()

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(from, password);
                }
            });

            session.setDebug(true);

            //step 2 : compose the message[text, multi media]
            MimeMessage m = new MimeMessage(session);

            //from
            m.setFrom(from);

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subjrct to message
            m.setSubject(subject);

            //adding text to message
//            m.setText(message);
            m.setContent(message,"text/html");


            //send
            //step 3 : send the mesaage using transport class
            Transport.send(m);

            System.out.println("sent message..........");
            return true;


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}