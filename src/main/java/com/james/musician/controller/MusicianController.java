package com.james.musician.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.james.musician.dao.MusicianRepository;
import com.james.musician.errors.ErrorMessage;
import com.james.musician.exceptions.MusicianException;
import com.james.musician.message.AddResponse;
import com.james.musician.model.Musician;
import com.james.musician.service.MusicianService;

@RestController
@RequestMapping("/api/musicians")
public class MusicianController {

	@Autowired
	MusicianService musicianService;
	
	@Autowired
	MusicianRepository musicianRepo;
	
	@GetMapping
	public ResponseEntity getAllMusicians() {
		ArrayList<Musician> musicians = (ArrayList<Musician>) musicianService.getAllMusicians();
		if (musicians.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(musicians);
		} else {
			return (ResponseEntity) ResponseEntity.status(HttpStatus.OK).body(musicians);
		}
	}
	
	@GetMapping("/{id}")
	public Musician getMusicianById(@PathVariable(value = "id") Long id) {
		Musician musician;
		
		try {
			musician = musicianService.getMusicianById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Musician not found");
		}
		
		return musician;
	}
//	

	@PostMapping      
	public ResponseEntity addMusician(@RequestBody Musician musician) {
		try {
			Musician savedMusician = musicianService.createMusician(musician);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedMusician);
			
		} catch (MusicianException e) {
			ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteMusician(@PathVariable("id") Long id) {
		try {
			musicianService.deleteMusician(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateMusician(@PathVariable("id") Long id, @RequestBody Musician musician) {
		AddResponse ad = new AddResponse();
		String longIdToString = id.toString();
		
		if(musicianService.checkMusicianAlreadyExists(id)) {
			musician.setId(id);
			musicianRepo.save(musician);
			HttpHeaders headers = new HttpHeaders();
			headers.add("unique",longIdToString);
			ad.setMsg("Musician successfully updated");
			ad.setId(longIdToString);
			return new ResponseEntity<AddResponse>(ad, headers, HttpStatus.OK);
		} else {
			ad.setMsg("Musician does not exist");
			ad.setId(longIdToString);
			return new ResponseEntity<AddResponse>(ad, HttpStatus.NOT_FOUND);
		}
		
	}
}
