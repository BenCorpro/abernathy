package com.abernathyclinic.riskreport.service;

import java.util.List;

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
		for(Trigger trigger: Trigger.values()) {
			riskOccurences += (int)patientNotes.stream().filter(note -> note.getRecommendation().contains(trigger.value))
													.count();
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
		return new AssessmentDTO(patient.getId(), patient.getFirstname(), patientAge, riskAssess);
	}
}
