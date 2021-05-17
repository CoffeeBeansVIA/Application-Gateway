package com.group4.gateway;

import com.group4.gateway.repositories.lorawan.LoRaWan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        new LoRaWan();
        SpringApplication.run(GatewayApplication.class, args);
    }
}
