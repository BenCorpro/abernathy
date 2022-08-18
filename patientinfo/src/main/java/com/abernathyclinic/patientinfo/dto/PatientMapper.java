package com.abernathyclinic.patientinfo.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.abernathyclinic.patientinfo.model.Patient;

@Service
public class PatientMapper {

	public PatientDTO fromPatient(Patient patient) {
		PatientDTO patientDTO = new PatientDTO();
		BeanUtils.copyProperties(patient, patientDTO);
		return patientDTO;
	}
	
	public Patient fromPatientDTO(PatientDTO patientDTO) {
		Patient patient = new Patient();
		BeanUtils.copyProperties(patientDTO, patient);
		return patient;
	}
}
