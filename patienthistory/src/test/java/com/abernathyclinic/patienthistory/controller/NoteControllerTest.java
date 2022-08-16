package com.abernathyclinic.patienthistory.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.abernathyclinic.patienthistory.dto.NoteDTO;
import com.abernathyclinic.patienthistory.exception.NoteNotFoundException;
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
	
	List<NoteDTO> noteTestList = new ArrayList<>();
	
	@BeforeAll
	public void setUp() throws Exception{
		NoteDTO noteTestOne = new NoteDTO(1L, "TestNone", "Patient states that they are 'feeling terrific' Weight at or below recommended level");
		NoteDTO noteTestTwo = new NoteDTO(2L, "TestBorderline", "Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late");
		NoteDTO noteTestThree = new NoteDTO(3L, "TestInDanger", "Patient states that they are short term Smoker");
		NoteDTO noteTestFour = new NoteDTO(4L, "TestEarlyOnset", "Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication");
		NoteDTO notedeleteTest = new NoteDTO(5L, "TestDeleteNote", "Deletion of patient history");
		noteTestList.add(noteTestOne);
		noteTestList.add(noteTestTwo);
		noteTestList.add(noteTestThree);
		noteTestList.add(noteTestFour);
		noteTestList.add(notedeleteTest);
	}
	
	@Test
	public void testGetAllNotes_returnsNoteList() throws Exception {
		when(noteService.getAllNotes()).thenReturn(noteTestList);
		mockMvc.perform(get("/notes"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(noteTestList)));
	}
	
	@Test
	public void testGetNoteById_existingId_returnsNote() throws Exception {
		when(noteService.getNoteById("fakenoteid")).thenReturn(noteTestList.get(0));
		mockMvc.perform(get("/note/fakenoteid"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(noteTestList.get(0))));
	}
	
	@Test
	public void testGetNoteById_wrongId_returnsNotFound() throws Exception {
		when(noteService.getNoteById("wrongnoteid")).thenThrow(new NoteNotFoundException("Note wrongnoteid not found!"));
		mockMvc.perform(get("/note/wrongnoteid"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("Error", Is.is("Note wrongnoteid not found!")));
	}
	
	@Test
	public void testGetNoteByPatientId_existingId_returnsNoteList() throws Exception {
		when(noteService.getNoteByPatientId(2L)).thenReturn(noteTestList.subList(1, 2));
		mockMvc.perform(get("/notes/2"))
		 		.andExpect(status().isOk())
		 		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		 		.andExpect(content().json(objectMapper.writeValueAsString(noteTestList.subList(1, 2))));
	}
	
	@Test
	public void testGetNoteByPatientId_wrongId_returnsNotFound() throws Exception {
		when(noteService.getNoteByPatientId(11L)).thenThrow(new NoteNotFoundException("Notes for patient 11 not found!"));
		mockMvc.perform(get("/notes/11"))
		 		.andExpect(status().isNotFound())
		 		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		 		.andExpect(MockMvcResultMatchers.jsonPath("Error", Is.is("Notes for patient 11 not found!")));
	}
	
	@Test
	public void testSaveNote_validNote_returnsNote() throws Exception {
		NoteDTO noteSaveTest = new NoteDTO(6L, "TestSaveNote", "Test for saving a new note");
		when(noteService.saveNote(any(NoteDTO.class))).thenReturn(noteSaveTest);
		mockMvc.perform(MockMvcRequestBuilders.post("/note/add")
				.content(objectMapper.writeValueAsString(noteSaveTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(noteSaveTest)));
	}
	
	@Test
	public void testSaveNote_nonValidField_returnsBadRequest() throws Exception {
		NoteDTO noteSaveTest = new NoteDTO(6L, null, "Test for saving a new note");
		when(noteService.saveNote(any(NoteDTO.class))).thenReturn(noteSaveTest);
		mockMvc.perform(MockMvcRequestBuilders.post("/note/add")
				.content(objectMapper.writeValueAsString(noteSaveTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("patientName", Is.is("Le nom du patient doit être renseigné")));
	}
	
	@Test
	public void testUpdateNote_validNote_returnsNote() throws Exception {
		NoteDTO noteUpdateTest = new NoteDTO(7L, "TestUpdateNote", "Test for updating a note");
		when(noteService.updateNote(anyString(), any(NoteDTO.class))).thenReturn(noteUpdateTest);
		mockMvc.perform(MockMvcRequestBuilders.put("/note/update/"+"fakenoteid")
				.content(objectMapper.writeValueAsString(noteUpdateTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(noteUpdateTest)));
	}
	
	@Test
	public void testUpdateNote_nonValidField_returnsBadRequest() throws Exception {
		NoteDTO noteUpdateTest = new NoteDTO(7L, null, "Test for updating a note");
		when(noteService.updateNote(anyString(), any(NoteDTO.class))).thenReturn(noteUpdateTest);
		mockMvc.perform(MockMvcRequestBuilders.put("/note/update/"+"fakenoteid")
				.content(objectMapper.writeValueAsString(noteUpdateTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("patientName", Is.is("Le nom du patient doit être renseigné")));
	}
	
	@Test
	public void testDeleteNote_existingId_returnsNoContent() throws Exception {
		mockMvc.perform(delete("/note/delete/"+"fakenoteid"))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void testDeleteNote_wrongId_returnsNotFound() throws Exception {
		doThrow(new NoteNotFoundException("Note wrongnoteid not found!")).when(noteService).deleteNote(anyString());
		mockMvc.perform(delete("/note/delete/"+"wrongnoteid"))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("Error", Is.is("Note wrongnoteid not found!")));
	}
	
}
