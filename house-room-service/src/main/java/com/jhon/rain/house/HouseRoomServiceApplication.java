package com.jhon.rain.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HouseRoomServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HouseRoomServiceApplication.class, args);
  }
}
