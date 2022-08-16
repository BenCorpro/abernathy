package com.abernathyclinic.patientinfo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abernathyclinic.patientinfo.dto.PatientDTO;
import com.abernathyclinic.patientinfo.dto.PatientMapper;
import com.abernathyclinic.patientinfo.exception.PatientNotFoundException;
import com.abernathyclinic.patientinfo.model.Patient;
import com.abernathyclinic.patientinfo.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	private PatientRepository patientRepository;
	private PatientMapper patientMapper;

	
	public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
	}


	@Override
	public List<PatientDTO> getAllPatients() {
		List<Patient> patients = patientRepository.findAll();
		List<PatientDTO> patientDTOs = patients.stream().map(patient -> patientMapper.fromPatient(patient))
														.collect(Collectors.toList());
		return patientDTOs;
	}
	
	@Override
	public PatientDTO getPatientById(Long id) throws PatientNotFoundException{
		Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient " + id + " not found!"));
		PatientDTO patientDTO = patientMapper.fromPatient(patient);
		return patientDTO;
	}
	
	@Override
	public List<PatientDTO> getPatientByNames(String lastname, String firstname) throws PatientNotFoundException {
		List<Patient> patients = patientRepository.findByFirstnameContainsAndLastnameContains(firstname, lastname);
		if(patients.isEmpty()) throw new PatientNotFoundException("Patient with firstname: " + firstname + " and lastname: " + lastname + " not found!");
		List<PatientDTO> patientsDTO = patients.stream().map(patient -> patientMapper.fromPatient(patient))
						.collect(Collectors.toList());
		return patientsDTO;
	}
	
	@Override
	public PatientDTO savePatient(PatientDTO newPatient) {
		Patient patient = patientMapper.fromPatientDTO(newPatient);
		patient = patientRepository.save(patient);
		return patientMapper.fromPatient(patient);
	}
	
	@Override
	public PatientDTO updatePatient(Long id, PatientDTO modPatient) throws PatientNotFoundException {
		Patient patientToUpdate = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient " + id + " not found!"));
		patientToUpdate.setFirstname(modPatient.getFirstname());
		patientToUpdate.setLastname(modPatient.getLastname());
		patientToUpdate.setBirthdate(modPatient.getBirthdate());
		patientToUpdate.setGender(modPatient.getGender());
		patientToUpdate.setAddress(modPatient.getAddress());
		patientToUpdate.setPhone(modPatient.getPhone());
		Patient patient = patientRepository.save(patientToUpdate);
		return patientMapper.fromPatient(patient);
	}
	
	@Override
	public void deletePatient(Long id) throws PatientNotFoundException {
		patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient " + id + " not found!"));
		patientRepository.deleteById(id);
	}
	
}
