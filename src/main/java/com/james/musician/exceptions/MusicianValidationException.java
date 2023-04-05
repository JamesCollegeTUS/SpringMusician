package com.james.musician.exceptions;

public class MusicianValidationException extends MusicianException {
	private static final long serialVersionUID = 334051992916748022L;

	public MusicianValidationException(final String errorMessage) {
		super(errorMessage);
	}

}
