package com.james.musician.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.james.musician.dao.MusicianRepository;
import com.james.musician.errors.ErrorMessages;
import com.james.musician.errors.ErrorValidation;
import com.james.musician.exceptions.MusicianValidationException;
import com.james.musician.model.MusicStyle;
import com.james.musician.model.Musician;

import java.util.Optional;

@Component
@Service
public class MusicianService {

	
	@Autowired
	MusicianRepository musicianRepo;
	
	Musician musician;
	
	@Autowired
	ErrorValidation errorValidation;
	


	 
	public boolean isValidMusician(Musician musician) throws MusicianValidationException{
		this.musician = musician;
		
		// use email address to check if musician already exists 
		if(musicianRepo.findByEmailAddress(musician.getEmailAddress()) != null) {
			throw new MusicianValidationException(ErrorMessages.MUSISIAN_ALREADY_EXISTS.getMsg());
			
		}
		
		if(musician.getStyle().equals(MusicStyle.FOLK)) {
			if(!errorValidation.checkValidFolkMusician(musician)) {
				throw new MusicianValidationException(ErrorMessages.INVALID_FOLK_MUSICIAN.getMsg());
			}
		}
		if(errorValidation.checkDuplicateInstrument(musician)) {
			throw new MusicianValidationException(ErrorMessages.DUPLICATE_INSTRUMENTS.getMsg()
					+ " (" + musician.getInstrumentA() + ")");
		}
		if(!errorValidation.checkInstrumentComboAllowed(musician)) {
			throw new MusicianValidationException(ErrorMessages.INVALID_INSTRUMENT_COMBO.getMsg()
				+ " (" + musician.getInstrumentA() + ", " + musician.getInstrumentB() + ")"	);
		}
		
		
		return true;
	} 
	
	
	public boolean checkMusicianAlreadyExists(Long id) {
		Optional<Musician> mus = musicianRepo.findById(id);
		System.out.println("In service > mus.isEmpty(): " + mus.isEmpty() + ", mus.isPresent():" + mus.isPresent());
		if(mus.isPresent()) {
			return true; 
		} else {
			return false;    
		}
	}

	
}
