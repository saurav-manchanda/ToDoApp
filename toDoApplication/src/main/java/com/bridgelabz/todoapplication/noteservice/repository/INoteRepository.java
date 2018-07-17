package com.bridgelabz.todoapplication.noteservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.todoapplication.noteservice.model.Note;

public interface INoteRepository extends MongoRepository<Note, String> {
	public Optional<Note> getByNoteId(String noteId);	
}
