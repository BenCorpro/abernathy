package com.abernathyclinic.patienthistory.service;

import java.util.List;

import com.abernathyclinic.patienthistory.dto.NoteDTO;
import com.abernathyclinic.patienthistory.exception.NoteNotFoundException;

public interface NoteService {

	List<NoteDTO> getAllNotes();

	NoteDTO getNoteById(String id) throws NoteNotFoundException;

	List<NoteDTO> getNoteByPatientId(long patientId) throws NoteNotFoundException;

	NoteDTO saveNote(NoteDTO newNote);

	NoteDTO updateNote(String id, NoteDTO modNote) throws NoteNotFoundException;

	void deleteNote(String id) throws NoteNotFoundException;

}