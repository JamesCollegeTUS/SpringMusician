package com.james.musician.exceptions;

public class MusicianNotFoundException extends MusicianException{
	private static final long serialVersionUID = 334051992916748022L;

	public MusicianNotFoundException(final String errorMessage) {
		super(errorMessage);
	}

}
