package com.james.musician.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@SuppressWarnings("deprecation")

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.antMatchers(HttpMethod.GET, "/secured/**").hasRole("ADMIN")
//		.anyRequest().authenticated();
//	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/**").permitAll().anyRequest().anonymous();
	
		http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
	}
	
}
