package com.pricedrop.services;

import com.pricedrop.entities.Product;

public interface ProductObserver {
    void update(Product product, String eventType, Double price);
}
