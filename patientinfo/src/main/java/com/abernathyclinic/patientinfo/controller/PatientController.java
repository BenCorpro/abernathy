package com.abernathyclinic.patientinfo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.patientinfo.exception.PatientNotFoundException;
import com.abernathyclinic.patientinfo.model.Patient;
import com.abernathyclinic.patientinfo.service.PatientService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PatientController {

	private PatientService patientService;
	
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}
	
	@GetMapping("/patients")
	public List<Patient> getAllPatients() {
		return patientService.getAllPatients();
	}
	
	//@GetMapping("/patients")
	public Page<Patient> getPatientPage(@RequestParam(name="page", defaultValue="0") int page,
										@RequestParam(name="size", defaultValue="5") int size) {
		return patientService.getPatientPage(page, size);
	}
	
	@GetMapping("/patient/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long id) {
		Patient patient= null;
		try {patient = patientService.getPatientById(id);
		} catch (PatientNotFoundException pnfEx) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}	
	
	@GetMapping("/patient")
	public List<Patient> getPatientByNames(@RequestParam(name="given", defaultValue="") String firstname, @RequestParam(name="family", defaultValue="") String lastname) {
		return patientService.getPatientByNames(lastname, firstname);
	}
	
	@PostMapping("/patient/add")
	@ResponseBody
	public Patient savePatient(@Valid @RequestBody Patient newPatient) {	
		return patientService.savePatient(newPatient);
	}
	
	@PutMapping("/patient/update/{id}")
	public Patient updatePatient(@PathVariable("id") Long id, @RequestBody Patient modPatient) {
		return patientService.updatePatient(id, modPatient);
	}
	
	@DeleteMapping("/patient/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePatient(@PathVariable("id") Long id) {
		patientService.deletePatient(id);
	}
}
