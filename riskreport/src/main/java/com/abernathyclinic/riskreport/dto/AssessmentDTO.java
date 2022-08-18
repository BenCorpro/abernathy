package com.abernathyclinic.riskreport.dto;

import com.abernathyclinic.riskreport.util.Risk;

public class AssessmentDTO {
	
	private long patientId;
	private String patientName;
	private int patientAge;
	private Risk risk;
	
	
	public AssessmentDTO() {
	}
	
	public AssessmentDTO(long patientId, String patientName, int patientAge, Risk risk) {
		this.patientId = patientId;
		this.patientName = patientName;
		this.patientAge = patientAge;
		this.risk = risk;
	}


	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public int getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}
	public Risk getRisk() {
		return risk;
	}
	public void setRisk(Risk risk) {
		this.risk = risk;
	}
	
	
}
