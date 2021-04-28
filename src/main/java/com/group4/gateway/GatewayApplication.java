package com.group4.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class GatewayApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(GatewayApplication.class, args);
//    }
    public static void main(String[] args) {
        WebsocketClient websocketClient = new WebsocketClient();
        websocketClient.sendDownLink("{cmd: 'tx', EUI: '0004A30B0021B92F', port: 1, confirmed: true, data: 'test text'}");

        while (true) {

        }
    }
}
