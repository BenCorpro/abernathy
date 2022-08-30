package com.abernathyclinic.patienthistory.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abernathyclinic.patienthistory.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String>{

	public List<Note> findByPatientIdOrderByCreatedAtDesc(Long id);
	
}
