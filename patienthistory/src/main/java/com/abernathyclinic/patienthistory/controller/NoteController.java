package com.abernathyclinic.patienthistory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.patienthistory.model.Note;
import com.abernathyclinic.patienthistory.service.NoteService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NoteController {

	private NoteService noteService;

	
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}
	
	@GetMapping("/notes")
	public List<Note> getAllNotes(){
		return noteService.getAllNotes();
	}
	
	@GetMapping("/note/{id}")
	public Note getNoteById(@PathVariable("id") String id){
		return noteService.getNoteById(id);
	}
	
	@GetMapping("/notes/{patientId}")
	public List<Note> getNoteByPatientId(@PathVariable("patientId") Long patientId){
		return noteService.getNoteByPatientId(patientId);
	}
	
	@PostMapping("/note/add")
	@ResponseBody
	public Note saveNote(@RequestBody Note newNote) {
		return noteService.saveNote(newNote);
	}
	
	@PutMapping("/note/update/{id}")
	public Note updateNote(@PathVariable("id") String id, @RequestBody Note modNote) {
		return noteService.updateNote(id, modNote);
	}
	
	@DeleteMapping("/note/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteNote(@PathVariable("id") String id) {
		noteService.deleteNote(id);
	}
	
}
