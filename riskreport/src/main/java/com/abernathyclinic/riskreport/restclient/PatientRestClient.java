package com.abernathyclinic.riskreport.restclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.abernathyclinic.riskreport.model.Patient;

@FeignClient(name = "patient-info-service", url = "http://localhost:8081")
public interface PatientRestClient {

	@GetMapping("/patient/{id}")
	public Patient getPatientById(@PathVariable("id") Long id);
	
	@GetMapping("/patient")
	public List<Patient> getPatientByNames(@RequestParam(name="given", defaultValue="") String firstname, @RequestParam(name="family", defaultValue="") String lastname);
	
}
