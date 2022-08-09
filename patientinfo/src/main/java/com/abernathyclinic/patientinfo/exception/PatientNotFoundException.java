package com.abernathyclinic.patientinfo.exception;

public class PatientNotFoundException extends Exception {

	private static final long serialVersionUID = 8564238969924002880L;
	
	public PatientNotFoundException(String message) {
		super(message);
	}

}
