package com.abernathyclinic.riskreport.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abernathyclinic.riskreport.dto.AssessmentDTO;
import com.abernathyclinic.riskreport.model.Note;
import com.abernathyclinic.riskreport.model.Patient;
import com.abernathyclinic.riskreport.restclient.NoteRestClient;
import com.abernathyclinic.riskreport.restclient.PatientRestClient;
import com.abernathyclinic.riskreport.util.Gender;
import com.abernathyclinic.riskreport.util.Risk;

import feign.FeignException;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ReportServiceTest {

	@Autowired
	private ReportService reportService;
	@MockBean
	private NoteRestClient noteRestClient;
	@MockBean
	private PatientRestClient patientRestClient;
	
	List<Patient> listPatientsTest = new ArrayList<>();
	List<Note> listNotesTestPatientOne = new ArrayList<>();
	List<Note> listNotesTestPatientTwo = new ArrayList<>();
	List<Note> listNotesTestPatientThree = new ArrayList<>();
	List<Note> listNotesTestPatientFour = new ArrayList<>();
	List<Note> listNotesTestPatientFive = new ArrayList<>();
	List<Note> listNotesTestPatientSix = new ArrayList<>();
	List<Note> listNotesTestPatientSeven = new ArrayList<>();
	List<Note> listNotesTestPatientEight = new ArrayList<>();
	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@BeforeAll
	public void setUp() throws Exception{
		Patient patientTestOne = new Patient(1L, "Test", "TestNone", dateFormatter.parse("1966-12-31"), Gender.FEMALE, "1 Brookside St", "100-222-3333");
		Patient patientTestTwo = new Patient(2L, "Test", "TestBorderline", dateFormatter.parse("1945-06-24"), Gender.MALE, "2 High St", "200-333-4444");
		Patient patientTestThree = new Patient(3L, "Test", "TestInDanger", dateFormatter.parse("2004-06-18"), Gender.MALE, "3 Club Road", "300-444-5555");
		Patient patientTestFour = new Patient(4L, "Test", "TestEarlyOnset", dateFormatter.parse("2002-06-28"), Gender.FEMALE, "4 Valley Drive", "400-555-6666");
		listPatientsTest.add(patientTestOne);
		listPatientsTest.add(patientTestTwo);
		listPatientsTest.add(patientTestThree);
		listPatientsTest.add(patientTestFour);
		Note noteTestOne = new Note("fakenoteid1", 1L, "TestNone", "Patient states that they are 'feeling terrific' Weight at or below recommended level");
		Note noteTestTwoOne = new Note("fakenoteid2", 2L, "TestBorderline", "Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late");
		Note noteTestTwoTwo = new Note("fakenoteid3", 2L, "TestBorderline", "Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic");
		Note noteTestThreeOne = new Note("fakenoteid4", 3L, "TestInDanger", "Patient states that they are short term Smoker");
		Note noteTestThreeTwo = new Note("fakenoteid5", 3L, "TestInDanger", "Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high");
		Note noteTestFourOne = new Note("fakenoteid6", 4L, "TestEarlyOnset", "Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication");
		Note noteTestFourTwo = new Note("fakenoteid7", 4L, "TestEarlyOnset", "Patient states that they are experiencing back pain when seated for a long time");
		Note noteTestFourThree = new Note("fakenoteid8", 4L, "TestEarlyOnset", "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
		Note noteTestFourFour = new Note("fakenoteid9", 4L, "TestEarlyOnset", "Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction");
		Note noteTestFiveOne = new Note("fakenoteid10", 5L, "TestFive", "Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction");
		Note noteTestFiveTwo = new Note("fakenoteid11", 5L, "TestFive", "Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late");
		Note noteTestSixOne = new Note("fakenoteid12", 6L, "TestSix", "Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late");
		Note noteTestSixTwo = new Note("fakenoteid13", 6L, "TestSix", "Patient states that Cholesterol, Dizziness and Reaction");
		Note noteTestSevenOne = new Note("fakenoteid14", 7L, "TestSeven", "Patient states that they are short term Smoker");
		Note noteTestSevenTwo = new Note("fakenoteid15", 7L, "TestSeven", "Patient states that Body Weight, Cholesterol, Dizziness and Reaction");
		Note noteTestEightOne = new Note("fakenoteid16", 8L, "TestEight", "Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication");
		Note noteTestEighTwo = new Note("fakenoteid17", 8L, "TestEight", "Patient states that they are experiencing back pain when seated for a long time");
		Note noteTestEightThree = new Note("fakenoteid18", 8L, "TestEight", "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
		Note noteTestEightFour = new Note("fakenoteid19", 8L, "TestEight", "Patient states that Cholesterol, Dizziness and Reaction");
		listNotesTestPatientOne.add(noteTestOne);
		listNotesTestPatientTwo.add(noteTestTwoOne);
		listNotesTestPatientTwo.add(noteTestTwoTwo);
		listNotesTestPatientThree.add(noteTestThreeOne);
		listNotesTestPatientThree.add(noteTestThreeTwo);
		listNotesTestPatientFour.add(noteTestFourOne);
		listNotesTestPatientFour.add(noteTestFourTwo);
		listNotesTestPatientFour.add(noteTestFourThree);
		listNotesTestPatientFour.add(noteTestFourFour);
		listNotesTestPatientFive.add(noteTestFiveOne);
		listNotesTestPatientFive.add(noteTestFiveTwo);
		listNotesTestPatientSix.add(noteTestSixOne);
		listNotesTestPatientSix.add(noteTestSixTwo);
		listNotesTestPatientSeven.add(noteTestSevenOne);
		listNotesTestPatientSeven.add(noteTestSevenTwo);
		listNotesTestPatientEight.add(noteTestEightOne);
		listNotesTestPatientEight.add(noteTestEighTwo);
		listNotesTestPatientEight.add(noteTestEightThree);
		listNotesTestPatientEight.add(noteTestEightFour);
	}
	
	@Test
	public void riskAssessmentTest_None_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(1L)).thenReturn(listNotesTestPatientOne);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(0));
		assertNotNull(testAssesment);
		assertEquals("Test TestNone", testAssesment.getPatientName());
		assertEquals(55, testAssesment.getPatientAge());
		assertEquals(Risk.None, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_NoneTwo_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(3L)).thenReturn(listNotesTestPatientTwo);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(2));
		assertNotNull(testAssesment);
		assertEquals("Test TestInDanger", testAssesment.getPatientName());
		assertEquals(18, testAssesment.getPatientAge());
		assertEquals(Risk.None, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_NoneThree_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(4L)).thenReturn(listNotesTestPatientThree);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(3));
		assertNotNull(testAssesment);
		assertEquals("Test TestEarlyOnset", testAssesment.getPatientName());
		assertEquals(20, testAssesment.getPatientAge());
		assertEquals(Risk.None, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_Borderline_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(2L)).thenReturn(listNotesTestPatientTwo);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(1));
		assertNotNull(testAssesment);
		assertEquals("Test TestBorderline", testAssesment.getPatientName());
		assertEquals(77, testAssesment.getPatientAge());
		assertEquals(Risk.BorderLine, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_BorderlineTwo_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(2L)).thenReturn(listNotesTestPatientThree);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(1));
		assertNotNull(testAssesment);
		assertEquals("Test TestBorderline", testAssesment.getPatientName());
		assertEquals(77, testAssesment.getPatientAge());
		assertEquals(Risk.BorderLine, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_BorderlineThree_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(2L)).thenReturn(listNotesTestPatientSix);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(1));
		assertNotNull(testAssesment);
		assertEquals("Test TestBorderline", testAssesment.getPatientName());
		assertEquals(77, testAssesment.getPatientAge());
		assertEquals(Risk.BorderLine, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_BorderlineFour_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(1L)).thenReturn(listNotesTestPatientSeven);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(0));
		assertNotNull(testAssesment);
		assertEquals("Test TestNone", testAssesment.getPatientName());
		assertEquals(55, testAssesment.getPatientAge());
		assertEquals(Risk.BorderLine, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_InDanger_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(3L)).thenReturn(listNotesTestPatientThree);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(2));
		assertNotNull(testAssesment);
		assertEquals("Test TestInDanger", testAssesment.getPatientName());
		assertEquals(18, testAssesment.getPatientAge());
		assertEquals(Risk.InDanger, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_InDangerTwo_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(4L)).thenReturn(listNotesTestPatientSix);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(3));
		assertNotNull(testAssesment);
		assertEquals("Test TestEarlyOnset", testAssesment.getPatientName());
		assertEquals(20, testAssesment.getPatientAge());
		assertEquals(Risk.InDanger, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_InDangerThree_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(4L)).thenReturn(listNotesTestPatientSeven);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(3));
		assertNotNull(testAssesment);
		assertEquals("Test TestEarlyOnset", testAssesment.getPatientName());
		assertEquals(20, testAssesment.getPatientAge());
		assertEquals(Risk.InDanger, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_InDangerFour_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(2L)).thenReturn(listNotesTestPatientFive);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(1));
		assertNotNull(testAssesment);
		assertEquals("Test TestBorderline", testAssesment.getPatientName());
		assertEquals(77, testAssesment.getPatientAge());
		assertEquals(Risk.InDanger, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_InDangerFive_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(1L)).thenReturn(listNotesTestPatientEight);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(0));
		assertNotNull(testAssesment);
		assertEquals("Test TestNone", testAssesment.getPatientName());
		assertEquals(55, testAssesment.getPatientAge());
		assertEquals(Risk.InDanger, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_EarlyOnset_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(4L)).thenReturn(listNotesTestPatientFour);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(3));
		assertNotNull(testAssesment);
		assertEquals("Test TestEarlyOnset", testAssesment.getPatientName());
		assertEquals(20, testAssesment.getPatientAge());
		assertEquals(Risk.EarlyOnset, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_EarlyOnsetTwo_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(3L)).thenReturn(listNotesTestPatientSeven);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(2));
		assertNotNull(testAssesment);
		assertEquals("Test TestInDanger", testAssesment.getPatientName());
		assertEquals(18, testAssesment.getPatientAge());
		assertEquals(Risk.EarlyOnset, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_EarlyOnsetThree_returnsCorrectAssessment() {
		when(noteRestClient.getNoteByPatientId(3L)).thenReturn(listNotesTestPatientFive);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(2));
		assertNotNull(testAssesment);
		assertEquals("Test TestInDanger", testAssesment.getPatientName());
		assertEquals(18, testAssesment.getPatientAge());
		assertEquals(Risk.EarlyOnset, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_ById_returnsAssessment() {
		when(patientRestClient.getPatientById(1L)).thenReturn(listPatientsTest.get(0));
		when(noteRestClient.getNoteByPatientId(1L)).thenReturn(listNotesTestPatientOne);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(0).getId());
		assertNotNull(testAssesment);
		assertEquals("Test TestNone", testAssesment.getPatientName());
		assertEquals(55, testAssesment.getPatientAge());
		assertEquals(Risk.None, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_ByWrongId_throwsException() {
		when(patientRestClient.getPatientById(11L)).thenThrow(FeignException.NotFound.class);
		assertThrows(FeignException.NotFound.class, () -> reportService.riskAssessment(11L));
	}
	
	@Test
	public void riskAssessmentTest_ByName_returnsAssessment() {
		when(patientRestClient.getPatientByNames(anyString(), anyString())).thenReturn(listPatientsTest);
		when(noteRestClient.getNoteByPatientId(1L)).thenReturn(listNotesTestPatientOne);
		AssessmentDTO testAssesment = reportService.riskAssessment(listPatientsTest.get(0).getFirstname());
		assertNotNull(testAssesment);
		assertEquals("Test TestNone", testAssesment.getPatientName());
		assertEquals(55, testAssesment.getPatientAge());
		assertEquals(Risk.None, testAssesment.getRisk());
	}
	
	@Test
	public void riskAssessmentTest_ByNameWithNoNotes_throwsException() {
		when(patientRestClient.getPatientByNames(anyString(), anyString())).thenReturn(listPatientsTest);
		when(noteRestClient.getNoteByPatientId(anyLong())).thenThrow(FeignException.NotFound.class);
		assertThrows(FeignException.NotFound.class, () -> reportService.riskAssessment(listPatientsTest.get(3).getFirstname()));
	}
}
