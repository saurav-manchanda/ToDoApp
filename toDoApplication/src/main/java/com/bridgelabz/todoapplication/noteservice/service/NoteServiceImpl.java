package com.bridgelabz.todoapplication.noteservice.service;

import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.utilservice.ToDoException;

public class NoteServiceImpl implements INoteService {

	@Override
	public void createNote(Note note) throws ToDoException {
		if (note.getNoteId().equals("") || note.getTitle().equals("") || note.getDescription().equals("") || note.getCreatedDate().equals("")) {
			throw new ToDoException("Null values entered");
		}
	}
	
}
