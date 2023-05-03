package com.james.musician;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableResourceServer
public class SpringMusicianApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMusicianApplication.class, args);

	}

}
