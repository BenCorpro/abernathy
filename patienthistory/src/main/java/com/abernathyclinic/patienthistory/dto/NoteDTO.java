package com.abernathyclinic.patienthistory.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NoteDTO {

	private String id;
	@Positive
	private long patientId;
	@NotNull(message = "Le nom du patient doit être renseigné")
	private String patientName;
	@NotEmpty(message ="La note ne doit pas être vide")
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
