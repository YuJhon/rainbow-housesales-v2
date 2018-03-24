package com.jhon.rain.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HouseCommentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseCommentServiceApplication.class, args);
	}
}
