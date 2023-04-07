package com.james.musician.errors;

public enum ErrorMessages {
	
	
	ALREADY_EXISTS("Musician already exists"),
	INVALID_INSTRUMENT_COMBO("Muscian cannot play more than one instrument"),
	DUPLICATE_INSTRUMENTS("Cannot duplicate instruments"),
	MUSICIAN_NOT_FOUND("Musician not found"),
	INVALID_FOLK_MUSICIAN("A FOLK musician must sing AND play guitar only");
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage = errMsg;
	}
	
	public String getMsg() {
		return errorMessage;
	}

}
