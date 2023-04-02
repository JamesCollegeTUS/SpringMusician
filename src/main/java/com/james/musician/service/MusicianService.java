package com.james.musician.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.james.musician.dao.MusicianRepository;
import com.james.musician.model.Musician;

@RestController
@Service
public class MusicianService {
	
	@Autowired
	MusicianRepository musicianRepo;
	
	public List<Musician> getAllMusicians(){
		return musicianRepo.findAll();
	}

}
