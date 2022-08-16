package com.abernathyclinic.patienthistory.dto;

public class NoteDTO {

	private String id;
	private long patientId;
	private String patientName;
	private String recommendation;
	
	
	public NoteDTO() {
	}


	public NoteDTO(long patientId, String patientName, String recommendation) {
		this.patientId = patientId;
		this.patientName = patientName;
		this.recommendation = recommendation;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public String getRecommendation() {
		return recommendation;
	}


	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	
	
}
