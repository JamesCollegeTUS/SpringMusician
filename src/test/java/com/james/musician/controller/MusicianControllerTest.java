package com.james.musician.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.james.musician.dao.MusicianRepository;
import com.james.musician.errors.ErrorMessage;
import com.james.musician.errors.ErrorMessages;
import com.james.musician.exceptions.MusicianValidationException;
import com.james.musician.model.Instruments;
import com.james.musician.model.MusicStyle;
import com.james.musician.model.Musician;
import com.james.musician.musicianBuilder.MusicianBuilder;
import com.james.musician.service.MusicianService;

@SpringBootTest
public class MusicianControllerTest {

	@Autowired
	MusicianController musicianController;
	
	MusicianBuilder musicianBuilder;
	
	@MockBean 
	MusicianService musicianService;
	
	@MockBean
	MusicianRepository musicianRepo;
	
//	@Test
	public void addMusicianSuccessTest() throws MusicianValidationException {
		musicianBuilder = new MusicianBuilder();
		Musician musician = musicianBuilder.buildMusician();
		Musician savedMusician = musicianBuilder.buildMusician();
		savedMusician.setId(1L);
		when(musicianService.isValidMusician(musician)).thenReturn(true);
		when(musicianRepo.save(any())).thenReturn(savedMusician);
		ResponseEntity response = musicianController.addMusician(savedMusician);
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
//		Musician musicianAdded = (Musician) response.getBody();
//		musicianAdded.getId();
//		assertEquals(1L,musicianAdded.getId());
//		assertTrue(musicianAdded.equals(savedMusician));
	}
	
//	@Test
	public void addMusicianFailedTest() throws MusicianValidationException {
		musicianBuilder = new MusicianBuilder();
		Musician musician = musicianBuilder.buildMusician();
		when(musicianService.isValidMusician(musician))
		.thenThrow(new MusicianValidationException(ErrorMessages.MUSISIAN_ALREADY_EXISTS.getMsg()));
		ResponseEntity response = musicianController.addMusician(musician);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		ErrorMessage errorMsg = (ErrorMessage) response.getBody();
		assertEquals(ErrorMessages.MUSISIAN_ALREADY_EXISTS.getMsg(), errorMsg.getErrorMessage());
		
	}
	
//	@Test
	public void getAllMusiciansTestNotNullList() {
		//create 3 musicians
		musicianBuilder = new MusicianBuilder();
		Musician musician1 = musicianBuilder.buildMusician();
		Musician musician2 = musicianBuilder.buildMusician();
		Musician musician3 = musicianBuilder.buildMusician();
		
		// mock the arraylist returned from musicianService method getAllMusicians()
		ArrayList<Musician> musicians = new ArrayList<>();
		musicians.add(musician1);
		musicians.add(musician2);
		musicians.add(musician3);
		// mock musician service returning a list of musicians
		//when(musicianService.getAllMusicians()).thenReturn(musicians);
		when(musicianRepo.findAll()).thenReturn(musicians);
		ResponseEntity response = musicianController.getAllMusicians();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
//	@Test
	public void getAllMusiciansTestNullList() {
		ArrayList<Musician> musicians = new ArrayList<>();
		//when(musicianService.getAllMusicians()).thenReturn(musicians);
		when(musicianRepo.findAll()).thenReturn(musicians);
		ResponseEntity response = musicianController.getAllMusicians();
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void getMusicianByIdFound() {
		musicianBuilder = new MusicianBuilder();
		Musician musician = musicianBuilder.buildMusicianWithId();
		
		when(musicianRepo.findById(musician.getId())).thenReturn(Optional.of(musician));
		Musician returnedMusician = musicianController.getMusicianById(1L);
		assertEquals(musician.getId(), returnedMusician.getId()); 
		
	}
	
//	@Test
	public void getMusicianByIdNotFound() {
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
				() -> {musicianController.getMusicianById(null);});
	
		//System.out.println("message from exception: " + exception.getMessage());
		assertTrue(exception.getMessage().contains("Musician not found"));
	}
	

	

	
}
