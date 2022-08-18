package com.abernathyclinic.patientinfo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.patientinfo.dto.PatientDTO;
import com.abernathyclinic.patientinfo.exception.PatientNotFoundException;
import com.abernathyclinic.patientinfo.service.PatientService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PatientController {

	private PatientService patientService;
	
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}
	
	
	@GetMapping("/patients")
	public List<PatientDTO> getAllPatients() {
		return patientService.getAllPatients();
	}
	
	@GetMapping("/patient/{id}")
	public PatientDTO getPatientById(@PathVariable("id") Long id) throws PatientNotFoundException {
		return patientService.getPatientById(id);
	}	
	
	@GetMapping("/patient")
	public List<PatientDTO> getPatientByNames(@RequestParam(name="given", defaultValue="") String firstname, @RequestParam(name="family", defaultValue="") String lastname) throws PatientNotFoundException {
		return patientService.getPatientByNames(lastname, firstname);
	}
	
	@PostMapping("/patient/add")
	@ResponseStatus(HttpStatus.CREATED)
	public PatientDTO savePatient(@Valid @RequestBody PatientDTO newPatient) {	
		return patientService.savePatient(newPatient);
	}
	
	@PutMapping("/patient/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PatientDTO updatePatient(@PathVariable("id") Long id, @Valid @RequestBody PatientDTO modPatient) throws PatientNotFoundException {
		return patientService.updatePatient(id, modPatient);
	}
	
	@DeleteMapping("/patient/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePatient(@PathVariable("id") Long id) throws PatientNotFoundException {
		patientService.deletePatient(id);
	}
	
}
