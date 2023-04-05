package com.james.musician.errors;

import javax.sound.midi.Instrument;

import org.springframework.stereotype.Component;

import com.james.musician.model.Instruments;
import com.james.musician.model.Musician;

@Component
public class ErrorValidation {

	// contains logic for validation during entity creation
	
	public boolean checkInstrumentComboAllowed(Musician musician) {
//		String instrumentA = musician.getInstrumentA().toString();
//		String instrumentB = musician.getInstrumentB().toString();
		Instruments instrumentA = musician.getInstrumentA();
		Instruments instrumentB = musician.getInstrumentB();
		
		if(instrumentA.equals(Instruments.VOCALS) && (instrumentB.equals(Instruments.GUITAR)
				|| instrumentB.equals(Instruments.DRUMS) || instrumentB.equals(Instruments.BASS)
				|| instrumentB.equals(null))) {
			return true;
		} else if((instrumentA.equals(Instruments.GUITAR) || instrumentA.equals(Instruments.DRUMS) || instrumentA.equals(Instruments.BASS))
				&& (instrumentB.equals(Instruments.VOCALS) || instrumentB.equals(null))) {
			return true;
		} else {
			return false;
		}
	
	
}
}
