package com.james.musician.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Lazy;

import com.james.musician.dao.MusicianRepository;
import com.james.musician.errors.ErrorMessages;
import com.james.musician.errors.ErrorValidation;
import com.james.musician.exceptions.MusicianValidationException;
import com.james.musician.model.Instruments;
import com.james.musician.model.MusicStyle;
import com.james.musician.model.Musician;
import com.james.musician.musicianBuilder.MusicianBuilder;

@SpringBootTest
public class MusicianServiceTest {
	
	@MockBean
	MusicianRepository musicianRepo;
	
	@Autowired
	MusicianService musicianService;
	
	MusicianBuilder musicianBuilder;
	
	ErrorValidation errorValidation;
	
	Musician musician;
	
	
	@BeforeEach
	void setUp() {
		errorValidation = new ErrorValidation();
		musicianBuilder = new MusicianBuilder();
		musician = musicianBuilder.buildMusicianWithId();
	}
	@Test
	public void checkMusicianAlreadyExistsTestTrue() {
		Optional<Musician> musOpt = Optional.of(musician);
		when(musicianRepo.findById(Mockito.anyLong())).thenReturn(musOpt);
		boolean result = musicianService.checkMusicianAlreadyExists(musician.getId());
		//some verification
		assertEquals(result, musOpt.isPresent());
	}
	
	@Test 
	public void CheckMusicianAlreadyExsistsFalse() {
		Optional<Musician> musOpt = Optional.ofNullable(null);
		when(musicianRepo.findById(musician.getId())).thenReturn(musOpt);
		boolean result = musicianService.checkMusicianAlreadyExists(musician.getId());
		//some verification
		assertEquals(result, musOpt.isPresent());
	}
	

    
	@Test
	public void TestMusicianAlreadyExistsException() throws MusicianValidationException{
		when(musicianRepo.findByEmailAddress(musician.getEmailAddress())).thenReturn(musician);
		assertThatThrownBy(() -> musicianService.isValidMusician(musician))
		.isInstanceOf(MusicianValidationException.class)
		.hasMessage("Musician already exists");
	}
	
	@Test
	public void TestInvalidFolMusicianException() throws MusicianValidationException{
		musician.setStyle(MusicStyle.FOLK);
		assertThatThrownBy(() -> musicianService.isValidMusician(musician))
		.isInstanceOf(MusicianValidationException.class)
		.hasMessage("A FOLK musician must sing AND play guitar only");
	}
	
	@Test
	public void TestCheckDuplicateInstrumentException() throws MusicianValidationException{
		musician.setInstrumentA(Instruments.BASS);
		musician.setInstrumentB(Instruments.BASS);
		assertThatThrownBy(() -> musicianService.isValidMusician(musician))
		.isInstanceOf(MusicianValidationException.class)
		.hasMessage("Cannot duplicate instruments " + "(" + Instruments.BASS +")");
	}
	
	@Test
	public void TestInstrumentComboAllowedException() throws MusicianValidationException{
		musician.setInstrumentA(Instruments.BASS);
		musician.setInstrumentB(Instruments.GUITAR);
		assertThatThrownBy(() -> musicianService.isValidMusician(musician))
		.isInstanceOf(MusicianValidationException.class)
		.hasMessage("Muscian cannot play more than one instrument"
		+ " (" + musician.getInstrumentA() + ", " + musician.getInstrumentB() + ")");
	}
	
	@Test
	public void TestIsValidMusicianTrue() throws MusicianValidationException{
		assertTrue(musicianService.isValidMusician(musician));
	}
	
	
}
