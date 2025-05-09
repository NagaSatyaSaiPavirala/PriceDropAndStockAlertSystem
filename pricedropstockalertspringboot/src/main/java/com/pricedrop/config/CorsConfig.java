//package com.pricedrop.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:3000")
//                       // .allowedOriginPatterns("*") // Use allowedOriginPatterns to support wildcards with credentials
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
//}



package com.pricedrop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:3000", "http://localhost:8080")
//                        // Use allowedOriginPatterns if you want to allow wildcard subdomains:
//                        // .allowedOriginPatterns("http://localhost:*")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);  // Allows cookies/session
//            }
//        };
//    }
//}




@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        //.allowedOriginPatterns("*")// 🔁 Accept all origins including from NGINX
                        .allowedOrigins("http://localhost:3000", "http://localhost:8080", "http://10.21.4.56:3000","http://10.21.4.56:8080","http://pdsa.iittp.ac.in:3000","http://pdsa.iittp.ac.in:8080","http://pdsa.iittp.ac.in")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // 🔁 Allow cookies (for session/auth)
            }
        };
    }
}
