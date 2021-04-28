package com.group4.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class GatewayApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(GatewayApplication.class, args);
//    }
    public static void main(String[] args) {
        WebsocketClient websocketClient = new WebsocketClient("wss://iotnet.cibicom.dk/app?token=vnoTwwAAABFpb3RuZXQuY2liaWNvbS5ka8Jer376b_vS6G62ZSL3pMU=");
    }
}
