package com.abernathyclinic.patienthistory.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class NoteDTO {

	private String id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	@NotNull
	@Positive(message= "L'identifiant du patient doit être valide")
	private long patientId;
	@NotNull(message = "Le nom du patient doit être renseigné")
	@Pattern(regexp = "[a-zA-Z-\\s]{3,15}", message = "Le nom doit être valide et comporter 3 caractères minimum")
	private String patientName;
	@NotBlank(message ="La note ne doit pas être vide")
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
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
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
