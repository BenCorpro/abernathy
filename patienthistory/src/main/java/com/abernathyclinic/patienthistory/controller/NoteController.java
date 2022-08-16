package com.abernathyclinic.patienthistory.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.patienthistory.dto.NoteDTO;
import com.abernathyclinic.patienthistory.exception.NoteNotFoundException;
import com.abernathyclinic.patienthistory.service.NoteService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NoteController {

	private NoteService noteService;

	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	
	@GetMapping("/notes")
	public List<NoteDTO> getAllNotes(){
		return noteService.getAllNotes();
	}
	
	@GetMapping("/note/{id}")
	public NoteDTO getNoteById(@PathVariable("id") String id) throws NoteNotFoundException{
		return noteService.getNoteById(id);
	}
	
	@GetMapping("/notes/{patientId}")
	public List<NoteDTO> getNoteByPatientId(@PathVariable("patientId") Long patientId) throws NoteNotFoundException{
		return noteService.getNoteByPatientId(patientId);
	}
	
	@PostMapping("/note/add")
	@ResponseStatus(HttpStatus.CREATED)
	public NoteDTO saveNote(@Valid @RequestBody NoteDTO newNote) {
		return noteService.saveNote(newNote);
	}
	
	@PutMapping("/note/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public NoteDTO updateNote(@PathVariable("id") String id, @Valid @RequestBody NoteDTO modNote) throws NoteNotFoundException {
		return noteService.updateNote(id, modNote);
	}
	
	@DeleteMapping("/note/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteNote(@PathVariable("id") String id) throws NoteNotFoundException {
		noteService.deleteNote(id);
	}
	
}
