package com.abernathyclinic.patienthistory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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

import com.abernathyclinic.patienthistory.model.Note;
import com.abernathyclinic.patienthistory.repository.NoteRepository;;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class NoteServiceTest {

	@Autowired
	private NoteService noteService;
	@MockBean
	private NoteRepository noteRepository;

	List<Note> noteTestList = new ArrayList<>();
	
	@BeforeAll
	public void setUp() throws Exception{
		Note noteTestOne = new Note(1L, "TestNone", "Patient states that they are 'feeling terrific' Weight at or below recommended level");
		Note noteTestTwo = new Note(2L, "TestBorderline", "Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late");
		Note noteTestThree = new Note(3L, "TestInDanger", "Patient states that they are short term Smoker");
		Note noteTestFour = new Note(4L, "TestEarlyOnset", "Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication");
		Note notedeleteTest = new Note(5L, "TestDeleteNote", "Deletion of patient history");
		noteTestList.add(noteTestOne);
		noteTestList.add(noteTestTwo);
		noteTestList.add(noteTestThree);
		noteTestList.add(noteTestFour);
		noteTestList.add(notedeleteTest);
	}
	
	@Test
	public void getAllNotesTest() {
		when(noteRepository.findAll()).thenReturn(noteTestList);
		List<Note> resultTestList = noteService.getAllNotes();
		assertNotNull(resultTestList);
		assertEquals(5, resultTestList.size());
		assertEquals("TestNone", resultTestList.get(0).getPatientName());
	}
	
	@Test
	public void getNoteByIdTest() throws Exception {
		when(noteRepository.findById(anyString())).thenReturn(Optional.of(noteTestList.get(1)));
		Note noteTest = noteService.getNoteById("testnoteid");
		assertNotNull(noteTest);
		assertEquals("TestBorderline", noteTest.getPatientName());
	}
	
	@Test
	public void getNoteByPatientIdTest() throws Exception {
		when(noteRepository.findByPatientId(3L)).thenReturn(noteTestList.subList(2, 3));
		List<Note> resultTestList = noteService.getNoteByPatientId(3L);
		assertNotNull(resultTestList.get(0));
		assertEquals("Patient states that they are short term Smoker", resultTestList.get(0).getRecommendation());
	}
	
	@Test
	public void saveNoteTest() throws Exception {
		Note noteSaveTest = new Note(6L, "TestSaveNote", "Test for saving a new note");
		when(noteRepository.insert(noteSaveTest)).thenReturn(noteSaveTest);
		noteSaveTest = noteService.saveNote(noteSaveTest);
		assertNotNull(noteSaveTest.getPatientId());
		assertEquals("TestSaveNote", noteSaveTest.getPatientName());
	}
	
	@Test
	public void updateNoteTest() throws Exception {
		Note noteUpdateTest = new Note(7L, "TestUpdateNote", "Test for updating a note");
		when(noteRepository.findById(noteTestList.get(3).getId())).thenReturn(Optional.of(noteTestList.get(3)));
		when(noteRepository.save(any(Note.class))).thenReturn(noteUpdateTest);
		noteUpdateTest = noteService.updateNote(noteTestList.get(3).getId(), noteUpdateTest);
		assertNotNull(noteUpdateTest);
		assertEquals("Test for updating a note", noteUpdateTest.getRecommendation());
	}
	
	@Test
	public void deleteNoteTest() {
		String idNoteDelete = noteTestList.get(4).getId();
		noteService.deleteNote(idNoteDelete);
	}
}
