package com.abernathyclinic.riskreport.restclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.abernathyclinic.riskreport.model.Note;

@FeignClient(name = "patient-histo-service", url = "${pathisto.url}")
public interface NoteRestClient {

	@GetMapping("/notes/{patientId}")
	public List<Note> getNoteByPatientId(@PathVariable("patientId") Long patientId);
	
}
