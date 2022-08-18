package com.abernathyclinic.riskreport.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class FeignExceptionHandler {
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(FeignException.NotFound.class)
	public Map<String, String> handleFeignNotFoundException(FeignException fEx){
		Map<String, String> error = new HashMap<>();
		error.put("Error", fEx.contentUTF8());
		return error;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FeignException.BadRequest.class)
	public Map<String, String> handleFeignBadRequestException(FeignException fEx){
		Map<String, String> error = new HashMap<>();
		error.put("Error", fEx.contentUTF8());
		return error;
	}
}
