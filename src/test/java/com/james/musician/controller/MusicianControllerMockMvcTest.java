package com.james.musician.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
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
	
	@Captor
	ArgumentCaptor<Long> longCaptor;
	
	

	@Test
	public void addMusicianControllerTestSuccess() throws Exception{
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
		Musician finalMusician = captor.getValue();
		assertEquals(finalMusician.getEmailAddress(),savedMusician.getEmailAddress());
		}
	
	@Test
	public void addMusicianControllerException() throws Exception{
		Musician musician = musicianBuilder.buildMusician(); 
		Musician savedMusician = musicianBuilder.buildMusician();
		savedMusician.setId(1L); 
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(savedMusician);

		when(musicianService.isValidMusician(musician)).thenThrow(MusicianValidationException.class);
		
		this.mockMvc.perform(post("/api/musicians").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonString)).andDo(print()).andExpect(status().isBadRequest());

	}
	
	@Test
	public void getAllMusiciansNotNull() throws Exception{
		Musician musician1 = musicianBuilder.buildMusician(); 
		Musician musician2 = musicianBuilder.buildMusician(); 
		Musician musician3 = musicianBuilder.buildMusician();
		ArrayList<Musician> musicians = new ArrayList<>();
		musicians.add(musician1);
		musicians.add(musician2);
		musicians.add(musician3);

		
		when(musicianRepo.findAll()).thenReturn(musicians);
		this.mockMvc.perform(get("/api/musicians")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.length()",is(3)));
}
	
	@Test
	public void getAllMusiciansNull() throws Exception{
		ArrayList<Musician> musicians = new ArrayList<>();
		when(musicianRepo.findAll()).thenReturn(musicians);
		this.mockMvc.perform(get("/api/musicians")).andDo(print()).andExpect(status().isNoContent())
		.andExpect(jsonPath("$.length()",is(0)));
	}
	
	@Test 
	public void getMusicianByIdFound() throws Exception{
		Musician musician = musicianBuilder.buildMusicianWithId();
		when(musicianRepo.findById(musician.getId())).thenReturn(Optional.of(musician));
		this.mockMvc.perform(get("/api/musicians/" + musician.getId())).andDo(print()).andExpect(status().isOk());
		
		
	
	} 
	
	@Test
	public void getMusicianByIdNotFound() throws Exception{
		Musician musician = musicianBuilder.buildMusicianWithId();
		when(musicianRepo.findById(musician.getId())).thenReturn(null);
		this.mockMvc.perform(get("/api/musicians/" + musician.getId())).andDo(print()).andExpect(status().isNotFound())
		.andExpect(status().reason(containsString("Musician not found")));

		
	}
	
	@Test
	public void deleteMusician() throws Exception{
		Musician musician = musicianBuilder.buildMusicianWithId();
		when(musicianRepo.findById(musician.getId())).thenReturn(Optional.of(musician));
		this.mockMvc.perform(delete("/api/musicians/" + musician.getId())).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(musician.getId()))
		.andExpect(jsonPath("$.msg").value("Musician Deleted")); 
		
	} 
	@Test
	public void deleteMusicianNotFound() throws Exception{
		Musician musician = musicianBuilder.buildMusicianWithId();
		when(musicianRepo.findById(musician.getId())).thenReturn(null);
		this.mockMvc.perform(delete("/api/musicians/" + musician.getId())).andDo(print()).andExpect(status().isNotFound())
		.andExpect(status().reason(containsString("Musician not found"))); 
		   
	}
	
	@Test
	public void updateMusician() throws Exception{
		Musician musician = musicianBuilder.updateMusician();
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(musician);
		when(musicianService.checkMusicianAlreadyExists(musician.getId())).thenReturn(true);
		when(musicianRepo.save(musician)).thenReturn(musician);
		this.mockMvc.perform(put("/api/musicians/" + musician.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.content(jsonString)).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(musician.getId()))
		.andExpect(jsonPath("$.msg").value("Musician successfully updated"));  
		 
		
	} 
	@Test
	public void updateMusicianNotFound() throws Exception{
		Musician musician = musicianBuilder.updateMusician();
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(musician);
		when(musicianService.checkMusicianAlreadyExists(musician.getId())).thenReturn(false);
		this.mockMvc.perform(put("/api/musicians/" + musician.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.content(jsonString)).andDo(print()).andExpect(status().isNotFound())
		.andExpect(jsonPath("$.id").value(musician.getId()))
		.andExpect(jsonPath("$.msg").value("Musician does not exist"));  
		 
		
	} 
	
}