package com.james.musician.errors;

public enum ErrorMessages {
	
	//INVALID_AGE("Musicians must be 18 or over and under 66"),
	ALREADY_EXISTS("Musician already exists"),
	INVALID_INSTRUMENT_COMBO("Muscian cannot play more than one instrument"),
	DUPLICATE_INSTRUMENTS("Cannot duplicate instruments"),
	NOT_FOUND("Musician not found"),
	INVALID_FOLK_MUSICIAN("A FOLK musician must sing AND play guitar only");
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage = errMsg;
	}
	
	public String getMsg() {
		return errorMessage;
	}

}
