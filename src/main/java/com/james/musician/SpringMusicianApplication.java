package com.james.musician;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class SpringMusicianApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMusicianApplication.class, args);

	}

}
