package com.james.musician.exceptions;

public abstract class MusicianException extends Exception{
	
	protected MusicianException(final String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 6013983962125460401L;

}
