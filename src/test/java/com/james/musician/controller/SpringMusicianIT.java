package com.james.musician.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.james.musician.dao.MusicianRepository;
import com.james.musician.model.Musician;
import com.james.musician.musicianBuilder.MusicianBuilder;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringMusicianIT {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	MusicianRepository musicianRepo;
	
	//@Value(value="${local.server.port}")
	@LocalServerPort
	private static int port = 8095;
	
	MusicianBuilder musicianBuilder;
	
	@BeforeEach
	void setUp() {
		musicianBuilder = new MusicianBuilder();
	}
	
	private static final String DELETE_EMPLOYEE_ENDPOINT_URL = "http://localhost:"+port+"/api/musicians/{id}";
	@Test
	public void addMusicianIntegrationTest() {
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Musician> request = new HttpEntity<Musician>(musicianBuilder.buildMusicianWithId(),headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:"+port+"/api/musicians", request, String.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(musicianBuilder.buildMusicianWithId().getId().toString(), response.getHeaders().get("unique").get(0));
	}
	
    @Test
   // @Sql({"/testdata.sql"})
    public void deleteMusicianIntegrationTest() {
    	Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");
		TestRestTemplate restTemplate = new TestRestTemplate();
		restTemplate.delete(DELETE_EMPLOYEE_ENDPOINT_URL, params);
    }
}
