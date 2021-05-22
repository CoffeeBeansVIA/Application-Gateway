package com.group4.gateway;

import com.group4.gateway.repositories.lorawan.LoRaWanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class AppStart {
    @Autowired
    private LoRaWanImpl loRaWanImpl;

    @PostConstruct
    public void start() {
        loRaWanImpl.init();
    }
}
