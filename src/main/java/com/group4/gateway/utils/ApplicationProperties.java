package com.group4.gateway.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {
    @Value("${lorawan.URL}")
    private String LoRaWanURL;
    @Value("${lorawan.TOKEN}")
    private String LoRaWanToken;

    public String getLoRaWanURL() {
        return LoRaWanURL;
    }

    public String getLoRaWanToken() {
        return LoRaWanToken;
    }
}
