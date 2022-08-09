package com.abernathyclinic.patienthistory.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.abernathyclinic.patienthistory.model.Note;
import com.abernathyclinic.patienthistory.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = NoteController.class)
@TestInstance(Lifecycle.PER_CLASS)
public class NoteControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private NoteService noteService;
	
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
	public void testGetAllNotes() throws Exception {
		when(noteService.getAllNotes()).thenReturn(noteTestList);
		mockMvc.perform(get("/notes"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(noteTestList)));
	}
	
	@Test
	public void testGetNoteById() throws Exception {
		when(noteService.getNoteById("fakenoteid")).thenReturn(noteTestList.get(0));
		mockMvc.perform(get("/note/fakenoteid"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(noteTestList.get(0))));
	}
	
	@Test
	public void testGetNoteByPatientId() throws Exception {
		when(noteService.getNoteByPatientId(2L)).thenReturn(noteTestList.subList(1, 2));
		mockMvc.perform(get("/notes/2"))
		 		.andExpect(status().isOk())
		 		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		 		.andExpect(content().json(objectMapper.writeValueAsString(noteTestList.subList(1, 2))));
	}
	
	@Test
	public void testSaveNote() throws Exception {
		Note noteSaveTest = new Note(6L, "TestSaveNote", "Test for saving a new note");
		when(noteService.saveNote(noteSaveTest)).thenReturn(noteSaveTest);
		mockMvc.perform(MockMvcRequestBuilders.post("/note/add")
				.content(objectMapper.writeValueAsString(noteSaveTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(noteSaveTest)));
	}
	
	@Test
	public void testUpdateNote() throws Exception {
		Note noteUpdateTest = new Note(7L, "TestUpdateNote", "Test for updating a note");
		when(noteService.updateNote("fakenoteid", noteUpdateTest)).thenReturn(noteUpdateTest);
		mockMvc.perform(MockMvcRequestBuilders.put("/note/update/"+"fakenoteid")
				.content(objectMapper.writeValueAsString(noteUpdateTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(noteUpdateTest)));
	}
	
	@Test
	public void testDeleteNote() throws Exception {
		mockMvc.perform(delete("/note/delete/"+"fakenoteid"))
				.andExpect(status().isNoContent());
	}
	
}
