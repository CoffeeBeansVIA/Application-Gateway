package com.group4.gateway;

import com.group4.gateway.utils.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Test {
    @Autowired
    private ApplicationProperties applicationProperties;
    @PostConstruct
    public void init() {
        System.out.println("sd");
        System.out.println(applicationProperties.getLoRaWanToken());
    }
}
