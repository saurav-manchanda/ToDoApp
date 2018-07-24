package com.bridgelabz.todoapplication.utilservice.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.bridgelabz.todoapplication.noteservice.service.INoteService;

public class Scheduler {

	@Autowired
	INoteService noteService;
	
	@Scheduled(fixedDelay = 1000)
	public void removeNoteFromTrash() {
//		noteService.deleteFromTrash(noteId, token);
}
}
