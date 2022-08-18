package com.abernathyclinic.riskreport.util;

public enum Trigger {
	
	HEMOGLOBINE_A1C("Hemoglobin A1C"),
	MICROALBUMINE("Microalbumine"),
	TAILLE("Height"),
	POIDS("Weight"),
	FUMEUR("Smoker"),
	ANORMAL("Abnormal"),
	CHOLESTEROL("Cholesterol"),
	VERTIGE("Dizziness"),
	RECHUTE("Rechute"),
	REACTION("Reaction"),
	ANTICORPS("Antibodies");
	
	public final String value;
	
	private Trigger(String value) {
		this.value = value;
	}
}
