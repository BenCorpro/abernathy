package com.abernathyclinic.riskreport.util;

public enum Trigger {
	
	HEMOGLOBINE_A1C("Hemoglobin A1C", "Hémoglobine A1C"),
	MICROALBUMINE("Microalbumin", "Microalbumine"),
	TAILLE("Height", "Taille"),
	POIDS("Weight", "Poids"),
	FUMEUR("Smoker", "Fumeur"),
	ANORMAL("Abnormal", "Anormal"),
	CHOLESTEROL("Cholesterol", "Cholestérol"),
	VERTIGE("Dizziness", "Vertige"),
	RECHUTE("Relapse", "Rechute"),
	REACTION("Reaction", "Réaction"),
	ANTICORPS("Antibodies", "Anticorps");
	
	public final String englishTerm;
	public final String frenchTerm;
	
	private Trigger(String englishTerm, String frenchTerm) {
		this.englishTerm = englishTerm;
		this.frenchTerm = frenchTerm;
	}
}
