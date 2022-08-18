package com.abernathyclinic.patientinfo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abernathyclinic.patientinfo.exception.PatientNotFoundException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleArgumentNotValidException(MethodArgumentNotValidException manvEx){
		Map<String, String> errors = new HashMap<>();
		manvEx.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			errors.put("Error", errorMessage);
		});
		return errors;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(PatientNotFoundException.class)
	public Map<String, String> handlePatientNotFoundException(PatientNotFoundException pnfEx) {
		Map<String, String> error = new HashMap<>();
		error.put("Error", pnfEx.getMessage());
		return error;
	}
	
}
