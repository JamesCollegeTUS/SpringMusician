package com.james.musician.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.james.musician.model.Musician;
import com.james.musician.service.MusicianService;

@RestController
@RequestMapping("/api/musicians")
public class MusicianController {

	@Autowired
	MusicianService musicianService;
	
	@GetMapping
	public ResponseEntity getAllMusicians() {
		ArrayList<Musician> musicians = (ArrayList<Musician>) musicianService.getAllMusicians();
		if (musicians.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(musicians);
		} else {
			return (ResponseEntity) ResponseEntity.status(HttpStatus.OK).body(musicians);
		}
	}
}
