package com.abernathyclinic.patientinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abernathyclinic.patientinfo.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

	public List<Patient> findByFirstnameContainsAndLastnameContains(String firstname, String lastname);
}
