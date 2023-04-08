package com.james.musician.errors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.james.musician.model.Instruments;
import com.james.musician.model.MusicStyle;
import com.james.musician.model.Musician;

class ErrorValidationTest {
	
	ErrorValidation errorValidation;
	Musician musician;
	
	@BeforeEach
	void setUp() {
		errorValidation = new ErrorValidation();
		musician = buildMusician();
	}


	
	@Test
	void testNullInstrumentBTrue() {
		musician.setInstrumentB(null);
		assertTrue(errorValidation.checkInstrumentComboAllowed(musician));
	}
	
	@Test
	void testVocalsAndGuitarTrue() {
		musician.setInstrumentA(Instruments.VOCALS);
		musician.setInstrumentB(Instruments.GUITAR);
		assertTrue(errorValidation.checkInstrumentComboAllowed(musician));
	}
	
	@Test
	void testVocalsAndDrumsTrue() {
		musician.setInstrumentA(Instruments.VOCALS);
		musician.setInstrumentB(Instruments.DRUMS);
		assertTrue(errorValidation.checkInstrumentComboAllowed(musician));
	}
	
	@Test
	void testVocalsAndBassTrue() {
		musician.setInstrumentA(Instruments.VOCALS);
		musician.setInstrumentB(Instruments.BASS);
		assertTrue(errorValidation.checkInstrumentComboAllowed(musician));
	}
	
	@Test
	void testGuitarAndVocalsTrue() {
		musician.setInstrumentA(Instruments.GUITAR);	
		musician.setInstrumentB(Instruments.VOCALS);
		assertTrue(errorValidation.checkInstrumentComboAllowed(musician));
	}
	
	@Test
	void testDrumsAndVocalsTrue() {
		musician.setInstrumentA(Instruments.DRUMS);	
		musician.setInstrumentB(Instruments.VOCALS);
		assertTrue(errorValidation.checkInstrumentComboAllowed(musician));
	}
	
	@Test
	void testBassAndVocalsTrue() {
		musician.setInstrumentA(Instruments.BASS);	
		musician.setInstrumentB(Instruments.VOCALS);
		assertTrue(errorValidation.checkInstrumentComboAllowed(musician));
	}
	
	@Test
	void testInstrumentComboAllowedFalse() {
		musician.setInstrumentA(Instruments.DRUMS);
		musician.setInstrumentB(Instruments.BASS);
		assertFalse(errorValidation.checkInstrumentComboAllowed(musician));
		}

	@Test
	void testDuplicateInstrumentBNull() {
		musician.setInstrumentB(null);
		assertFalse(errorValidation.checkDuplicateInstrument(musician));
	}
	
	@Test
	void testDuplicateInstrumentTrue() {
		musician.setInstrumentA(Instruments.BASS);
		musician.setInstrumentB(Instruments.BASS);
		assertTrue(errorValidation.checkDuplicateInstrument(musician));
	}
	
	@Test
	void testDuplicateInstrumentFalse() {
		// uses default musician object from buildMusician()
		assertFalse(errorValidation.checkDuplicateInstrument(musician));
	}
	
	@Test
	void testValidFolkMusicianNullInstrumentB() {
		System.out.println("in testValidFolkMusicianNullInstrumentB()");
		musician.setInstrumentB(null);
		assertFalse(errorValidation.checkValidFolkMusician(musician));
	}
	
	@Test 
	void testValidFolkMusicianTrue() {
		System.out.println("in testValidFolkMusicianTrue()");
		musician.setInstrumentA(Instruments.VOCALS);
		musician.setInstrumentB(Instruments.GUITAR);
		assertTrue(errorValidation.checkValidFolkMusician(musician));
		
		musician.setInstrumentA(Instruments.GUITAR);
		musician.setInstrumentB(Instruments.VOCALS);
		assertTrue(errorValidation.checkValidFolkMusician(musician));
		
		
	}
	
	@Test
	void testValidFolkMusicianFalse() {
		// test with default musician object
		System.out.println("in testValidFolkMusicianFalse()");
		assertFalse(errorValidation.checkValidFolkMusician(musician));
	}
	Musician buildMusician() {
		Musician musician = new Musician();
		musician.setAge(45);
		musician.setEmailAddress("james.james@james.com");
		musician.setFirstName("James");
		musician.setLastName("Connolly");
		musician.setStyle(MusicStyle.METAL);
		musician.setInstrumentA(Instruments.DRUMS);
		musician.setInstrumentB(Instruments.VOCALS);
		
		return musician;
	}
}