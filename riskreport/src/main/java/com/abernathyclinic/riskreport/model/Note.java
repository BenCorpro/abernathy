package com.abernathyclinic.riskreport.model;

import java.time.LocalDateTime;

public class Note {

	private String id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private long patientId;
	private String patientName;
	private String recommendation;
	
	public Note(String id, long patientId, String patientName, String recommendation) {
		this.id = id;
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
