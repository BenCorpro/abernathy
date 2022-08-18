package com.abernathyclinic.riskreport.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.riskreport.dto.AssessmentDTO;
import com.abernathyclinic.riskreport.service.ReportService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReportController {

	private ReportService reportService;

	
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}
	

	@GetMapping("/report/{patientId}")
	public AssessmentDTO getPatientRiskAssessment(@PathVariable("patientId") long patientId) {
		return reportService.riskAssessment(patientId);
	}
	
	@GetMapping("/report")
	public AssessmentDTO getPatientRiskAssessment(@RequestParam("patientName") String patientName) {
		return reportService.riskAssessment(patientName);
	}
	
}
