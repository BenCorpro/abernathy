package com.abernathyclinic.patienthistory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abernathyclinic.patienthistory.model.Note;
import com.abernathyclinic.patienthistory.repository.NoteRepository;

@Service
public class NoteService {

	private NoteRepository noteRepository;

	
	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}
	
	
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}
	
	public Note getNoteById(String id) {
		return noteRepository.findById(id).orElseThrow();
	}
	
	public List<Note> getNoteByPatientId(long patientId){
		return noteRepository.findByPatientId(patientId);
	}
	
	public Note saveNote(Note newNote) {
		return noteRepository.insert(newNote);
	}
	
	public Note updateNote(String id, Note modNote) {
		Note noteToUpdate = noteRepository.findById(id).orElseThrow();
		noteToUpdate.setRecommendation(modNote.getRecommendation());
		return noteRepository.save(noteToUpdate);
	}
	
	public void deleteNote(String id) {
		noteRepository.deleteById(id);
	}
	
}
