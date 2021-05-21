package com.group4.gateway;

import com.group4.gateway.repositories.lorawan.LoRaWan;
import com.group4.gateway.services.GatewayService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
new GatewayService();
    }

}
