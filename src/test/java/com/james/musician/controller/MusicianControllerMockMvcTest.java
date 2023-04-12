package com.james.musician.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.musician.dao.MusicianRepository;
import com.james.musician.errors.ErrorMessage;
import com.james.musician.errors.ErrorMessages;
import com.james.musician.errors.ErrorValidation;
import com.james.musician.exceptions.MusicianValidationException;
import com.james.musician.model.Instruments;
import com.james.musician.model.MusicStyle;
import com.james.musician.model.Musician;
import com.james.musician.musicianBuilder.MusicianBuilder;
import com.james.musician.service.MusicianService;

@SpringBootTest
@AutoConfigureMockMvc
class MusicianControllerMockMvcTest {

	@Autowired
	MusicianController musicianController;
	
	@MockBean
	MusicianRepository musicianRepo;
	
	@MockBean
	MusicianService musicianService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired 
	MusicianBuilder musicianBuilder;
	
	@Autowired
	ErrorValidation errorValidation;
	
	@Captor
	ArgumentCaptor<Musician> captor;

	@Test
	public void addMusicianControllerTest() throws Exception{
		Musician musician = musicianBuilder.buildMusician(); 
		Musician savedMusician = musicianBuilder.buildMusician();
		savedMusician.setId(1L); 
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(savedMusician);
		
		when(musicianService.isValidMusician(musician)).thenReturn(true);
		when(musicianRepo.save(any())).thenReturn(savedMusician);
		
		this.mockMvc.perform(post("/api/musicians").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonString)).andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(savedMusician.getId()))
				.andExpect(jsonPath("$.msg").value("Musician Added Successfully")); 
		
		verify(musicianRepo, new Times(1)).save(captor.capture()); 
	//	verify(musicianService, new Times(1)).createMusician(captor.capture());
		Musician finalMusician = captor.getValue();
		assertEquals(finalMusician.getEmailAddress(),savedMusician.getEmailAddress());
		
		 
	}
	
 
	

	
	


}
