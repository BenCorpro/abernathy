package com.abernathyclinic.patienthistory.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abernathyclinic.patienthistory.dto.NoteDTO;
import com.abernathyclinic.patienthistory.dto.NoteMapper;
import com.abernathyclinic.patienthistory.exception.NoteNotFoundException;
import com.abernathyclinic.patienthistory.model.Note;
import com.abernathyclinic.patienthistory.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService {

	private NoteRepository noteRepository;
	private NoteMapper noteMapper;
	
	
	public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
		this.noteRepository = noteRepository;
		this.noteMapper = noteMapper;
	}

	
	@Override
	public List<NoteDTO> getAllNotes() {
		List<Note> notes = noteRepository.findAll();
		List<NoteDTO> noteDTOs = notes.stream().map(note -> noteMapper.fromNote(note))
												.collect(Collectors.toList());
		return noteDTOs;
	}
	
	@Override
	public NoteDTO getNoteById(String id) throws NoteNotFoundException {
		Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException("Note " + id + " not found!"));
		NoteDTO noteDTO = noteMapper.fromNote(note);
		return noteDTO;
	}
	
	@Override
	public List<NoteDTO> getNoteByPatientId(long patientId) throws NoteNotFoundException{
		List<Note> notes = noteRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
		if(notes.isEmpty()) throw new NoteNotFoundException("Notes for patient " + patientId + " not found!");
		List<NoteDTO> notesDTO = notes.stream().map(note -> noteMapper.fromNote(note))
												.collect(Collectors.toList());
		return notesDTO;
	}
	
	@Override
	public NoteDTO saveNote(NoteDTO newNote) {
		Note note = noteMapper.fromNoteDTO(newNote);
		note.setCreatedAt(LocalDateTime.now());
		note = noteRepository.insert(note);
		return noteMapper.fromNote(note);
	}
	
	@Override
	public NoteDTO updateNote(String id, NoteDTO modNote) throws NoteNotFoundException {
		Note noteToUpdate = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException("Note " + id + " not found!"));
		noteToUpdate.setRecommendation(modNote.getRecommendation());
		noteToUpdate.setUpdatedAt(LocalDateTime.now());
		Note note = noteRepository.save(noteToUpdate);
		return noteMapper.fromNote(note);
	}
	
	@Override
	public void deleteNote(String id) throws NoteNotFoundException {
		noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException("Note " + id + " not found!"));
		noteRepository.deleteById(id);
	}
	
}
