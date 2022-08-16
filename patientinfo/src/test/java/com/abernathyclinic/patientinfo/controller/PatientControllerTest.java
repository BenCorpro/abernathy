package com.abernathyclinic.patientinfo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.abernathyclinic.patientinfo.dto.PatientDTO;
import com.abernathyclinic.patientinfo.exception.PatientNotFoundException;
import com.abernathyclinic.patientinfo.service.PatientService;
import com.abernathyclinic.patientinfo.util.Gender;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = PatientController.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PatientService patientService;
	
	List<PatientDTO> patientTestList = new ArrayList<>();
	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@BeforeAll
	public void setUp() throws Exception{
		PatientDTO patientTestOne = new PatientDTO("TestNone", "Test", dateFormatter.parse("1966-12-31"), Gender.FEMALE, "1 Brookside St", "100-222-3333");
		PatientDTO patientTestTwo = new PatientDTO("TestBorderline", "Test", dateFormatter.parse("1945-06-24"), Gender.MALE, "2 High St", "200-333-4444");
		PatientDTO patientTestThree = new PatientDTO("TestInDanger", "Test", dateFormatter.parse("2004-06-18"), Gender.MALE, "3 Club Road", "300-444-5555");
		PatientDTO patientTestFour = new PatientDTO("TestEarlyOnset", "Test", dateFormatter.parse("2002-06-28"), Gender.FEMALE, "4 Valley Drive", "400-555-6666");
		PatientDTO patientdeleteTest = new PatientDTO("TestDeletePatient", "Test", dateFormatter.parse("1974-07-31"), Gender.MALE, "7 Callico St", "777-888-9999");
		patientTestList.add(patientTestOne);
		patientTestList.add(patientTestTwo);
		patientTestList.add(patientTestThree);
		patientTestList.add(patientTestFour);
		patientTestList.add(patientdeleteTest);
	}
	
	@Test
	public void testGetAllPatients_returnsPatientList() throws Exception {
		when(patientService.getAllPatients()).thenReturn(patientTestList);
		mockMvc.perform(get("/patients"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(patientTestList)));
	}
	
	@Test
	public void testGetPatientById_existingId_returnsPatient() throws Exception {
		when(patientService.getPatientById(1L)).thenReturn(patientTestList.get(0));
		mockMvc.perform(get("/patient/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(patientTestList.get(0))));
	}
	
	@Test
	public void testGetPatientById_wrongId_returnsNotFound() throws Exception {
		when(patientService.getPatientById(11L)).thenThrow(new PatientNotFoundException("Patient 11 not found!"));
		mockMvc.perform(get("/patient/11"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("Error", Is.is("Patient 11 not found!")));
	}
	
	@Test
	public void testGetPatientByNames_matchingName_returnsPatient() throws Exception {
		when(patientService.getPatientByNames("", "bord")).thenReturn(patientTestList.subList(1, 2));
		mockMvc.perform(get("/patient?given=bord&family="))
		 		.andExpect(status().isOk())
		 		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		 		.andExpect(content().json(objectMapper.writeValueAsString(patientTestList.subList(1, 2))));
	}
	
	@Test
	public void testGetPatientByNames_wrongName_returnsNotFound() throws Exception {
		when(patientService.getPatientByNames("", "wrong")).thenThrow(new PatientNotFoundException("Patient with firstname: and lastname: wrong not found!"));
		mockMvc.perform(get("/patient?given=wrong&family="))
		 		.andExpect(status().isNotFound())
		 		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		 		.andExpect(MockMvcResultMatchers.jsonPath("Error", Is.is("Patient with firstname: and lastname: wrong not found!")));
	}
	
	@Test
	public void testSavePatient_validPatient_returnsPatient() throws Exception {
		PatientDTO patientSaveTest = new PatientDTO("TestBorderline", "Test", dateFormatter.parse("1945-06-24"), Gender.MALE, "2 High St", "200-333-4444");
		when(patientService.savePatient(any(PatientDTO.class))).thenReturn(patientSaveTest);
		mockMvc.perform(MockMvcRequestBuilders.post("/patient/add")
				.content(objectMapper.writeValueAsString(patientSaveTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(patientSaveTest)));
	}
	
	@Test
	public void testSavePatient_nonValidField_returnsBadRequest() throws Exception {
		PatientDTO patientSaveTest = new PatientDTO(null, "Test", dateFormatter.parse("1945-06-24"), Gender.MALE, "2 High St", "200-333-4444");
		when(patientService.savePatient(any(PatientDTO.class))).thenReturn(patientSaveTest);
		mockMvc.perform(MockMvcRequestBuilders.post("/patient/add")
				.content(objectMapper.writeValueAsString(patientSaveTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("firstname", Is.is("Le prénom doit être renseigné")));
	}
	
	@Test
	public void testUpdatePatient_validPatient_returnsPatient() throws Exception {
		PatientDTO patientUpdateTest = new PatientDTO("TestInDanger", "Test", dateFormatter.parse("2004-06-18"), Gender.MALE, "3 Club Road", "300-444-5555");
		when(patientService.updatePatient(anyLong(), any(PatientDTO.class))).thenReturn(patientUpdateTest);
		mockMvc.perform(MockMvcRequestBuilders.put("/patient/update/"+"3")
				.content(objectMapper.writeValueAsString(patientUpdateTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(patientUpdateTest)));
	}
	
	@Test
	public void testUpdatePatient_nonValidField_returnsBadRequest() throws Exception {
		PatientDTO patientUpdateTest = new PatientDTO("TestInDanger", null, dateFormatter.parse("2004-06-18"), Gender.MALE, "3 Club Road", "300-444-5555");
		when(patientService.updatePatient(anyLong(), any(PatientDTO.class))).thenReturn(patientUpdateTest);
		mockMvc.perform(MockMvcRequestBuilders.put("/patient/update/"+"3")
				.content(objectMapper.writeValueAsString(patientUpdateTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("lastname", Is.is("Le nom doit être renseigné")));
	}
	
	@Test
	public void testDeletePatient_existingId_returnsNoContent() throws Exception {
		mockMvc.perform(delete("/patient/delete/"+"5"))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void testDeletePatient_wrongId_returnsNotFound() throws Exception {
		doThrow(new PatientNotFoundException("Patient 11 not found!")).when(patientService).deletePatient(anyLong());
		mockMvc.perform(delete("/patient/delete/"+"11"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("Error", Is.is("Patient 11 not found!")));
	}
}
