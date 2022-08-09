package com.abernathyclinic.patientinfo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.abernathyclinic.patientinfo.exception.PatientNotFoundException;
import com.abernathyclinic.patientinfo.model.Patient;
import com.abernathyclinic.patientinfo.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	private PatientRepository patientRepository;
	
	public PatientServiceImpl(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}
	
	@Override
	public Page<Patient> getPatientPage(int page, int size) {
		return patientRepository.findAll(PageRequest.of(page, size));
	}
	
	@Override
	public Patient getPatientById(Long id) throws PatientNotFoundException{
		return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient " + id + " not found!"));
	}
	
	@Override
	public List<Patient> getPatientByNames(String lastname, String firstname) {
		return patientRepository.findByFirstnameContainsAndLastnameContains(firstname, lastname);
	}
	
	@Override
	public Patient savePatient(Patient newPatient) {
		return patientRepository.save(newPatient);
	}
	
	@Override
	public Patient updatePatient(Long id, Patient modPatient) {
		Patient patientToUpdate = patientRepository.findById(id).orElseThrow();
		patientToUpdate.setFirstname(modPatient.getFirstname());
		patientToUpdate.setLastname(modPatient.getLastname());
		patientToUpdate.setBirthdate(modPatient.getBirthdate());
		patientToUpdate.setGender(modPatient.getGender());
		patientToUpdate.setAddress(modPatient.getAddress());
		patientToUpdate.setPhone(modPatient.getPhone());
		return patientRepository.save(patientToUpdate);
	}
	
	@Override
	public void deletePatient(Long id) {
		patientRepository.deleteById(id);
	}
}
