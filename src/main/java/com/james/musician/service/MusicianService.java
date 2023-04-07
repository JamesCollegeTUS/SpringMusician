package com.james.musician.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.james.musician.dao.MusicianRepository;
import com.james.musician.errors.ErrorMessages;
import com.james.musician.errors.ErrorValidation;
import com.james.musician.exceptions.MusicianNotFoundException;
import com.james.musician.exceptions.MusicianValidationException;
import com.james.musician.model.MusicStyle;
import com.james.musician.model.Musician;

import java.util.Optional;


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
		this.musician = musician;
		
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
		return musicianRepo.save(musician);
	}
	
	public void deleteMusician(Long id) throws MusicianNotFoundException{
		try {
			Musician musician = musicianRepo.findById(id).get();
			musicianRepo.delete(musician);
		}catch(Exception e){
			throw new MusicianNotFoundException("Musician not found");
		}
	}
	
	public boolean checkMusicianAlreadyExists(Long id) {
		Optional<Musician> mus = musicianRepo.findById(id);
		if(mus.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	
}
