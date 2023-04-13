package com.james.musician.musicianBuilder;

import org.springframework.stereotype.Component;

import com.james.musician.model.Instruments;
import com.james.musician.model.MusicStyle;
import com.james.musician.model.Musician;

@Component
public class MusicianBuilder {

	public Musician buildMusician() {
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
	
	public Musician buildMusicianWithId() {
		Musician musician = new Musician();
		musician.setId(1L);
		musician.setAge(45);
		musician.setEmailAddress("james.james@james.com");
		musician.setFirstName("James");
		musician.setLastName("Connolly");
		musician.setStyle(MusicStyle.METAL);
		musician.setInstrumentA(Instruments.DRUMS);
		musician.setInstrumentB(Instruments.VOCALS);
		
		return musician;
	}
	public Musician updateMusician() {
		Musician musician = new Musician();
		musician.setAge(54);
		musician.setEmailAddress("james.james@james.com");
		musician.setFirstName("James");
		musician.setLastName("Connolly");
		musician.setStyle(MusicStyle.ROCK);
		musician.setInstrumentA(Instruments.VOCALS);
		musician.setInstrumentB(Instruments.GUITAR);
		
		return musician;
	}
}
