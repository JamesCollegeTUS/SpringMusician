package com.james.musician.errors;

import javax.sound.midi.Instrument;

import org.springframework.stereotype.Component;

import com.james.musician.model.Instruments;
import com.james.musician.model.MusicStyle;
import com.james.musician.model.Musician;

@Component
public class ErrorValidation {

	// contains logic for validation during entity creation
	Musician musician;
	
	// valid instruments are GUITAR, DRUMS, BASS, VOCALS. Must select at least one and no more than two.
	// the following rules also apply: 
	// can enter a single valid instrument
	// can be vocals + one other physical instrument
	// can't play two physical instruments. I.e GUITAR & BASS
	public boolean checkInstrumentComboAllowed(Musician musician) {

		Instruments instrumentA = musician.getInstrumentA();
		Instruments instrumentB = musician.getInstrumentB();
		
		if(instrumentB == null) {
			return true;
		}
		else if(instrumentA.equals(Instruments.VOCALS) && (instrumentB.equals(Instruments.GUITAR)
				|| instrumentB.equals(Instruments.DRUMS) || instrumentB.equals(Instruments.BASS))){
			return true;
		} else if((instrumentA.equals(Instruments.GUITAR) || instrumentA.equals(Instruments.DRUMS) || instrumentA.equals(Instruments.BASS))
				&& (instrumentB.equals(Instruments.VOCALS))) {
			return true;
		} 
		else {
			return false;
		}
	}
	
	// cannot enter duplicate instruments. I.E instrumentA: GUITAR, instrumentB: GUITAR
	public boolean checkDuplicateInstrument(Musician musician) {
		this.musician = musician;
		
		if(musician.getInstrumentB() == null) {
			return false;
		}
		if(musician.getInstrumentA().equals(musician.getInstrumentB())) {
			return true;
		}
		return false;
	}
	
	// a FOLK musician must sing AND play guitar only 
	
	public boolean checkValidFolkMusician(Musician musician) {
		this.musician = musician;
		System.out.println("in checkValidFolkMusician()");
		System.out.println("instrumentA: " + musician.getInstrumentA() + " , " + "instrumentB: " + musician.getInstrumentB());
		if(musician.getInstrumentB() == null) {
			return false;
		}
		if((musician.getInstrumentA().equals(Instruments.VOCALS) 
				&& musician.getInstrumentB().equals(Instruments.GUITAR)) 
				|| (musician.getInstrumentA().equals(Instruments.GUITAR)
				&& musician.getInstrumentB().equals(Instruments.VOCALS))){
				return true;
		}
		return false;
		}
}
