package com.abernathyclinic.patientinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abernathyclinic.patientinfo.dto.PatientDTO;
import com.abernathyclinic.patientinfo.exception.PatientNotFoundException;
import com.abernathyclinic.patientinfo.model.Patient;
import com.abernathyclinic.patientinfo.repository.PatientRepository;
import com.abernathyclinic.patientinfo.util.Gender;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class PatientServiceTest {

	@Autowired
	private PatientService patientService;
	@MockBean
	private PatientRepository patientRepository;
	
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
	public void getAllPatientsTest_returnsPatientList() {
		when(patientRepository.findAll()).thenReturn(patientTestList);
		List<PatientDTO> resultTestList = patientService.getAllPatients();
		assertNotNull(resultTestList);
		assertEquals(5, resultTestList.size());
		assertEquals("TestNone", resultTestList.get(0).getFirstname());
	}
	
	@Test
	public void getPatientByIdTest_existingId_returnsPatient() throws Exception {
		when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patientTestList.get(1)));
		PatientDTO patientTest = patientService.getPatientById(2L);
		assertNotNull(patientTest);
		assertEquals("TestBorderline", patientTest.getFirstname());
	}
	
	@Test
	public void getPatientByIdTest_wrongId_throwsException() throws Exception {
		when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(11L));
	}
	
	@Test 
	public void getPatientByNamesTest_matchingName_returnsPatient() throws Exception {
		when(patientRepository.findByFirstnameContainsAndLastnameContains(anyString(), anyString())).thenReturn(patientTestList.subList(2, 3));
		List<PatientDTO> resultTestList = patientService.getPatientByNames("Test", "TestInDanger");
		assertNotNull(resultTestList.get(0));
		assertEquals("3 Club Road", resultTestList.get(0).getAddress());
	}
	
	@Test 
	public void getPatientByNamesTest_wrongName_throwsException() throws Exception {
		when(patientRepository.findByFirstnameContainsAndLastnameContains(anyString(), anyString())).thenReturn(new ArrayList<Patient>());
		assertThrows(PatientNotFoundException.class, () -> patientService.getPatientByNames("Wrong", "Name"));
	}
	
	@Test
	public void savePatientTest_returnsPatient() throws Exception {
		Patient patientSaveTest = new Patient("TestSavePatient", "Test", dateFormatter.parse("1986-02-12"), Gender.FEMALE, "5 Crash Boulevard", "555-666-7777");
		PatientDTO patientDTOSaveTest = new PatientDTO("TestSavePatient", "Test", dateFormatter.parse("1986-02-12"), Gender.FEMALE, "5 Crash Boulevard", "555-666-7777");
		when(patientRepository.save(any(Patient.class))).thenReturn(patientSaveTest);
		patientDTOSaveTest = patientService.savePatient(patientDTOSaveTest);
		assertNotNull(patientDTOSaveTest.getFirstname());
		assertEquals("555-666-7777", patientDTOSaveTest.getPhone());
	}
	
	@Test
	public void updatePatientTest_existingId_returnsPatient() throws Exception {
		Patient patientUpdateTest = new Patient("TestUpdatePatient", "Test", dateFormatter.parse("1924-03-15"), Gender.FEMALE, "6 Off Road", "666-777-9999");
		PatientDTO patientDTOUpdateTest = new PatientDTO("TestUpdatePatient", "Test", dateFormatter.parse("1924-03-15"), Gender.FEMALE, "6 Off Road", "666-777-9999");
		when(patientRepository.findById(patientTestList.get(3).getId())).thenReturn(Optional.of(patientTestList.get(3)));
		when(patientRepository.save(any(Patient.class))).thenReturn(patientUpdateTest);
		patientDTOUpdateTest = patientService.updatePatient(patientTestList.get(3).getId(), patientDTOUpdateTest);
		assertNotNull(patientDTOUpdateTest);
		assertEquals("TestUpdatePatient", patientDTOUpdateTest.getFirstname());
	}
	
	@Test
	public void updatePatientTest_wrongId_throwsException() throws Exception {
		Patient patientUpdateTest = new Patient("TestUpdatePatient", "Test", dateFormatter.parse("1924-03-15"), Gender.FEMALE, "6 Off Road", "666-777-9999");
		PatientDTO patientDTOUpdateTest = new PatientDTO("TestUpdatePatient", "Test", dateFormatter.parse("1924-03-15"), Gender.FEMALE, "6 Off Road", "666-777-9999");
		when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(patientRepository.save(any(Patient.class))).thenReturn(patientUpdateTest);
		assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(patientTestList.get(3).getId(), patientDTOUpdateTest));
	}
	
	@Test
	public void deletePatientTest_existingId_returnsVoid() throws Exception {
		long idPatientDelete = patientTestList.get(4).getId();
		when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patientTestList.get(3)));
		patientService.deletePatient(idPatientDelete);
	}
	
	@Test
	public void deletePatientTest_wrongId_throwsException() throws Exception {
		when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(PatientNotFoundException.class, () -> patientService.deletePatient(11L));
	}
}
