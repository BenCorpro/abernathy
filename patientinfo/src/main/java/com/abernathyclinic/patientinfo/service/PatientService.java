package com.abernathyclinic.patientinfo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.abernathyclinic.patientinfo.exception.PatientNotFoundException;
import com.abernathyclinic.patientinfo.model.Patient;

public interface PatientService {

	List<Patient> getAllPatients();

	Patient getPatientById(Long id) throws PatientNotFoundException;

	List<Patient> getPatientByNames(String lastname, String firstname);

	Patient savePatient(Patient newPatient);

	Patient updatePatient(Long id, Patient modPatient);

	void deletePatient(Long id);

	Page<Patient> getPatientPage(int page, int size);

}
