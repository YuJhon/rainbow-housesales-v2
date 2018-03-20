package com.jhon.rain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HouseEurekaServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(HouseEurekaServerApplication.class, args);
  }
}
