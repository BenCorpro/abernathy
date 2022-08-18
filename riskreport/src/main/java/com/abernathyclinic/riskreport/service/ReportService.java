package com.abernathyclinic.riskreport.service;

import com.abernathyclinic.riskreport.dto.AssessmentDTO;
import com.abernathyclinic.riskreport.model.Patient;

public interface ReportService {

	AssessmentDTO riskAssessment(long patientId);

	AssessmentDTO riskAssessment(String patientName);

	AssessmentDTO riskAssessment(Patient patient);

}