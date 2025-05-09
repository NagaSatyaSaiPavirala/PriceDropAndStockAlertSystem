package com.pricedrop.services;

import com.pricedrop.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductNotifier {
    private final List<ProductObserver> observers = new ArrayList<>();

    public void addObserver(ProductObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProductObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Product product, String eventType, Double price) {
        for (ProductObserver observer : observers) {
            observer.update(product, eventType, price);
        }
    }
}
