package com.abernathyclinic.patientinfo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

import com.abernathyclinic.patientinfo.model.Patient;
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
	
	List<Patient> patientTestList = new ArrayList<>();
	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@BeforeAll
	public void setUp() throws Exception{
		Patient patientTestOne = new Patient("TestNone", "Test", dateFormatter.parse("1966-12-31"), Gender.FEMALE, "1 Brookside St", "100-222-3333");
		Patient patientTestTwo = new Patient("TestBorderline", "Test", dateFormatter.parse("1945-06-24"), Gender.MALE, "2 High St", "200-333-4444");
		Patient patientTestThree = new Patient("TestInDanger", "Test", dateFormatter.parse("2004-06-18"), Gender.MALE, "3 Club Road", "300-444-5555");
		Patient patientTestFour = new Patient("TestEarlyOnset", "Test", dateFormatter.parse("2002-06-28"), Gender.FEMALE, "4 Valley Drive", "400-555-6666");
		Patient patientdeleteTest = new Patient("TestDeletePatient", "Test", dateFormatter.parse("1974-07-31"), Gender.MALE, "7 Callico St", "777-888-9999");
		patientTestList.add(patientTestOne);
		patientTestList.add(patientTestTwo);
		patientTestList.add(patientTestThree);
		patientTestList.add(patientTestFour);
		patientTestList.add(patientdeleteTest);
	}
	
	@Test
	public void testGetAllPatients() throws Exception {
		when(patientService.getAllPatients()).thenReturn(patientTestList);
		mockMvc.perform(get("/patients"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(patientTestList)));
	}
	
	@Test
	public void testGetPatientById() throws Exception {
		when(patientService.getPatientById(1L)).thenReturn(patientTestList.get(0));
		mockMvc.perform(get("/patient/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(patientTestList.get(0))));
	}
	
	@Test
	public void testGetPatientByNames() throws Exception {
		when(patientService.getPatientByNames("", "bord")).thenReturn(patientTestList.subList(1, 2));
		mockMvc.perform(get("/patient?given=bord&family="))
		 		.andExpect(status().isOk())
		 		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		 		.andExpect(content().json(objectMapper.writeValueAsString(patientTestList.subList(1, 2))));
	}
	
	@Test
	public void testSavePatient() throws Exception {
		Patient patientSaveTest = new Patient("TestBorderline", "Test", dateFormatter.parse("1945-06-24"), Gender.MALE, "2 High St", "200-333-4444");
		when(patientService.savePatient(patientSaveTest)).thenReturn(patientSaveTest);
		mockMvc.perform(MockMvcRequestBuilders.post("/patient/add")
				.content(objectMapper.writeValueAsString(patientSaveTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(patientSaveTest)));
	}
	
	@Test
	public void testUpdatePatient() throws Exception {
		Patient patientUpdateTest = new Patient("TestInDanger", "Test", dateFormatter.parse("2004-06-18"), Gender.MALE, "3 Club Road", "300-444-5555");
		when(patientService.updatePatient(3L, patientUpdateTest)).thenReturn(patientUpdateTest);
		mockMvc.perform(MockMvcRequestBuilders.put("/patient/update/"+"3")
				.content(objectMapper.writeValueAsString(patientUpdateTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(patientUpdateTest)));
	}
	
	@Test
	public void testDeletePatient() throws Exception {
		mockMvc.perform(delete("/patient/delete/"+"5"))
				.andExpect(status().isNoContent());
	}
}
