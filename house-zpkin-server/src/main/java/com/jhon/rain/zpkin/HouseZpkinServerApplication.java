package com.jhon.rain.zpkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class HouseZpkinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseZpkinServerApplication.class, args);
	}
}
