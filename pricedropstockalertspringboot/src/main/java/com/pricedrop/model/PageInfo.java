package com.pricedrop.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@Document(collection = "html_page")
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {

    @Id
    //private String id;
    private String pageId;

    @Field(name = "url_id")
    //private String urlId;
    private String productUrlId;

    //private String url;
    private String productUrl;

   // private String title;
   private String productName;
    //    private String description;
    private String category;

    //private String price;        // Add this field
    private String productPrice;        // Add this field
    //private String availability; // Add this field
    private String productAvailability; // Add this field

   // private String imageUrl;
   private String productImageUrl;

    //private List<String> keywords;




    //private String body;
    private List<String> reviews;


    @Field(name = "created_time")
    private Date createdTime;
}
