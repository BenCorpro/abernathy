package com.abernathyclinic.patienthistory.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.abernathyclinic.patienthistory.model.Note;

@Service
public class NoteMapper {

	public NoteDTO fromNote(Note note) {
		NoteDTO noteDTO = new NoteDTO();
		BeanUtils.copyProperties(note, noteDTO);
		return noteDTO;
	}
	
	public Note fromNoteDTO(NoteDTO noteDTO) {
		Note note = new Note();
		BeanUtils.copyProperties(noteDTO, note);
		return note;
	}
}
