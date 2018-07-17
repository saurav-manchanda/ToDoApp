package com.bridgelabz.todoapplication.noteservice.service;

import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.utilservice.ToDoException;

public interface INoteService {

	void createNote(Note note) throws ToDoException;

}
