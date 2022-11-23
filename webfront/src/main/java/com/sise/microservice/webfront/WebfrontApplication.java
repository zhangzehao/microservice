package com.sise.microservice.webfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class WebfrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfrontApplication.class, args);
    }

}
