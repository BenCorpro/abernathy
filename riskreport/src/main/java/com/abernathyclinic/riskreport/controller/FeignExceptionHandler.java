package com.abernathyclinic.riskreport.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;

import feign.FeignException;

@RestControllerAdvice
public class FeignExceptionHandler {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(FeignException.NotFound.class)
	public Map<String, String> handleFeignNotFoundException(FeignException fEx){
		Map<String, String> error = new HashMap<>();
		//error.put("Error", fEx.contentUTF8());
		MapLikeType mapLikeType = objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, String.class);
		try {
			error = objectMapper.readValue(fEx.contentUTF8(), mapLikeType);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return error;
	}

}
