package com.james.musician.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.james.musician.dao.MusicianRepository;
import com.james.musician.errors.ErrorValidation;
import com.james.musician.exceptions.MusicianValidationException;
import com.james.musician.model.Musician;


@Service
public class MusicianService {
	Musician musician;
	@Autowired
	MusicianRepository musicianRepo;
	
	@Autowired
	ErrorValidation errorValidation;
	
	public List<Musician> getAllMusicians(){
		return musicianRepo.findAll();
	}

	public Musician getMusicianById(long id) {
	
		return musicianRepo.findById(id).get();
	}

	public Musician createMusician(Musician musician) throws MusicianValidationException{
		// TODO Auto-generated method stub
		return null;
	}
	
}
