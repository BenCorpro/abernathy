package com.abernathyclinic.riskreport.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.abernathyclinic.riskreport.dto.AssessmentDTO;
import com.abernathyclinic.riskreport.service.ReportService;
import com.abernathyclinic.riskreport.util.Risk;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

@WebMvcTest(controllers = ReportController.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ReportControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ReportService reportService;
	
	AssessmentDTO assessmentTest;
	
	@BeforeAll
	public void setUp() throws Exception{
		assessmentTest = new AssessmentDTO(1L, "TestNone", 55, Risk.None);
	}
	
	@Test
	public void testGetPatientRiskAssessment_byId_returnsAssessment() throws Exception {
		when(reportService.riskAssessment(anyLong())).thenReturn(assessmentTest);
		mockMvc.perform(get("/report/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(assessmentTest)));
	}
	
	@Test
	public void testGetPatientRiskAssessment_byWrongId_returnsNotFound() throws Exception {
		when(reportService.riskAssessment(anyLong())).thenThrow(FeignException.NotFound.class);
		mockMvc.perform(get("/report/11"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void testGetPatientRiskAssessment_byName_returnsAssessment() throws Exception {
		when(reportService.riskAssessment(anyString())).thenReturn(assessmentTest);
		mockMvc.perform(get("/report?patientName=TestNone"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(assessmentTest)));
	}
	
	@Test
	public void testGetPatientRiskAssessment_byNameWithNoNotes_returnsNotFound() throws Exception {
		when(reportService.riskAssessment(anyString())).thenThrow(FeignException.NotFound.class);
		mockMvc.perform(get("/report?patientName=TestNoNotes"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
}
