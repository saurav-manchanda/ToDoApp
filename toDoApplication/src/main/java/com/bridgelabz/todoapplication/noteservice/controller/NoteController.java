package com.bridgelabz.todoapplication.noteservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgelabz.todoapplication.noteservice.model.Note;
import com.bridgelabz.todoapplication.noteservice.service.INoteService;

public class NoteController {
	@Autowired
	INoteService noteService;
	
//	@RequestMapping(value = "/createnote")
//	public ResponseEntity<Note> createNote(@RequestBody Note note) {
//		noteService.createNote(note);
//		
//}
}
