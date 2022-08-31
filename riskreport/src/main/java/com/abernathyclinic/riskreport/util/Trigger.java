package com.abernathyclinic.riskreport.util;

public enum Trigger {
	
	HEMOGLOBINE_A1C("hemoglobin a1c", "hémoglobine a1c"),
	MICROALBUMINE("microalbumin", "microalbumine"),
	TAILLE("height", "taille"),
	POIDS("weight", "poids"),
	FUMEUR("smoker", "fumeur"),
	ANORMAL("abnormal", "anormal"),
	CHOLESTEROL("cholesterol", "cholestérol"),
	VERTIGE("dizziness", "vertige"),
	RECHUTE("relapse", "rechute"),
	REACTION("reaction", "réaction"),
	ANTICORPS("antibodies", "anticorps");
	
	public final String englishTerm;
	public final String frenchTerm;
	
	private Trigger(String englishTerm, String frenchTerm) {
		this.englishTerm = englishTerm;
		this.frenchTerm = frenchTerm;
	}
}
