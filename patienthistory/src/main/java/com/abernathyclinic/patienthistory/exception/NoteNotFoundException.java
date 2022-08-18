package com.abernathyclinic.patienthistory.exception;

public class NoteNotFoundException extends Exception{

	private static final long serialVersionUID = -734529380183606365L;

	public NoteNotFoundException(String message) {
		super(message);
	}

}
