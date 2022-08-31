package com.abernathyclinic.riskreport.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abernathyclinic.riskreport.dto.AssessmentDTO;
import com.abernathyclinic.riskreport.model.Note;
import com.abernathyclinic.riskreport.model.Patient;
import com.abernathyclinic.riskreport.restclient.NoteRestClient;
import com.abernathyclinic.riskreport.restclient.PatientRestClient;
import com.abernathyclinic.riskreport.util.AgeCalculator;
import com.abernathyclinic.riskreport.util.Gender;
import com.abernathyclinic.riskreport.util.Risk;
import com.abernathyclinic.riskreport.util.Trigger;

@Service
public class ReportServiceImpl implements ReportService {
	
	private static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

	private PatientRestClient patientRestClient;
	private NoteRestClient noteRestClient;
	
	
	public ReportServiceImpl(PatientRestClient patientRestClient, NoteRestClient noteRestClient) {
		this.patientRestClient = patientRestClient;
		this.noteRestClient = noteRestClient;
	}
	

	@Override
	public AssessmentDTO riskAssessment(long patientId) {
		return riskAssessment(patientRestClient.getPatientById(patientId));
	}
	
	@Override
	public AssessmentDTO riskAssessment(String patientName) {
		return riskAssessment(patientRestClient.getPatientByNames(patientName, "").get(0));
	}
	
	@Override
	public AssessmentDTO riskAssessment(Patient patient) {
		Risk riskAssess = null;
		List<Note> patientNotes = noteRestClient.getNoteByPatientId(patient.getId());
		int patientAge = AgeCalculator.ageCalculator(patient.getBirthdate()).getYears();
		boolean underThirty = AgeCalculator.isUnderThirtyFunction(patient.getBirthdate());
		int riskOccurences = 0;
		boolean found;
		for(Trigger trigger: Trigger.values()) {
			found = patientNotes.stream().anyMatch(note -> note.getRecommendation().toLowerCase().contains(trigger.englishTerm) || 
																	note.getRecommendation().toLowerCase().contains(trigger.frenchTerm));
			if(found) riskOccurences++;
		}
		switch(riskOccurences) {
			case 0:
			case 1: riskAssess = Risk.None;
					break;
			case 2: if (!underThirty) { riskAssess = Risk.BorderLine;
					} else {
						riskAssess = Risk.None;	
					}
					break;
			case 3: if(underThirty && patient.getGender() == Gender.MALE) { riskAssess = Risk.InDanger;
					} else if (underThirty && patient.getGender() == Gender.FEMALE) {
						riskAssess = Risk.None;
					} else {
						riskAssess = Risk.BorderLine;
					}
					break;
			case 4: if(underThirty) {riskAssess = Risk.InDanger;
					} else {
						riskAssess = Risk.BorderLine;
					}
					break;
			case 5: if(underThirty && patient.getGender() == Gender.MALE) { riskAssess = Risk.EarlyOnset;
					} else if (underThirty && patient.getGender() == Gender.FEMALE) {
						riskAssess = Risk.InDanger;
					} else {
						riskAssess = Risk.BorderLine;
					}
					break;
			case 6: if(underThirty && patient.getGender() == Gender.MALE) { riskAssess = Risk.EarlyOnset;
					} else {
						riskAssess = Risk.InDanger;
					}
					break;
			case 7: if(underThirty) { riskAssess = Risk.EarlyOnset;
					} else {
						riskAssess = Risk.InDanger;
					}
					break;
			case 8:
			default: riskAssess = Risk.EarlyOnset;
		}
		StringBuilder strName = new StringBuilder();
		String patientName = strName.append(patient.getFirstname()).append(" ").append(patient.getLastname()).toString();
		logger.info("Patient " + patientName + " has " + riskOccurences + "risk occurences");
		return new AssessmentDTO(patient.getId(), patientName, patientAge, riskAssess);
	}
}
