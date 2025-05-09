package com.pricedrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.pricedrop.services.ProductCheckPrice;
//import javax.annotation.PostConstruct;

@Component
public class ObserverInitializer {

    @Autowired
    private ProductCheckPrice productCheckPrice;

    @Autowired
    private EmailService emailService;

    @EventListener(ContextRefreshedEvent.class)
    public void initObservers() {
        productCheckPrice.addObserver(emailService);
    }
}
