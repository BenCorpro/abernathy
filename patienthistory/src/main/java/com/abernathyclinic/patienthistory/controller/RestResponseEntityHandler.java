package com.abernathyclinic.patienthistory.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abernathyclinic.patienthistory.exception.NoteNotFoundException;

@RestControllerAdvice
public class RestResponseEntityHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleArgumentNotValidException(MethodArgumentNotValidException manvEx){
		Map<String, String> errors = new HashMap<>();
		manvEx.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMEssage = error.getDefaultMessage();
			errors.put("Error", errorMEssage);
		});
		return errors;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoteNotFoundException.class)
	public Map<String, String> handleNoteNotFoundException(NoteNotFoundException nnfEx){
		Map<String, String> error = new HashMap<>();
		error.put("Error", nnfEx.getMessage());
		return error;
	}
	
}
