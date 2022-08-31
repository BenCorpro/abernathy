package com.abernathyclinic.patientinfo.service;

import java.util.List;

import com.abernathyclinic.patientinfo.dto.PatientDTO;
import com.abernathyclinic.patientinfo.exception.PatientNotFoundException;

public interface PatientService {

	List<PatientDTO> getAllPatients();

	PatientDTO getPatientById(Long id) throws PatientNotFoundException;

	List<PatientDTO> getPatientByNames(String firstname, String lastname) throws PatientNotFoundException;

	PatientDTO savePatient(PatientDTO newPatient);

	PatientDTO updatePatient(Long id, PatientDTO modPatient) throws PatientNotFoundException;

	void deletePatient(Long id) throws PatientNotFoundException;

}
