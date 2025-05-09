package com.pricedrop.model;

public class ProductDetails {
    private String productName;
    private String productImageUrl;

    public ProductDetails(String productName, String productImageUrl) {
        this.productName = productName;
        this.productImageUrl = productImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }
}
