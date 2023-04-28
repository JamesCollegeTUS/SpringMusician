package com.james.musician.hystrixtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.james.musician.dao.MusicianRepository;
import com.james.musician.model.Musician;
import com.james.musician.utils.SleepUtils;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
public class MusicianDAOHystrix {

	@Autowired
	MusicianRepository musicianRepo;

	@HystrixCommand(fallbackMethod = "getIdFallbackMethod", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3") })
	public Musician getMusicianByIdDelay(@PathVariable(value = "id") Long id,
			@PathVariable(value = "delayMs") int delayMs) {
		Musician musician;
		try {
			System.err.println("In Hystrix method");
			SleepUtils.sleep(delayMs);
			musician = musicianRepo.findById(id).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Musician not found");
		}

		return musician;
	}

	public Musician getIdFallbackMethod(@PathVariable(value = "id") Long id,
			@PathVariable(value = "delayMs") int delayMs) {
		System.err.println("In fallback method");
		return new Musician(id, "Alexei Sayle");
	}



}
