package com.jhon.rain.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroHouseUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroHouseUserApplication.class, args);
  }
}
