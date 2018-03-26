package com.jhon.rain.admin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class HouseAdminServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(HouseAdminServerApplication.class, args);
  }
}
